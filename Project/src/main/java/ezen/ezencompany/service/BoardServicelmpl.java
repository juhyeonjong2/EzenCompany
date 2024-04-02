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
	public List<BoardVO> list(){
		
		List<BoardVO> blist = boardDAO.list();
		return blist;
	}
	
	
}