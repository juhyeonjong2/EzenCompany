package ezen.ezencompany.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ezen.ezencompany.vo.BoardAttachVO;
import ezen.ezencompany.vo.BoardReplyVO;
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
	
	public int insertfile(BoardAttachVO vo) {
		return sqlSession.insert("ezen.ezencompany.mapper.boardmapper.insertfile", vo);
	}

	public List<BoardAttachVO> selectFiles(int bno){
		return sqlSession.selectList("ezen.ezencompany.mapper.boardmapper.selectFiles", bno);
	}
	
	public BoardAttachVO selectFile(int bfno){
		return sqlSession.selectOne("ezen.ezencompany.mapper.boardmapper.selectFile", bfno);
	}
	
	
	
	
	
	public List<BoardReplyVO> selectReplyList(int bno) {
		return sqlSession.selectList("ezen.ezencompany.mapper.boardmapper.selectReplyList", bno);
	}
	
	public BoardReplyVO selectReply(int rno) {
		return sqlSession.selectOne("ezen.ezencompany.mapper.boardmapper.selectReply", rno);
	}
	
	public int insertReply(BoardReplyVO vo){
		return sqlSession.insert("ezen.ezencompany.mapper.boardmapper.insertReply", vo);
	}
	
	public int removeReply(int rno) {
		return sqlSession.update("ezen.ezencompany.mapper.boardmapper.removeReply", rno);
	}
	
	public int modifyReply(BoardReplyVO vo) {
		return sqlSession.update("ezen.ezencompany.mapper.boardmapper.modifyReply", vo);
	}
	
}
