package ezen.ezencompany.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminDAO {
	
	//sql을 사용하기 위해서 생성(자동주입)
	@Autowired
	SqlSession sqlSession;
	
	//Mapper의 경로를 적어줌
	private final String namespace = "ezen.ezencompany.mapper.memberMapper";
	
	public int memberRegistration(List names){
		return sqlSession.insert(namespace+".memberRegistration", names);
	}
	
	public int employeeRegistration(String aa){
		return sqlSession.insert(namespace+".employeeRegistration", aa);
	}
}
