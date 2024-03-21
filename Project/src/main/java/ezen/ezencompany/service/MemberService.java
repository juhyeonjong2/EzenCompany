package ezen.ezencompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezen.ezencompany.dao.MemberDAO;

@Service
public class MemberService {

	@Autowired
	MemberDAO memberDAO;
	
	//중복체크하는 dao호출
	public int checkID(String text) {
		return memberDAO.checkID(text);
	}
	
}
