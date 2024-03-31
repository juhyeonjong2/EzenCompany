package ezen.ezencompany.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezen.ezencompany.dao.Map;
import ezen.ezencompany.dao.MemberDAO;
import ezen.ezencompany.dao.Object;
import ezen.ezencompany.dao.String;
import ezen.ezencompany.vo.MemberVO;

@Service
public class MemberService {

	@Autowired
	MemberDAO memberDAO;
	
	//중복체크하는 dao호출
	public int checkID(String text) {
		return memberDAO.checkID(text);
	}
	
	//짧은 경로를 mno로 변환
	public int requestMno(String url) {
		return memberDAO.requestMno(url);
	}
	
	//관리자에 의해 만들어진 계정에 업데이트
	public int joinOk(MemberVO vo) {
		return memberDAO.joinOk(vo);
	}
	
	//사원 리스트를 가져옴
	public List<MemberVO> employeeList() {
		return memberDAO.employeeList();
	}
	
	//인증번호가 존재하는지 확인
	public int selectNum(String email) {
		return memberDAO.selectNum(email);
	}
	
	//인증번호를 insert
	public int insertNum(Map<String, Object> map) {
		return memberDAO.insertNum(map);
	}
	
	//인증번호를 update
	public int updateNum(Map<String, Object> map) {
		return memberDAO.updateNum(map);
	}
	
	//인증번호가 일치하는지 확인
	public int checkCert(Map<String, Object> map) {
		return memberDAO.checkCert(map);
	}
}
