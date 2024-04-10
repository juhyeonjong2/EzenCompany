package ezen.ezencompany.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
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
		// 2. blogView 정보.
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
		model.addAttribute("blogUsers", blogUsers);
				
		// 작성자 이름 및 프로필 정보
		model.addAttribute("writer", user.getMname());
		String profileSrc = request.getContextPath() + "/resources/icon/user.png"; // 기본 아이콘이고 변경 해야함.
		model.addAttribute("profileImage", profileSrc);
		
		// 가장최근에 쓴 블로그
		BlogVO vo = blogService.getLastOne(user.getMno(), true);
		model.addAttribute("vo", vo);
		model.addAttribute("bno", vo.getBgno()); // bgno
		
		// 첨부 파일들
		List<BlogAttachVO> files = blogService.getFiles(vo.getBgno());
		model.addAttribute("files", files);
		

		
		
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
	public String writeOk(/*HttpServletRequest request,*/ BlogVO vo, List<MultipartFile> uploadFile) throws Exception {
		
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
					
					// 업로드 경로 : basePath + upload/blog/{bgno}
					String [] subPath =  {"upload", "blog", Integer.toString(vo.getBgno())};
					
					String fileName = UUID.randomUUID().toString(); // 중복방지를 위한 랜덤 이름.
					StringBuffer buffer = new StringBuffer();
					buffer.append(fileName);
					buffer.append("_");
					buffer.append(originfileName); // 확장자까지
					String realFileName = buffer.toString();
					
					String uploadPath = Path.getUploadPath(subPath);
					multipartFile.transferTo(new File(uploadPath,realFileName)); // 저장
					
					BlogAttachVO attach = new BlogAttachVO();
					attach.setBgno(vo.getBgno());
					attach.setBgforeignname(originfileName);
					attach.setBgfrealname(realFileName);
					
					blogService.insertfile(attach);
					
				}
			}
			
			
			
		}
		
		return "redirect:home";
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
		// 2. blogView 정보.
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
		
		// 댓글은  AJAX로 요청함.
		return "blog/view";
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
	public void download(Model model, @PathVariable int bgfno, HttpServletResponse response) throws Exception {
		
			 BlogAttachVO vo  = blogService.getFile(bgfno);
			 
			 // 실제 파일경로 구하기.
			 String [] subPath =  {"upload", "blog", Integer.toString(vo.getBgno())};
			 String uploadPath = Path.getUploadPath(subPath);
			 
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
