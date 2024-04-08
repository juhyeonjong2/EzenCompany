package ezen.ezencompany.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezen.ezencompany.dao.BoardDAO;
import ezen.ezencompany.vo.BoardVO;

@Service
public class BoardServicelmpl implements BoardService {

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
		
		
		return boardDAO.updateBhit(bno);
	}
	
}