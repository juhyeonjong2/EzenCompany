package ezen.ezencompany.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ezen.ezencompany.service.ChattingService;
import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.ChatVO;
import ezen.ezencompany.vo.MemberVO;
import ezen.ezencompany.vo.UserVO;

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
	@RequestMapping(value = "/chattingStart", method = RequestMethod.GET)
	@ResponseBody 
	public List<ChatVO> chattingStart(int amno) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		int bmno = user.getMno();
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("amno", amno); //상대방의 mno
		map.put("bmno", bmno); //나의 mno
		
		//채팅방 찾기 디비 값이 없어서 null값이 나옴 
		String chattingroom = chattingService.getRoom(map);
		
		System.out.println(chattingroom);
		//채팅내역 찾기
		List<ChatVO> data = chattingService.chattingStart(chattingroom);
		
		return data;
	}
	
	//채팅방 들어온 경우 사전에 필요한 데이터를 받는다
	@RequestMapping(value = "/detaleList", method = RequestMethod.GET)
	@ResponseBody 
	public List<MemberVO> detaleList() {
		List<MemberVO> detaleList = chattingService.detaleList();
		return detaleList;
	}
	
	
	//연습용 채팅방
	@RequestMapping(value = "/chat")
	public String aa() {
		return "admin/chattingEx";
	}
}
