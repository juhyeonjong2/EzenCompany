package ezen.ezencompany.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezen.ezencompany.dao.ChattingDAO;
import ezen.ezencompany.vo.AttributeVO;
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
}
