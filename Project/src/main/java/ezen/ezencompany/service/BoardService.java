package ezen.ezencompany.service;

import java.util.List;

import ezen.ezencompany.vo.BlogVO;
import ezen.ezencompany.vo.BoardAttachVO;
import ezen.ezencompany.vo.BoardReplyVO;
import ezen.ezencompany.vo.BoardVO;

public interface BoardService {
	List<BoardVO> list(int bindex);
	
	BoardVO selectOneByBno(int bno) throws Exception; 

	int update(BoardVO vo) throws Exception;

	int delete(int bno) throws Exception;
	
	int insert(BoardVO vo) throws Exception;
	
	int updateBhit(int bno) throws Exception;
	
	int insertfile(BoardAttachVO vo) throws Exception;
	
	BoardVO selectOne(int bgno, boolean force);
	
	List<BoardReplyVO> boardReplyList(int bno);
	BoardReplyVO getReply(int rno);
	int insertReply(BoardReplyVO vo);
	int removeReply(int bno);
	int modifyReply(BoardReplyVO vo);
	List<BoardAttachVO> getFiles(int bgno);
	BoardAttachVO getFile(int bgfno);
	BoardVO getLastOne(int mno, boolean force);
}


