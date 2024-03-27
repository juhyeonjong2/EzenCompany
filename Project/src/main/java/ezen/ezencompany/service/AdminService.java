package ezen.ezencompany.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ezen.ezencompany.dao.AdminDAO;

@Service
public class AdminService {
	
	//@Autowired
	//AdminDAO adminDAO;
	
	// 트랜잭션을 사용해 전부 성공하는 경우에만 insert를 한다
	// 작업순서
	//1. member에 이름과 이메일을 넣는다
	//2. employeeOption에 분류테이블의 코드 수만큼 반복해서 넣는다
	@Transactional
	public void employeeRegistration(List<String> names) {
		 //member
		// adminDAO.memberRegistration(names);
		 //employeeOption
		 //문제 1 names에는 사원 옵션에 들어갈 mno(방금 insert한), 분류테이블의 code, 넘어온 name값들이 들어가야함
		 //문제 2 for문은 분류테이블의 code만큼 실행함(names에 아디 이메일빼고 넣으면 names길이도 ㄱㅊ)
		 //문제 1.1 member에 insert하자마자 마지막 mno값을 구해야함 -> 트랜잭션을 사용하면 전부 성공안하면 커밋 안되는데 어캐구함?
		 //문제 1.2 분류테이블의 code는 따로 가져와야하나? 
		 //문제 1.3 넘어온 값 중에 이름 이메일은 따로가져와야하는데 그경우 names에서 효율적으로 그 둘을 제외하고 가져오는법(방법없다면 sysout로 두번 호출뒤 실행)
		 //문제 1.4 분류테이블의 code와 넘어온 name값이 알맞게 매치해야하는데 jsp의 셀렉트의 값은 cate.code 옵션의 값은 attr.value
		// for(int i = 0; i<names.size(); i++) {
		//	 adminDAO.employeeRegistration(여기에는 cate.code, 여기에는 attr.value, mno는 평범하게 구함); 
		// }
	}
}
