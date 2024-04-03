package ezen.ezencompany.service;

import java.util.List;

import ezen.ezencompany.vo.BoardVO;

public interface BoardService {
	List<BoardVO> list();
	
	BoardVO selectOneByBno(int bno) throws Exception; 

	int update(BoardVO vo) throws Exception;

	int delete(int bno) throws Exception;
}


