package ezen.ezencompany.service;

import org.springframework.stereotype.Service;


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
