package ezen.ezencompany.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import ezen.ezencompany.service.BlogService;
import ezen.ezencompany.vo.BlogNodeVO;
import ezen.ezencompany.vo.BlogUserVO;
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
		List<BlogUserVO> retiredEmployees = blogService.blogUserListByRetired(); // 탈퇴한 사람목록
		// 2. blogView 정보.
		HashMap<String, List<BlogUserVO>> blogUsers = new HashMap<String, List<BlogUserVO>>();
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
				List<BlogUserVO> blogUserList = blogService.blogUserListByOption(category, attribute);
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
		BlogVO vo = blogService.getLastOne(user.getMno());
		model.addAttribute("vo", vo);
		if(vo!=null) {
			System.out.println("home vo:");
			System.out.println(vo.getBgtitle());
			System.out.println(vo.getBgcontent());
		}
		
		// 첨부 파일들
		
		
		// 블로그 댓글.
		
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
	public String writeOk(HttpServletRequest request) {
		
		// 로그인된 사용자 정보 가져오기.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		
		System.out.println("writeOk");
		
		// 이건 필터 사용할떄 필터처리후 데이터를 넣어주는 경우.
		//String title = (String)request.getAttribute("title"); 
		//String content = (String)request.getAttribute("content"); 
		
		String title = (String)request.getParameter("title");
		String content = (String)request.getParameter("content");
		String folder = (String)request.getParameter("folder");
		Document doc = Jsoup.parse(content);
		String blockyn = "n"; // 블로그 생성시 넘겨받아야할 정보.
		int fno = Integer.parseInt(folder);// 폴더 선택 번호.
		
		
		
		
		// 폴더가 없으면 기본 폴더 생성하기.
		
		System.out.println("title");
		System.out.println(title);
		System.out.println("content");
		System.out.println(content);
		System.out.println("folder");
		System.out.println(folder);
		System.out.println("rawContent");
		System.out.println(doc.text());
		

		int mno = user.getMno();
		System.out.println("mno");
		System.out.println(mno);
		
		
		//폴더 목록 가져오기
		List<FolderVO> folders = blogService.getFolders(mno);
		if(folders.isEmpty()) {
			// 비어 있다면 하나 생성하기.
			FolderVO vo = new FolderVO();
			vo.setMno(mno);
			vo.setFname("기본");
			vo.setPfno(0);
			blogService.makeFolder(vo);
			fno = vo.getFno(); // 생성후 바로 fno 넣기.
		} else {
			boolean isFind = false;
			for(FolderVO vo : folders) 
			{
				if(vo.getFno()	== fno) 
				{
					isFind= true;
					break;
				}
			}
			if(!isFind) {
				fno = folders.get(0).getFno(); // 없으면 가장 위에 있는 데이터(기본) 폴더 선택.
			}
		}
		

		BlogVO vo = new BlogVO();
		vo.setBgtitle(title);
		vo.setBgcontent(content);
		vo.setBgrealcontent(doc.text());
		vo.setBlockyn(blockyn);
		vo.setFno(fno);
		vo.setMno(mno);
		
		
		
		int result = blogService.insert(vo);
		System.out.println("result :" + result);
		return "redirect:home";
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
		List<BlogUserVO> retiredEmployees = blogService.blogUserListByRetired(); // 탈퇴한 사람목록
		// 2. blogView 정보.
		HashMap<String, List<BlogUserVO>> blogUsers = new HashMap<String, List<BlogUserVO>>();
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
				List<BlogUserVO> blogUserList = blogService.blogUserListByOption(category, attribute);
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
		
		MemberVO user = blogService.getMember(vo.getMno());
		model.addAttribute("blogSubject", user.getMname() + "'s Blog"); // 블로그 제목
		model.addAttribute("vo", vo);
		model.addAttribute("mno", user.getMno()); // mno
		model.addAttribute("writer", user.getMname()); 		// 작성자 이름 및 프로필 정보
		String profileSrc = request.getContextPath() + "/resources/icon/user.png"; // 기본 아이콘이고 변경 해야함.
		model.addAttribute("profileImage", profileSrc); // 프로필 이미지 
		
		
		
		// 첨부 파일들
		
		
		// 블로그 댓글.
			
		
		return "blog/view";
	}
	
	
	@RequestMapping(value="/folder/nodes", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getNodes(int mno) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		
		System.out.println("mno : " +  mno);
		
		boolean isMine = false;
		if(user.getMno() == mno) {
			isMine = true;
		}
		
		// 블로그 목록
		List<BlogNodeVO> nodes = new ArrayList<BlogNodeVO>();
		
		
		// 폴더 가져오기.
		List<FolderVO> folders = blogService.getFolders(mno); // origin
		System.out.println("folders : " + folders.size());
		
		// 블로그 가져오기
		List<BlogVO> blogs  = blogService.blogList(mno, isMine);
		System.out.println("blogs : " + blogs.size());
		
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
	
}
