package ezen.ezencompany.service;

import java.util.HashMap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezen.ezencompany.dao.BoardTypeDAO;
import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.BoardTypeVO;
import ezen.ezencompany.vo.BoardVO;


@Service
public class BoardTypeService {
	
	@Autowired
	BoardTypeDAO boardTypeDAO;
	
	
	//게시판의 최신글 5개를 찾음
	public List<BoardVO> boardList(int bindex) {
		return boardTypeDAO.boardList(bindex);
	}
	
	//모든 게시판을 찾음
	public List<BoardTypeVO> getBoardType() {
		return boardTypeDAO.getBoardType();
	}
	
	//모든 게시판 권한을 찾음
	public List<AttributeVO> getReader(int index) {
		return boardTypeDAO.getReader(index);
	}
	//해당 되는 게시판의 쓰기 권한을 반환.
	public List<AttributeVO> getWriter(int index) {
		return boardTypeDAO.getWriter(index);
	}
}
