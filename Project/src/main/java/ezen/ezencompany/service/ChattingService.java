package ezen.ezencompany.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezen.ezencompany.dao.ChattingDAO;
import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.ChatVO;
import ezen.ezencompany.vo.MemberVO;

@Service
public class ChattingService {
	
	@Autowired
	ChattingDAO chattingDAO;
	
	//사원 목록
	public List<AttributeVO> chattingList(){
		return chattingDAO.chattingList();
	}
	
	//모든 사원
	public List<MemberVO> detaleList(){
		return chattingDAO.detaleList();
	}
	
	//채팅방 찾기
	public String getRoom(HashMap<String, Object> map){
		return chattingDAO.getRoom(map);
	}
	
	//채팅방 찾기
	public List<ChatVO> chattingStart(String chattingroom){
		return chattingDAO.chattingStart(chattingroom);
	}
}
