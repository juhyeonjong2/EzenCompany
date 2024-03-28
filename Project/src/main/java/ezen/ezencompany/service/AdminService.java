package ezen.ezencompany.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ezen.ezencompany.dao.AdminDAO;
import ezen.ezencompany.dao.String;

@Service
public class AdminService {
	
	@Autowired
	AdminDAO adminDAO;
	
	// 트랜잭션을 사용해 전부 성공하는 경우에만 insert를 한다
	// 작업순서
	//1. member에 이름과 이메일을 넣는다
	//2. 마지막 mno를 가져온다 
	//2. employeeOption에 분류테이블의 코드 수만큼 반복해서 넣는다
	@Transactional
	public void employeeRegistration(String name, String email, List<HashMap<String, Object>> list) {
		 //member
		 adminDAO.memberRegistration(name, email);
		 //mno
		 int mno = adminDAO.getLastMno(email);
		 //employeeOption
		 adminDAO.employeeRegistration(mno, list); 
	}
}
