package ezen.ezencompany.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ezen.ezencompany.service.AdminService;
import ezen.ezencompany.service.MemberService;
import ezen.ezencompany.vo.MemberVO;

@RequestMapping(value="/admin")
@Controller
public class AdminController {
	
	@Autowired
	MemberService memberService;
	
	//@Autowired
	//AdminService adminService;
	
	@Autowired
	SqlSession sqlSession;
	//로그 사용법 공부 필요
	//Logger logger = Logger.getLogger("AdminController.java");
	
	@RequestMapping(value="/home")
	public String main(Model model) {
		
		//모달안에 사원들 값 넣어두고 보낸다
		List<MemberVO> list = memberService.employeeList();
		model.addAttribute("list", list);
		
		//Map 형식으로 분류들을 전부 가져온다 -> 키와 벨류가 없는 것도 가져와야해서 리스트로 변경
		List<Object> cate 
			=  sqlSession.selectList("ezen.ezencompany.mapper.categoryMapper.selectCategory", null);
		model.addAttribute("cate", cate);
		//logger.info(cate);
		//Map 형식으로 속성들을 전부 가져온다 -> 키와 벨류가 없는 것도 가져와야해서 리스트로 변경
		List<Object> attr 
			= sqlSession.selectList("ezen.ezencompany.mapper.categoryMapper.selectAttribute", null);
		model.addAttribute("attr", attr);
		//logger.info(attr);
		return "admin/main";
	}
	
	@RequestMapping(value="/registration")
	public String registration(HttpServletRequest request) {
		Enumeration name = request.getParameterNames();
		//hasMoreElements 다음에 읽어올 내용이 있다면 true반환 
		//nextElement 사용할 때마다 처음부터 하나씩 name을 반환한다
		List names = new ArrayList();
		System.out.println(name.nextElement());
		System.out.println(name.nextElement()+"코드가 후져서 대안 생각해야할듯 일단은 이렇게하면 되긴함");
		while (name.hasMoreElements()) {
			//처음에 String name 등등 2개 받아와서 서비스로 보냄(그럼 트랜잭션은 어디서?)
			String aa = (String) name.nextElement();
			String bb = request.getParameter(aa);
			System.out.println(aa); //키
			System.out.println(bb); //값 이걸 묶어서 가져가면 될거같음 단 member에 들어갈 것은 따로 분리해서 넣어야할듯함
		        //값을 리스트로 만들어서 인서트함 -> 
		        //names.add(name.nextElement());
		}
		//adminService.employeeRegistration(names);
		
		//컨트롤러 간 이동을 할때(view resolver에 걸리지 않게 하려면)리다이렉트를 사용함
		return "redirect:/admin/home";
	}
}
