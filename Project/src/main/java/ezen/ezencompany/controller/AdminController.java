package ezen.ezencompany.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ezen.ezencompany.service.MemberService;
import ezen.ezencompany.vo.MemberVO;

@RequestMapping(value="/admin")
@Controller
public class AdminController {
	
	@Autowired
	MemberService memberService;
	
	@RequestMapping(value="/home")
	public String main(Model model) {
		
		//모달안에 사원들 값 넣어두고 보낸다
		List<MemberVO> list = memberService.employeeList();
		model.addAttribute("list", list);
		return "admin/main";
	}
}
