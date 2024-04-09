package ezen.ezencompany.service;

import java.util.HashMap;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezen.ezencompany.dao.MemberDAO;
import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.MemberAttachVO;
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
	public int insertNum(HashMap<String, Object> map) {
		return memberDAO.insertNum(map);
	}
	
	//인증번호를 update
	public int updateNum(HashMap<String, Object> map) {
		return memberDAO.updateNum(map);
	}
	
	//인증번호가 일치하는지 확인
	public int checkCert(HashMap<String, Object> map) {
		return memberDAO.checkCert(map);
	}
	
	//이메일로 그 사람의 정보를 memberVO에 넣는다
	public MemberVO getMember(String email) {
		return memberDAO.getMember(email);
	}
	
	//프로필 이미지를 가져온다
	public String getImg(int mno) {
		return memberDAO.getImg(mno);
	}
	
	//아이디로 이메일을 가져온다
	public String getEmailId(String mid) {
		return memberDAO.getEmailId(mid);
	}
	
	//비밀번호를 수정한다
	public void changePwOk(HashMap<String, Object> map) {
		memberDAO.changePwOk(map);
	}
	
	// 사원의 옵션목록을 반환한다.
	public List<AttributeVO> getOptions(int mno) {
		return memberDAO.getOption(mno);
	}
}
