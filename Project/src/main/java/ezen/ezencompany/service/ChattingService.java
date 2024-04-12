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
	
	//채팅 내역 찾기
	public List<ChatVO> chattingStart(String chattingroom){
		return chattingDAO.chattingStart(chattingroom);
	}
	
	//채팅방 만들기
	public void chattingCreate(HashMap<String, Object> map){
		chattingDAO.chattingCreate(map);
	}
	
	//프로필사진 찾기
	public String getProfile(int anotherMno){
		return chattingDAO.getProfile(anotherMno);
	}
	
	//채팅 메세지 기록하기
	public void chatting(HashMap<String, Object> chatting){
		chattingDAO.chatting(chatting);
	}
	
	//채팅 연결
	public void linkStart(int mno){
		chattingDAO.linkStart(mno);
	}
	
	//채팅 끊기
	public void linkEnd(int mno){
		chattingDAO.linkEnd(mno);
	}
}
