package ezen.ezencompany.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ezen.ezencompany.vo.BoardReaderVO;
import ezen.ezencompany.vo.BoardVO2;

@Repository
public class BoardTypeDAO {

	@Autowired
	SqlSession sqlSession;
	
	private final String namespace = "ezen.ezencompany.mapper.boardTypeMapper";
	
	//읽기권한이 있는 게시판을 찾음
	public List<BoardReaderVO> searchRead(HashMap<String, Object> map){
		return sqlSession.selectList(namespace+".searchRead", map);
	}
	
	//읽기권한이 있는 게시판을 찾음
	public List<BoardVO2> boardList(int bindex){
		return sqlSession.selectList(namespace+".boardList", bindex);
	}
}
