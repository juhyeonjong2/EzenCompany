package ezen.ezencompany.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ezen.ezencompany.service.BlogService;
import ezen.ezencompany.service.MemberService;
import ezen.ezencompany.util.Path;
import ezen.ezencompany.vo.BlogAttachVO;
import ezen.ezencompany.vo.BlogNodeVO;
import ezen.ezencompany.vo.BlogReplyVO;
import ezen.ezencompany.vo.BlogVO;
import ezen.ezencompany.vo.EmployeeOptionVO;
import ezen.ezencompany.vo.FolderVO;
import ezen.ezencompany.vo.MemberVO;
import ezen.ezencompany.vo.UserVO;


@RequestMapping(value="/blog")
@Controller
public class BlogController {
	
	@Autowired
	BlogService blogService;
	
	@Autowired
	MemberService memberService;
	

	@RequestMapping(value="/home")
	public String home(Model model, HttpServletRequest request) {
		// 로그인된 사용자 정보 가져오기.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		// mno
		model.addAttribute("mno", user.getMno());
		
		// 블로그 제목
		model.addAttribute("blogSubject", user.getMname() + "'s Blog");

		// 블로그 네비게이션
		// 1. 퇴사자
		List<MemberVO> retiredEmployees = blogService.blogUserListByRetired(); // 탈퇴한 사람목록
		// 2. 전체 사원
		List<MemberVO> employees = blogService.blogUserListByActive(user.getMno());
		// 3. blogView 정보.
		HashMap<String, List<MemberVO>> blogUsers = new HashMap<String, List<MemberVO>>();
		// 내 옵션정보 가져옴
		List<EmployeeOptionVO> myOptions = blogService.getAdditionalOptions(user.getMno());
		HashMap<Integer, Integer> myOptionMap = new HashMap<Integer, Integer>();
		for(EmployeeOptionVO vo : myOptions) {
			myOptionMap.put(vo.getCidx(), vo.getAidx());
		}
		
		// category에 blogview가 있는목록중.
		List<Integer> blogViewCategorys = blogService.blogViewCategorys(); 
		for(int i=0;i<blogViewCategorys.size();i++) {
			Integer category = blogViewCategorys.get(i);
			if(myOptionMap.containsKey(category)) {
				Integer attribute = myOptionMap.get(category);
				String attributeName = blogService.getAttributeName(attribute);
				List<MemberVO> blogUserList = blogService.blogUserListByOption(category, attribute);
				
				MemberVO myMember = null;
				// 자신은 제외
				for(MemberVO member : blogUserList) {
					if(member.getMno() == user.getMno()) {
						myMember = member;
						break;
					}
				}
				if(myMember != null) {
					blogUserList.remove(myMember);
				}
				
				
				if(blogUserList.size() >0 ) {
					blogUsers.put(attributeName, blogUserList);
				}
			}
		}
		
		model.addAttribute("retiredEmployees",retiredEmployees);
		model.addAttribute("employees", employees);
		model.addAttribute("blogUsers", blogUsers);
				
		// 작성자 이름 및 프로필 정보
		model.addAttribute("writer", user.getMname());
		String profileSrc = request.getContextPath() + "/resources/icon/user.png"; // 기본 아이콘이고 변경 해야함.
		model.addAttribute("profileImage", profileSrc);
		
		// 가장최근에 쓴 블로그
		BlogVO vo = blogService.getLastOne(user.getMno(), true);
		model.addAttribute("vo", vo);
		if(vo!=null) {
			model.addAttribute("bno", vo.getBgno()); // bgno
			
			// 첨부 파일들
			List<BlogAttachVO> files = blogService.getFiles(vo.getBgno());
			model.addAttribute("files", files);
		}
		
		model.addAttribute("isEditable", true);
		
		

		
		
		// 댓글은  AJAX로 요청함.
		return "blog/home";
	}
	
	private String getRecursiveFolderName(Map<Integer,FolderVO> map, FolderVO vo )
	{
		if(vo.getPfno() == 0) {
			return vo.getFname();
		}
		else {
			return getRecursiveFolderName(map, map.get(vo.getPfno())) + "/" + vo.getFname();
		}
	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(Model model) {
		
		// 로그인된 사용자 정보 가져오기.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		
		int mno =  user.getMno();
		// mno
		model.addAttribute("mno", mno);

		
		// write 들어왔는데 폴더가 0개인경우 한개 기본 폴더 생성하기.
		List<FolderVO> folders = blogService.getFolders(mno);
		if(folders.isEmpty()) {
			// 비어 있다면 하나 생성하기.
			FolderVO vo = new FolderVO();
			vo.setMno(mno);
			vo.setFname("기본");
			vo.setPfno(0);
			blogService.makeFolder(vo);
			folders = blogService.getFolders(mno);
		} 
		
		Map<Integer, FolderVO > hashFolders = new HashMap<Integer, FolderVO >();
		for(FolderVO vo : folders){
			hashFolders.put(vo.getFno(), vo);
		}
		
		// 폴더 목록을 만들어서 넣기
		// fno, name 두개만있으면됨.
		List<FolderVO> rebuildFolders = new ArrayList<FolderVO>();
		for(FolderVO vo : folders)
		{
			FolderVO folder = new FolderVO();
			folder.setFno(vo.getFno());
			folder.setFname(getRecursiveFolderName(hashFolders, vo));
			rebuildFolders.add(folder);
		}
		model.addAttribute("folders", rebuildFolders);
		
		return "blog/write";
	}
	
	@RequestMapping(value="/writeOk", method=RequestMethod.POST)
	public String writeOk(HttpServletRequest request, BlogVO vo, List<MultipartFile> uploadFile) throws Exception {
		
		// 로그인된 사용자 정보 가져오기.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		
		// 이건 필터 사용할떄 필터처리후 데이터를 넣어주는 경우.
		//String title = (String)request.getAttribute("title"); 
		//String content = (String)request.getAttribute("content"); 
		
		// 넘어온 데이터 가공 및 검증
		
		// 태그 제거 데이터 넣기
		Document doc = Jsoup.parse(vo.getBgcontent());
		vo.setBgrealcontent(doc.text());
		
		// 공개/비공개 처리
		if(vo.getBlockyn() == null) {
			vo.setBlockyn("n");
		}
		vo.setMno(user.getMno());
		
		int result = blogService.insert(vo);
		if(result > 0) {
			// 파일 저장
			
			for (MultipartFile multipartFile : uploadFile) {
				if(!multipartFile.getOriginalFilename().isEmpty()) {
					
					String originfileName = multipartFile.getOriginalFilename();
					String fileExtension = FilenameUtils.getExtension(originfileName);

					// 업로드 경로 : basePath + upload/blog/{bgno}
					String [] subPath =  {"upload", "blog", Integer.toString(vo.getBgno())};
					
					String fileName = UUID.randomUUID().toString(); // 중복방지를 위한 랜덤 이름.
					StringBuffer buffer = new StringBuffer();
					buffer.append(fileName);
					buffer.append("_");
					buffer.append(originfileName.hashCode()); //긴문자열 대비 해시코드로 변경
					if(!fileExtension.isBlank()) {
						buffer.append("."); 
						buffer.append(fileExtension);
					}
					String realFileName = buffer.toString();
					String basePath = request.getSession().getServletContext().getRealPath("resources");
					String uploadPath = Path.getUploadPath(basePath, subPath);
					multipartFile.transferTo(new File(uploadPath,realFileName)); // 저장
					
					BlogAttachVO attach = new BlogAttachVO();
					attach.setBgno(vo.getBgno());
					attach.setBgforeignname(originfileName);
					attach.setBgfrealname(realFileName);
					
					blogService.insertFile(attach);
					
				}
			}
		}
		
		return "redirect:home";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(Model model, int bgno) {
		
		// 로그인된 사용자 정보 가져오기.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		
		int mno =  user.getMno();
		model.addAttribute("mno", mno);
		
		// 블로그 데이터 가져오기.
		BlogVO blogVO = blogService.selectOne(bgno, true); // 일단 강제로 가져온다.
		// 이블로그를 쓴사람이 자신이라면 -> 그냥 보여주기. 
		if(blogVO == null || blogVO.getMno() != mno)
		{
			// 블로그 주인장이 아니라면 수정권한이 없음.
			return "redirect:/blog/home";
		}
		
		model.addAttribute("vo", blogVO);
		
		// write 들어왔는데 폴더가 0개인경우 한개 기본 폴더 생성하기.
		List<FolderVO> folders = blogService.getFolders(mno);
		Map<Integer, FolderVO > hashFolders = new HashMap<Integer, FolderVO >();
		for(FolderVO vo : folders){
			hashFolders.put(vo.getFno(), vo);
		}
		
		// 폴더 목록을 만들어서 넣기
		// fno, name 두개만있으면됨.
		List<FolderVO> rebuildFolders = new ArrayList<FolderVO>();
		for(FolderVO vo : folders)
		{
			FolderVO folder = new FolderVO();
			folder.setFno(vo.getFno());
			folder.setFname(getRecursiveFolderName(hashFolders, vo));
			rebuildFolders.add(folder);
		}
		model.addAttribute("folders", rebuildFolders);
		
		
		// 첨부 파일들
		List<BlogAttachVO> files = blogService.getFiles(bgno);
		model.addAttribute("files", files);
		
		
		
		return "blog/modify";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modifyOk(HttpServletRequest request ,BlogVO vo, List<MultipartFile> uploadFile, @RequestParam(value="uploadedFiles") String[] uploadedFiles) throws Exception {
		
		// 로그인된 사용자 정보 가져오기.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		
		int mno =  user.getMno();
		// 블로그 데이터 가져오기.
		BlogVO originalBlogVO = blogService.selectOne(vo.getBgno(), true); // 일단 강제로 가져온다.
		// 이블로그를 쓴사람이 자신이라면 -> 그냥 보여주기. 
		if(originalBlogVO == null || originalBlogVO.getMno() != mno)
		{
			// 블로그 주인장이 아니라면 수정권한이 없음.
			return "redirect:/blog/home";
		}
		
		
		//System.out.println("=========originalFiles========");
		// 오리지날 버전을 준비한다. (Bgno)
		List<BlogAttachVO> originalFiles = blogService.getFiles(vo.getBgno());
		List<String> uploadedList = Arrays.asList(uploadedFiles);
		List<Integer> removedList = new ArrayList<Integer>();
		// 이전 데이터중 file이 변경되었다면 지울껀 지워주기.
		for(BlogAttachVO file : originalFiles) {
			//System.out.println(file.getBgfno());
			String s =Integer.toString(file.getBgfno());
			if(!uploadedList.contains(s)) {
				removedList.add(file.getBgfno());
			}
			
		}
		// 삭제목록에 있는 파일 삭제.
		//System.out.println("=========removedList========");
		for(Integer v : removedList) {
			//System.out.println(v);
			int blogFileNo = v;
			BlogAttachVO fileVo = blogService.getFile(blogFileNo);
			if(fileVo != null) {
				//1. 웹서버 드라이브에서 실제 파일 삭제
				// 1-1. 저장된 파일 경로 - 업로드 경로 : basePath + upload/blog/{bgno}
				String [] subPath =  {"upload", "blog", Integer.toString(fileVo.getBgno())};
				String basePath = request.getSession().getServletContext().getRealPath("resources");
				String uploadPath = Path.getUploadPath(basePath,subPath);
				// 1-2. 파일 삭제.
				File file = new File(uploadPath, fileVo.getBgfrealname());
				if( file.exists()) {
		    		file.delete();
				}
				
				//2. 디비에서 정보 삭제.
				blogService.removeFile(v);
			}
		}
		
		// 폴더 확인.
		List<FolderVO> folders = blogService.getFolders(mno);
		Map<Integer, FolderVO > hashFolders = new HashMap<Integer, FolderVO >();
		for(FolderVO folderVo : folders){
			hashFolders.put(folderVo.getFno(), folderVo);
		}
	
		// 새로운 데이터 설정
		originalBlogVO.setBgcontent(vo.getBgcontent());
		originalBlogVO.setBlockyn(originalBlogVO.getBlockyn());
		// 태그 제거 데이터 넣기
		Document doc = Jsoup.parse(originalBlogVO.getBgcontent());
		originalBlogVO.setBgrealcontent(doc.text());
		
		if(hashFolders.containsKey(vo.getFno())) {
			originalBlogVO.setFno(vo.getFno());
		}
		
		originalBlogVO.setBlockyn(vo.getBlockyn());
		// 공개/비공개 처리
		if(originalBlogVO.getBlockyn() == null) {
			originalBlogVO.setBlockyn("n");
		}
		
		int result = blogService.modifyOne(originalBlogVO);
		// 새로운 파일이 잇으면 추가.	
		for (MultipartFile multipartFile : uploadFile) {
			if(!multipartFile.getOriginalFilename().isEmpty()) {
				
				String originfileName = multipartFile.getOriginalFilename();
				String fileExtension = FilenameUtils.getExtension(originfileName);
				
				// 업로드 경로 : basePath + upload/blog/{bgno}
				String [] subPath =  {"upload", "blog", Integer.toString(originalBlogVO.getBgno())};
				
				String fileName = UUID.randomUUID().toString(); // 중복방지를 위한 랜덤 이름.
				StringBuffer buffer = new StringBuffer();
				buffer.append(fileName); // 오리지널 파일 이름이 긴경우가 있다. 예외를 방지하기위해 이것도 짧게 줄이자.
				buffer.append("_");
				buffer.append(originfileName.hashCode()); //긴문자열 대비 해시코드로 변경
				if(!fileExtension.isBlank()) {
					buffer.append("."); 
					buffer.append(fileExtension);
				}
				String realFileName = buffer.toString();
				String basePath = request.getSession().getServletContext().getRealPath("resources");
				String uploadPath = Path.getUploadPath(basePath, subPath);
				multipartFile.transferTo(new File(uploadPath,realFileName)); // 저장
				
				BlogAttachVO attach = new BlogAttachVO();
				attach.setBgno(originalBlogVO.getBgno());
				attach.setBgforeignname(originfileName);
				attach.setBgfrealname(realFileName);
				
				blogService.insertFile(attach);
				
			}
		}

		return "redirect:/blog/page/" + Integer.toString(originalBlogVO.getBgno());
	}
	
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	public String removeOk(HttpServletRequest request, int bgno){
		// 로그인된 사용자 정보 가져오기.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		
		int mno =  user.getMno();
		// 블로그 데이터 가져오기.
		BlogVO vo = blogService.selectOne(bgno, true); // 일단 강제로 가져온다.
		// 이블로그를 쓴사람이 자신이라면 -> 그냥 보여주기. 
		if(vo == null || vo.getMno() != mno)
		{
			// 블로그 주인장이 아니라면 수정권한이 없음.
			return "redirect:/blog/home";
		}
		
		//1. 해당 블로그가 가지고있는 모든 파일 삭제.
		List<BlogAttachVO> originalFiles = blogService.getFiles(bgno);
		for(BlogAttachVO fileVo : originalFiles) {
		
			//1. 웹서버 드라이브에서 실제 파일 삭제
			// 1-1. 저장된 파일 경로 - 업로드 경로 : basePath + upload/blog/{bgno}
			String [] subPath =  {"upload", "blog", Integer.toString(fileVo.getBgno())};
			String basePath = request.getSession().getServletContext().getRealPath("resources");
			String uploadPath = Path.getUploadPath(basePath, subPath);
			// 1-2. 파일 삭제.
			File file = new File(uploadPath, fileVo.getBgfrealname());
			if( file.exists()) {
	    		file.delete();
			}
		}
		// 2. 디비에서 파일 정보 일괄 삭제.
		blogService.removeFiles(bgno);
		
		//3. 블로그 삭제
		blogService.removeOne(bgno);
		
		// 삭제 뒤에도 홈으로.
		return "redirect:/blog/home";
	}
	
	
	@GetMapping(value="/other/{mno}")
	public String other(@PathVariable int mno) {
		
		// 가장최근에 쓴 블로그
		BlogVO vo = blogService.getLastOne(mno, false);
		if(vo == null) {
			return "redirect:/blog/home";
		}
		
		//가장 최근에쓴 블로그로 리다이렉트시킴.
		
		return "redirect:/blog/page/" + Integer.toString(vo.getBgno());
	}
	

	@GetMapping(value="/page/{bgno}")
	public String view(@PathVariable int bgno, Model model, HttpServletRequest request) {
		// 로그인된 사용자 정보 가져오기.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO myUser = (UserVO) authentication.getPrincipal();
		int myMno = myUser.getMno();
		// 상단 정보 ( 내정보기반)
		// 블로그 네비게이션
		// 1. 퇴사자
		List<MemberVO> retiredEmployees = blogService.blogUserListByRetired(); // 탈퇴한 사람목록
		// 2. 전체 직원
		List<MemberVO> employees = blogService.blogUserListByActive(myMno);
		// 3. blogView 정보.
		HashMap<String, List<MemberVO>> blogUsers = new HashMap<String, List<MemberVO>>();
		// 내 옵션정보 가져옴
		List<EmployeeOptionVO> myOptions = blogService.getAdditionalOptions(myUser.getMno());
		HashMap<Integer, Integer> myOptionMap = new HashMap<Integer, Integer>();
		for(EmployeeOptionVO vo : myOptions) {
			myOptionMap.put(vo.getCidx(), vo.getAidx());
		}
		
		// category에 blogview가 있는목록중.
		List<Integer> blogViewCategorys = blogService.blogViewCategorys(); 
		for(int i=0;i<blogViewCategorys.size();i++) {
			Integer category = blogViewCategorys.get(i);
			if(myOptionMap.containsKey(category)) {
				Integer attribute = myOptionMap.get(category);
				String attributeName = blogService.getAttributeName(attribute);
				List<MemberVO> blogUserList = blogService.blogUserListByOption(category, attribute);
				
				MemberVO myMember = null;
				// 자신은 제외
				for(MemberVO member : blogUserList) {
					if(member.getMno() == myMno) {
						myMember = member;
						break;
					}
				}
				if(myMember != null) {
					blogUserList.remove(myMember);
				}
				
				
				if(blogUserList.size() >0 ) {
					blogUsers.put(attributeName, blogUserList);
				}
			}
		}
		
		model.addAttribute("retiredEmployees",retiredEmployees);
		model.addAttribute("employees", employees);
		model.addAttribute("blogUsers", blogUsers);
		
		// 내용
		
		// 블로그 데이터 가져오기.
		BlogVO vo = blogService.selectOne(bgno, true); // 일단 강제로 가져온다.
		// 이블로그를 쓴사람이 자신이라면 -> 그냥 보여주기. 
		if(vo.getMno() != myMno)
		{
			// 블로그 주인장이 아니라면 block확인
			if(vo.getBlockyn().equals("y")) {
				 // 블럭된 글이라면 홈으로 리다이렉트.
				return "redirect:/blog/home";
			}
		}
		
		MemberVO user = memberService.getMember(vo.getMno());
		model.addAttribute("blogSubject", user.getMname() + "'s Blog"); // 블로그 제목
		model.addAttribute("vo", vo);
		model.addAttribute("bno", vo.getBgno()); // bgno
		model.addAttribute("mno", user.getMno()); // mno
		model.addAttribute("writer", user.getMname()); 		// 작성자 이름 및 프로필 정보
		String profileSrc = request.getContextPath() + "/resources/icon/user.png"; // 기본 아이콘이고 변경 해야함.
		model.addAttribute("profileImage", profileSrc); // 프로필 이미지 
		
		
		// 첨부 파일들
		List<BlogAttachVO> files = blogService.getFiles(bgno);
		model.addAttribute("files", files);
		
		if(vo.getMno() == myMno) {
			model.addAttribute("isEditable", true);
		}else {
			model.addAttribute("isEditable", false);
		}
		
		// 댓글은  AJAX로 요청함.
		return "blog/view";
	}
	
	@RequestMapping(value="/folder/list", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getFolderList() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		int mno = user.getMno();
		
		
		List<FolderVO> folders = blogService.getFolders(mno);
		Map<Integer, FolderVO > hashFolders = new HashMap<Integer, FolderVO >();
		for(FolderVO vo : folders){
			hashFolders.put(vo.getFno(), vo);
		}
		
		// 폴더 목록을 만들어서 넣기
		// fno, name 두개만있으면됨.
		List<FolderVO> rebuildFolders = new ArrayList<FolderVO>();
		for(FolderVO vo : folders)
		{
			FolderVO folder = new FolderVO();
			folder.setFno(vo.getFno());
			folder.setFname(getRecursiveFolderName(hashFolders, vo));
			rebuildFolders.add(folder);
		}
	
		// 반환값 생성 
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap.put("result", "SUCCESS"); //  결과 추가
		resMap.put("folders", rebuildFolders); // 노드 리스트 추가
		
		return resMap;
	
	}
	
	@RequestMapping(value="/folder/write", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> folderWrite(int pfno, String fname) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		int mno = user.getMno();
		
		
		List<FolderVO> folders = blogService.getFolders(mno);
		Map<Integer, FolderVO > hashFolders = new HashMap<Integer, FolderVO >();
		for(FolderVO vo : folders){
			hashFolders.put(vo.getFno(), vo);
		}
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		if(pfno != 0) { 
			// 부모폴더 존재하지 않음.
			if(hashFolders.containsKey(pfno) == false) {
				resMap.put("result", "FAIL"); //  결과 추가
				return resMap;
			}
		}
		
		FolderVO folderVO = new FolderVO();
		folderVO.setFname(fname);
		folderVO.setPfno(pfno);
		folderVO.setMno(mno);
		
		int result = blogService.makeFolder(folderVO);
		if(result > 0) {
			resMap.put("result", "SUCCESS"); //  결과 추가
		}
		else {
			resMap.put("result", "FAIL"); //  결과 추가
		}
		return resMap;
	
	}
	
	
	@RequestMapping(value="/folder/nodes", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getNodes(int mno) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		
		
		boolean isMine = false;
		if(user.getMno() == mno) {
			isMine = true;
		}
		
		// 블로그 목록
		List<BlogNodeVO> nodes = new ArrayList<BlogNodeVO>();
		// 폴더 가져오기.
		List<FolderVO> folders = blogService.getFolders(mno); // origin
		// 블로그 가져오기
		List<BlogVO> blogs  = blogService.blogList(mno, isMine);
		
		
		// 폴더를 먼저 추가한다.
		for(FolderVO folder : folders) {
			BlogNodeVO node = new BlogNodeVO(folder.getFno(), folder.getPfno(), folder.getFname());
			node.setOpen(true); // 여기 나오는건 모두 폴더라 부모로 설정함.
			nodes.add(node);
		}
		
		// 블로그 추가
		int blogNodeIndex = 10000;
		for(BlogVO blog : blogs) {
			BlogNodeVO node = new BlogNodeVO(blogNodeIndex++, blog.getFno(), blog.getBgtitle());
			node.setBlogNo(blog.getBgno()); //"/blog/page/[index]"
			nodes.add(node);
		}
		
		
		// 반환값 생성 
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap.put("result", "SUCCESS"); //  결과 추가
		resMap.put("nodes", nodes); // 노드 리스트 추가
		
		return resMap;
	
	}
	
	@RequestMapping(value="/reply/write", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> replyWriteOk(int bno, int prno, String content) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		int mno = user.getMno();
		
		BlogVO blog = blogService.selectOne(bno, true);
		int ownerMno = blog.getMno();
		
		BlogReplyVO vo = new BlogReplyVO();
		vo.setMno(mno);
		vo.setBgno(bno);
		vo.setBgrpno(prno);
		vo.setBgrcontent(content);
		
		int result = blogService.insertReply(vo);
		
		int targetMno=0;
		if(prno != 0) {
			BlogReplyVO preplyVO = blogService.getReply(prno);
			targetMno = preplyVO.getMno(); // 타겟
		}else {
			targetMno = blog.getMno();
		}
		
		String targetMid = memberService.getId(targetMno);
		 
		// 반환값 생성 
		Map<String,Object> resMap = new HashMap<String,Object>();
		if(result > 0 ) {
			MemberVO member = memberService.getMember(mno);
			vo = blogService.getReply(vo.getBgrno());
			vo.setAuthor(member.getMname());	
			vo.setEditable(true);
			
			// 블로그 주인장이 작성한 댓글
			if(mno == ownerMno) {
				vo.setMaster(true);
			}

			resMap.put("result", "SUCCESS"); //  성공
			resMap.put("total", blogService.blogReplyList(bno).size()); //  갯수
			resMap.put("data", vo);
			resMap.put("targetMno", targetMno);
			resMap.put("targetMid", targetMid);
			
		}
		else {
			resMap.put("result", "FAIL"); //  실패
		}
		
		return resMap;
	
	}
	
	@RequestMapping(value="/reply/list", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> replyList(int bno) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		int mno = user.getMno();
		
		BlogVO blog = blogService.selectOne(bno, true);
		int ownerMno = blog.getMno();
		
		List<BlogReplyVO> list =   blogService.blogReplyList(bno);
		for(BlogReplyVO vo : list) {
			MemberVO member = memberService.getMember(vo.getMno());
			vo.setAuthor(member.getMname());
			
			// 내가 작성한 댓글.
			if(vo.getMno() == mno) {
				vo.setEditable(true);
			}
			
			// 블로그 주인장이 작성한 댓글
			if(vo.getMno() == ownerMno) {
				vo.setMaster(true);
			}
			
			// 삭제된 댓글 확인.
			if(vo.getDelyn().equals("y")) {
				vo.setBgrcontent("삭제된 글입니다.");
				vo.setEditable(false);
			}
		}
		
		// 반환값 생성 
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap.put("total", list.size()); //  갯수
		resMap.put("list", list); //  
		
		
		return resMap;
	
	}
	
	@RequestMapping(value="/reply/modify", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> replyModifyOk(int rno, String content) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		int mno = user.getMno();
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		/// 내가 쓴 reply인지 확인.
		BlogReplyVO vo = blogService.getReply(rno);
		if(vo.getMno() != mno) {
			
			// 타인이 작성한 댓글인데 수정이 들어옴 : 오류 처리
			resMap.put("result", "FAIL"); //  실패
		} else {
			
			// 내가 작성한 글이 맞음.
			vo.setBgrcontent(content);
			
			// 수정
			int result = blogService.modifyReply(vo);
			if(result > 0 ) {
				vo = blogService.getReply(vo.getBgrno());
				
				BlogVO blog = blogService.selectOne(vo.getBgno(), true);
				int ownerMno = blog.getMno();
				MemberVO member = memberService.getMember(mno);
				vo.setAuthor(member.getMname());
				vo.setEditable(true);
				// 블로그 주인장이 작성한 댓글
				if(mno == ownerMno) {
					vo.setMaster(true);
				}
				
				
				resMap.put("result", "SUCCESS"); //  성공
				resMap.put("total", blogService.blogReplyList(vo.getBgno()).size()); //  갯수
				resMap.put("data", vo); 
			}
			else {
				resMap.put("result", "FAIL"); //  실패
			}
		}
		
		return resMap;
	
	}
	
	@RequestMapping(value="/reply/remove", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> replyRemoveOk(int rno) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		int mno = user.getMno();
		
		System.out.println("replyRemoveOk");
		Map<String,Object> resMap = new HashMap<String,Object>();
		
		/// 내가 쓴 reply인지 확인.
		BlogReplyVO vo = blogService.getReply(rno);
		if(vo.getMno() != mno) {
			
			// 타인이 작성한 댓글인데 수정이 들어옴 : 오류 처리
			resMap.put("result", "FAIL"); //  실패
		} else {
			
			int result = blogService.removeReply(vo.getBgrno());
			if(result > 0 ) {
				vo = blogService.getReply(vo.getBgrno());
				
				BlogVO blog = blogService.selectOne(vo.getBgno(), true);
				int ownerMno = blog.getMno();
				MemberVO member = memberService.getMember(mno);
				vo.setAuthor(member.getMname());
				vo.setEditable(false); // 이미 삭제된 댓글이므로 수정/삭제 버튼 x
				
				// 블로그 주인장이 작성한 댓글
				if(mno == ownerMno) {
					vo.setMaster(true);
				}
				

				// 삭제된 댓글 확인.
				if(vo.getDelyn().equals("y")) {
					vo.setBgrcontent("삭제된 글입니다.");
				}
				
				resMap.put("result", "SUCCESS"); //  성공
				resMap.put("total", blogService.blogReplyList(vo.getBgno()).size()); //  갯수
				resMap.put("data", vo); 
			}
			else {
				resMap.put("result", "FAIL"); //  실패
			}
		}
		
		return resMap;
	
	}
	
	// 파일 다운로드
	@GetMapping("/download/{bgfno}")
	public void download(HttpServletRequest request,Model model, @PathVariable int bgfno, HttpServletResponse response) throws Exception {
		
			 BlogAttachVO vo  = blogService.getFile(bgfno);
			 
			 // 실제 파일경로 구하기.
			 String [] subPath =  {"upload", "blog", Integer.toString(vo.getBgno())};
			 String basePath = request.getSession().getServletContext().getRealPath("resources");
			 
			 String uploadPath = Path.getUploadPath(basePath,subPath);
			 File downloadFile = new File(uploadPath,vo.getBgfrealname());
			 byte fileByte[] =  FileUtils.readFileToByteArray(downloadFile);
			 response.setContentType("application/octet-stream");
			 response.setContentLength(fileByte.length);
			 response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(vo.getBgforeignname(),"UTF-8") + "\";" );	
			 response.setHeader("Content-Transfer-Encoding", "binary;");
			 response.getOutputStream().write(fileByte);
			 response.getOutputStream().flush();
			 response.getOutputStream().close();
	}
	
}
