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
	
	public List<BoardVO> list(int bindex){
		return sqlSession.selectList("ezen.ezencompany.mapper.boardmapper.list",bindex); 
	}
	
	public BoardVO selectOneByBno(int bno) {
		return sqlSession.selectOne("ezen.ezencompany.mapper.boardmapper.selectOneByBno", bno);
	}
	
	public int update(BoardVO vo) {
		return sqlSession.update("ezen.ezencompany.mapper.boardmapper.update", vo);
		
	}

	public int delete(int bno) {
		return sqlSession.insert("ezen.ezencompany.mapper.boardmapper.delete", bno);
	}
	
	public int insert(BoardVO vo) {
		return sqlSession.insert("ezen.ezencompany.mapper.boardmapper.insert", vo);
	}
	
	public int updateBhit(int bno) {
		 return sqlSession.update("ezen.ezencompany.mapper.boardmapper.updateBhit", bno); 
	}
	
	public int upload(BoardVO uploadFile) {
		return sqlSession.insert("ezen.ezencompany.mapper.boardmapper.upload", uploadFile);
	}
}
