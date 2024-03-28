package ezen.ezencompany.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminDAO {
	
	//sql을 사용하기 위해서 생성(자동주입)
	@Autowired
	SqlSession sqlSession;
	
	//Mapper의 경로를 적어줌
	private final String namespace = "ezen.ezencompany.mapper.adminMapper";
	
	//1차로 member에 등록
	public int memberRegistration(String name, String email){
		return sqlSession.insert(namespace+".memberRegistration", name, email);
	}
	
	//2차로 mno를가져옴 (email은 고유함)
	public int getLastMno(String email) {
		return sqlSession.selectOne(namespace+".requestMno", email);
	}
	
	//3차로 사원옵션에 등록
	public int employeeRegistration(List<HashMap<String, Object>> list){
		return sqlSession.insert(namespace+".employeeRegistration", list);
	}
}
