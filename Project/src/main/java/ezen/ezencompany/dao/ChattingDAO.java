package ezen.ezencompany.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.MemberVO;

@Repository
public class ChattingDAO {

	//sql을 사용하기 위해서 생성(자동주입)
	@Autowired
	SqlSession sqlSession;
	
	//Mapper의 경로를 적어줌
	private final String namespace = "ezen.ezencompany.mapper.chattingMapper";
	
	public List<AttributeVO> chattingList(){
		return sqlSession.selectList(namespace+".chattingList");
	};
	
	public List<MemberVO> detaleList(){
		return sqlSession.selectList(namespace+".detaleList");
	};
}
