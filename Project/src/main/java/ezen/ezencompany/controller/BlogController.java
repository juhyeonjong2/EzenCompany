package ezen.ezencompany.controller;

import java.util.ArrayList;
import java.util.HashMap;

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
import ezen.ezencompany.vo.BlogVO;
import ezen.ezencompany.vo.MemberVO;
import ezen.ezencompany.vo.UserVO;


@RequestMapping(value="/blog")
@Controller
public class BlogController {
	
	@Autowired
	BlogService blogService;

	@RequestMapping(value="/home")
	public String home(Model model) {
		// 로그인된 사용자 정보 가져오기.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		
		// ws comment - 여기 작업중~
		
		// 블로그 네비게이션
		// 1. 퇴사자
		ArrayList<MemberVO> exitEmployee = blogService.exitEmployee(); // 탈퇴한 사람목록
		// 2. blogView 정보.
		HashMap<String, ArrayList<MemberVO>> blogUsers = new HahsMap<String, ArrayList<MemberVO>>();
		// 내 옵션정보 가져옴
		HashMap<Integer, Integer> myOptions = blogService.getMyAdditionalOptions(user.getMno());
		
		// category에 blogview가 있는목록중.
		ArrayList<Integer> blogViewCategorys = blogService.blogViewCategorys(); // 탈퇴한 사람목록
		for(int i=0;i<blogViewCategorys.size();i++) {
			Integer category = blogViewCategorys.get(i);
			if(myOptions.containsKey(category)) {
				Integer attribute = myOptions.get(category);
				String categoryName = blogService.getCategoryName(category);
				ArrayList<MemberVO> userList = blogService.userListByOption(category, attribute);
				if(userList.size() >0 ) {
					blogUsers.put(categoryName, userList);
				}
			}
		}
		model.addAttribute("exitEmployee",exitEmployee);
		model.addAttribute("blogUsers", blogUsers);
		
				
		// 작성자 이름 및 프로필 정보
		model.addAttribute("writer", user.getMname());
		model.addAttribute("profileImage", "");
		
		
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
		String blockyn = "n";
		int fno = 1;
		
		System.out.println("title");
		System.out.println(title);
		System.out.println("content");
		System.out.println(content);
		
		System.out.println("rawContent");
		System.out.println(doc.text());
		

		int mno = user.getMno();
		System.out.println("mno");
		System.out.println(mno);

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
