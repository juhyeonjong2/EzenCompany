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
	public int memberRegistration(Map<String, Object> member){
		return sqlSession.insert(namespace+".memberRegistration", member);
	}
	
	//2차로 mno를가져옴 (email은 고유함)
	public int getLastMno(Map<String, Object> member) {
		return sqlSession.selectOne(namespace+".getLastMno", member);
	}
	
	//3차로 사원옵션에 등록
	public int employeeRegistration(HashMap<String, Object> map){
		return sqlSession.insert(namespace+".employeeRegistration", map);
	}
	
	//짧은 경로 생성
	public int insertShortUrl(HashMap<String, Object> shortUrl){
		return sqlSession.insert(namespace+".insertShortUrl", shortUrl);
	}
}
