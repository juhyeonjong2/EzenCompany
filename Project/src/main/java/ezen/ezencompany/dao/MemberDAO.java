package ezen.ezencompany.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO {
	
	//sql을 사용하기 위해서 생성(자동주입)
	@Autowired
	SqlSession sqlSession;
	
	//Mapper의 경로를 적어줌
	private final String namespace = "ezen.ezencompany.mapper.memberMapper";
	
	//사용할 sql문을 호출함
	public int checkID(String text){
		return sqlSession.selectOne(namespace+".checkID", text);
	}
	
}
