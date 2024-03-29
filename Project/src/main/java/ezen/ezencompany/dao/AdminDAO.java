package ezen.ezencompany.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDAO {
	
	//sql을 사용하기 위해서 생성(자동주입)
	@Autowired
	SqlSession sqlSession;
	
	//Mapper의 경로를 적어줌
	private final String namespace = "ezen.ezencompany.mapper.adminMapper";
	
	//1차로 member에 등록
	public int memberRegistration(Map<String, Object> aaa){
		return sqlSession.insert(namespace+".memberRegistration", aaa);
	}
	
	//2차로 mno를가져옴 (email은 고유함)
	public int getLastMno(Map<String, Object> aaa) {
		return sqlSession.selectOne(namespace+".getLastMno", aaa);
	}
	
	//3차로 사원옵션에 등록
	public int employeeRegistration(HashMap<String, Object> mm){
		return sqlSession.insert(namespace+".employeeRegistration", mm);
	}
}
