package ezen.ezencompany.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ezen.ezencompany.service.BoardTypeService;
import ezen.ezencompany.util.BoardAuthority;
import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.BoardTypeVO;
import ezen.ezencompany.vo.BoardVO2;
import ezen.ezencompany.vo.UserVO;


@RequestMapping(value="/board")
@Controller
public class BoardTypeController {
	
	@Autowired
	BoardAuthority boardAuthority;
	
	@Autowired
	BoardTypeService boardTypeService;
	
	@Autowired
	SqlSession sqlSession;
	
	@RequestMapping(value="/home")
	public String main(Model model) {
		//Map 형식으로 분류들을 전부 가져온다 -> 키와 벨류가 없는 것도 가져와야해서 리스트로 변경
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		
		int mno = user.getMno();
		List<BoardTypeVO> acceptedList = boardAuthority.getReadableList(mno);
		System.out.println(acceptedList);
		for(BoardTypeVO vo : acceptedList) {
			System.out.println(vo.getBindex());
			System.out.println(vo.getBtname());
		}
		
		model.addAttribute("boardType", acceptedList);

		List<BoardVO2> board = new ArrayList<BoardVO2>();
		for(int i =0; i<acceptedList.size(); i++) {
			int bindex = acceptedList.get(i).getBindex();
			List<BoardVO2> boardList= boardTypeService.boardList(bindex);
			board.addAll(boardList);
		};
		
		model.addAttribute("board", board);	
		return "board/main";
	}
}
