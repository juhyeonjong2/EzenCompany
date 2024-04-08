package ezen.ezencompany.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ezen.ezencompany.service.BlogService;
import ezen.ezencompany.vo.BlogUserVO;
import ezen.ezencompany.vo.BlogVO;
import ezen.ezencompany.vo.EmployeeOptionVO;
import ezen.ezencompany.vo.FolderVO;
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
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write() {
		
		
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
		Document doc = Jsoup.parse(content);
		String blockyn = "n"; // 블로그 생성시 넘겨받아야할 정보.
		int fno = 1;  // 폴더 선택 번호.
		
		
		
		
		// 폴더가 없으면 기본 폴더 생성하기.
		
		System.out.println("title");
		System.out.println(title);
		System.out.println("content");
		System.out.println(content);
		
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
	
}
