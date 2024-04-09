package ezen.ezencompany.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ezen.ezencompany.service.ChattingService;
import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.MemberVO;

@RequestMapping(value = "/chatting")
@Controller
public class ChattingController {

	@Autowired
	ChattingService chattingService;
	
	//채팅팝업 사원 리스트 가져오기
	@RequestMapping(value = "/chattingList", method = RequestMethod.GET)
	@ResponseBody 
	public List<AttributeVO> chattingList() {
		List<AttributeVO> list = chattingService.chattingList();
		return list;
	}
	
	//채팅팝업 사원 디테일 리스트(모든 사원)
	@RequestMapping(value = "/detaleList", method = RequestMethod.GET)
	@ResponseBody 
	public List<MemberVO> detaleList() {
		List<MemberVO> detaleList = chattingService.detaleList();
		return detaleList;
	}
}
