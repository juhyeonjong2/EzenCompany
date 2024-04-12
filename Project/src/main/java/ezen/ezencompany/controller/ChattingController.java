package ezen.ezencompany.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
import ezen.ezencompany.vo.MemberAttachVO;
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
	
	//채팅방 들어온 경우 사전에 필요한 데이터를 받는다
	@RequestMapping(value = "/detaleList", method = RequestMethod.GET)
	@ResponseBody 
	public List<MemberVO> detaleList() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		int mno = user.getMno();
		
		
		List<MemberVO> detaleList2 = chattingService.detaleList();
		List<MemberVO> detaleList = new ArrayList<MemberVO>();
		//똑같은 리스트를 만들어서 그안에 자신을 제외한 값들만 넣어준다
		for(int i = 0; i<detaleList2.size(); i++) {
			//나의 mno와 비교해서 나를 제외한 리스트만 보이도록 한다
			if(mno != detaleList2.get(i).getMno()) {
				detaleList.add(detaleList2.get(i));
			}
		}
		return detaleList;
	}
	
	//프로필 사진을 찾는다
	@RequestMapping(value = "/getProfile", method = RequestMethod.GET)
	@ResponseBody 
	public String getProfile(int anotherMno) {
		String profile = chattingService.getProfile(anotherMno);
		return profile;
	}
	
	//채팅팝업 사원 디테일 리스트(모든 사원)
	@RequestMapping(value = "/chattingStart", method = RequestMethod.GET)
	@ResponseBody 
	public List<ChatVO> chattingStart(int anotherMno) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		int myMno = user.getMno();
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("anotherMno", anotherMno); //상대방의 mno
		map.put("myMno", myMno); //나의 mno
		
		//채팅방 찾기
		String chattingroom = chattingService.getRoom(map);
		
		System.out.println(chattingroom);
		//만약 채팅방이 null(채팅방이 없는경우)이라면 채팅방을 만들어준다
		if(chattingroom == null) {
			
			//uuid 생성(채팅방 주소)
			String uuid = UUID.randomUUID().toString();
			
			HashMap<String, Object> createRoom = new HashMap<>();
			createRoom.put("myMno", myMno);
			createRoom.put("anotherMno", anotherMno);
			createRoom.put("uuid", uuid);
			chattingService.chattingCreate(createRoom);
			
			//빈문자열 반환
			List<ChatVO> data = chattingService.chattingStart(chattingroom);
			return data;
		}else {
		    List<ChatVO> data = chattingService.chattingStart(chattingroom);
		    return data;
		}
	}
	
	//채팅을 친 경우
	@RequestMapping(value = "/sendChat", method = RequestMethod.GET)
	@ResponseBody 
	public String sendChat(int anotherMno, String chat) {
		
		//메세지를 db에 기록해줌
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		int myMno = user.getMno();
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("anotherMno", anotherMno); //상대방의 mno
		map.put("myMno", myMno); //나의 mno
		
		//채팅방 찾기
		String chattingroom = chattingService.getRoom(map);
		
		HashMap<String, Object> chatting = new HashMap<>();
		chatting.put("chattingRoom", chattingroom); //채팅방 주소
		chatting.put("myMno", myMno); //나의 mno
		chatting.put("chat", chat);
		
		//db저장
		chattingService.chatting(chatting);
		
		return "true";
	}
	
	//연습용 채팅방
	@RequestMapping(value = "/chat")
	public String aa() {
		return "admin/chattingEx";
	}
	
	//연습용 채팅방2
	@RequestMapping(value = "/chat2")
	public String aaa() {
		return "admin/aaaaa";
	}
}
