package ezen.ezencompany.service;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ezen.ezencompany.dao.AdminDAO;

@Service
public class AdminService {
	
	@Autowired
	AdminDAO adminDAO;
	
	// 트랜잭션을 사용해 전부 성공하는 경우에만 insert를 한다
	// 작업순서
	//1. member에 이름과 이메일을 넣는다
	//2. 마지막 mno를 가져온다 
	//2. employeeOption에 분류테이블의 코드 수만큼 반복해서 넣는다
	// 나중에 트랜잭션이 잘 작동하는지 확인 필요
	@Transactional()
	public void employeeRegistration (Map<String, Object> member, List<HashMap<String, Object>> list) throws Exception{
		 //member
		 adminDAO.memberRegistration(member);
		 //mno
		 int mno = adminDAO.getLastMno(member);
		 //employeeOption
		 //여기서 리스트를 map으로 분해 -> 그리고 mno를 넣음
		 HashMap<String, Object> map = new HashMap<>();
		 for(HashMap<String, Object> category : list) {
			 for ( String aidx : category.keySet() ) {
				 map.put("aidx", aidx);
				 map.put("cidx", category.get(aidx));
				 map.put("mno", mno);
				 adminDAO.employeeRegistration(map);
				}
		 }
	}
	//짧은 경로를 만드는 함수
	public void insertShortUrl(Map<String, Object> member, String url) {
		//mno를 구함
		 int mno = adminDAO.getLastMno(member);
		//mno와 짧은 경로를 합쳐서 dao호출
		 HashMap<String, Object> shortUrl = new HashMap<>();
		 shortUrl.put("mno", mno);
		 shortUrl.put("url", url);
		 adminDAO.insertShortUrl(shortUrl);
	}

}