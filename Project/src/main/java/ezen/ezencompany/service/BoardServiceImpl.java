package ezen.ezencompany.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezen.ezencompany.dao.BoardDAO;
import ezen.ezencompany.vo.BlogVO;
import ezen.ezencompany.vo.BoardAttachVO;
import ezen.ezencompany.vo.BoardReplyVO;
import ezen.ezencompany.vo.BoardVO;


@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDAO boardDAO;
	
	@Override
	public List<BoardVO> list(int bindex){
		
		List<BoardVO> blist = boardDAO.list(bindex);
		return blist;
	}
	
	@Override
	public BoardVO selectOneByBno(int bno) throws Exception {
		
		BoardVO bvo = boardDAO.selectOneByBno(bno);
		return bvo;
	}
	
	@Override
	public int update(BoardVO vo) throws Exception {
		return boardDAO.update(vo);
	}
	
	@Override
	public int delete(int bno) throws Exception {
		return boardDAO.delete(bno);
	}
	
	@Override
	public int insert(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		return boardDAO.insert(vo);
	}
	
	
	@Override
	public int updateBhit(int bno) throws Exception {
		
		int result = boardDAO.updateBhit(bno);  
		return result;
	}
	
	
	@Override
	public int insertfile(BoardAttachVO vo) throws Exception {
		
		int result = boardDAO.insertfile(vo);
		return result;
		
	
	}
	@Override
	public List<BoardAttachVO> getFiles(int bno) {
 
		return boardDAO.selectFiles(bno);
	}

	@Override
	public BoardAttachVO getFile(int bfno) {
		return boardDAO.selectFile(bfno);
	}

	@Override
	public BoardVO selectOne(int bgno, boolean force) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BoardReplyVO> boardReplyList(int bno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoardReplyVO getReply(int rno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertReply(BoardReplyVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removeReply(int bno) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int modifyReply(BoardReplyVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public BoardVO getLastOne(int mno, boolean force) {
		if(force) {
			return boardDAO.selectLastForce(mno);
		}
		return boardDAO.selectLast(mno);
	}
}