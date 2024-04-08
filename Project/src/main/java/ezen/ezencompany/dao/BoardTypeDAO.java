package ezen.ezencompany.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.BoardReaderVO;
import ezen.ezencompany.vo.BoardVO2;

@Repository
public class BoardTypeDAO {

	@Autowired
	SqlSession sqlSession;
	
	private final String namespace = "ezen.ezencompany.mapper.boardTypeMapper";
	
	
	//게시판의 최신글 5개를 찾음
	public List<BoardVO2> boardList(int bindex){
		return sqlSession.selectList(namespace+".boardList", bindex);
	}
	
	//모든 게시판을 찾음
	public List<BoardReaderVO> getBoardType(){
		return sqlSession.selectList(namespace+".getBoardType");
	}
	
	//모든 게시판을 찾음
	public List<AttributeVO> getReader(int index){
		return sqlSession.selectList(namespace+".getReader", index);
	}
}
