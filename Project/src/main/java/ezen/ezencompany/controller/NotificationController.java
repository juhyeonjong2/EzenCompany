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
import ezen.ezencompany.service.NotificationService;
import ezen.ezencompany.vo.NotificationVO;
import ezen.ezencompany.vo.UserVO;


@RequestMapping(value = "/notification")
@Controller
public class NotificationController {
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	ChattingService chattingService;
	
	//시작하자마자 알림 가져오기
	@RequestMapping(value = "/firstNoti", method = RequestMethod.GET)
	@ResponseBody 
	public List<NotificationVO> firstNoti() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		int mno = user.getMno();
		//번호로 이름 찾기
		String targetName = chattingService.getName(mno);
		List<NotificationVO> list = notificationService.getNoti(targetName);

		return list;
	}
	
	//알림 가져오기
	@RequestMapping(value = "/getNoti", method = RequestMethod.GET)
	@ResponseBody 
	public List<NotificationVO> getNoti() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		int mno = user.getMno();
		//번호로 이름 찾기
		String targetName = chattingService.getName(mno);
		List<NotificationVO> list = notificationService.getNoti(targetName);
		for(int i=0; i<list.size(); i++) {
			notificationService.checkNoti(list.get(i).getNno());
		}

		return list;
	}
	
	//블로그에서 댓글을 단 경우
	@RequestMapping(value = "/blogNoti", method = RequestMethod.GET, produces = "application/string; charset=utf8")
	@ResponseBody 
	public String blogNoti(int targetMno) {
		System.out.println(targetMno+"sssss");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		int mno = user.getMno();
		String myName = chattingService.getName(mno);
		
		//보내고자 하는 상대의 targetMno로 이름을 얻어냄
		String targetName = chattingService.getName(targetMno);
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("targetName", targetName); //채팅방 주소
		map.put("mno", mno); //나의 mno
		map.put("code", "bg");
		
		notificationService.sendNoti(map);
		return myName;
	}
	
	//게시글에서 댓글을 단 경우
	@RequestMapping(value = "/boardNoti", method = RequestMethod.GET)
	@ResponseBody 
	public String boardNoti(int targetMno) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		int mno = user.getMno();
		
		//보내고자 하는 상대의 targetMno로 이름을 얻어냄
		String targetName = chattingService.getName(targetMno);
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("targetName", targetName); //채팅방 주소
		map.put("mno", mno); //나의 mno
		map.put("code", "bo");
		
		notificationService.sendNoti(map);
		return "true";
	}
	
	//채팅밖에서 댓글을 단 경우
	@RequestMapping(value = "/chatNoti", method = RequestMethod.GET)
	@ResponseBody 
	public String chatNoti(int targetMno) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		int mno = user.getMno();
		
		//보내고자 하는 상대의 targetMno로 이름을 얻어냄
		String targetName = chattingService.getName(targetMno);
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("targetName", targetName); //상대이름
		map.put("mno", mno); //나의 mno
		map.put("code", "ch");
		
		notificationService.sendNoti(map);
		return "true";
	}
}
