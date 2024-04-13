package ezen.ezencompany.controller;

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
	
	
	//알림 가져오기
	@RequestMapping(value = "/getNoti", method = RequestMethod.GET)
	@ResponseBody 
	public List<NotificationVO> chattingList() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		int mno = user.getMno();
		
		List<NotificationVO> list = notificationService.getNoti(mno);
		for(int i=0; i<list.size(); i++) {
			notificationService.checkNoti(list.get(i).getNno());
		}

		return list;
	}
}
