package ezen.ezencompany.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezen.ezencompany.dao.NotificationDAO;
import ezen.ezencompany.vo.NotificationVO;

@Service
public class NotificationService {
	@Autowired
	NotificationDAO notificationDAO;
	
	//읽지 않은 알림을 가져옴
	public List<NotificationVO> getNoti(int mno) {
		return notificationDAO.getNoti(mno);
	}
	
	//가져온 알림들을 읽음표시함
	public void checkNoti(int nno) {
		 notificationDAO.checkNoti(nno);
	}
}
