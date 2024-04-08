package ezen.ezencompany.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ezen.ezencompany.dao.BoardTypeDAO;
import ezen.ezencompany.vo.BoardReaderVO;
import ezen.ezencompany.vo.BoardVO2;


@Service
public class BoardTypeService {
	
	@Autowired
	BoardTypeDAO boardTypeDAO;
	
	//읽기권한이 있는 게시판을 찾음
	public List<BoardReaderVO> searchRead(HashMap<String, Object> map) {
		return boardTypeDAO.searchRead(map);
	}
	
	//게시판의 최신글 5개를 찾음
	public List<BoardVO2> boardList(int bindex) {
		return boardTypeDAO.boardList(bindex);
	}
}
