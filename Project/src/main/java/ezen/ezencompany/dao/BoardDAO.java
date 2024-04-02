package ezen.ezencompany.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ezen.ezencompany.vo.BoardVO;

@Repository
public class BoardDAO {

	@Autowired
	SqlSession sqlSession;
	
	private final String namespace = "ezen.ezencompany.mapper.boardMapper";
	
	public List<BoardVO> list(){
		return sqlSession.selectList("ezen.ezencompany.mapper.boardmapper.list"); 
	}
}
