package ezen.ezencompany.service;

import java.util.List;

import ezen.ezencompany.vo.BoardAttachVO;
import ezen.ezencompany.vo.BoardVO;

public interface BoardService {
	List<BoardVO> list(int bindex);
	
	BoardVO selectOneByBno(int bno) throws Exception; 

	int update(BoardVO vo) throws Exception;

	int delete(int bno) throws Exception;
	
	int insert(BoardVO vo) throws Exception;
	
	int updateBhit(int bno) throws Exception;
	
	int insertfile(BoardAttachVO vo) throws Exception;
	
}


