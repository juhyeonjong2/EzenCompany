package ezen.ezencompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezen.ezencompany.dao.MemberDAO;

@Service
public class MemberService {

	@Autowired
	MemberDAO memberDAO;
	
	public int checkID(String text) {
		return memberDAO.checkID(text);
	}
}
