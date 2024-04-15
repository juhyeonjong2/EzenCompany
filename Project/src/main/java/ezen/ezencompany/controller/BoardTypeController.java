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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ezen.ezencompany.service.BoardTypeService;
import ezen.ezencompany.service.ManagementService;
import ezen.ezencompany.util.BoardAuthority;
import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.BoardTypeVO;
import ezen.ezencompany.vo.BoardVO;
import ezen.ezencompany.vo.CategoryVO;
import ezen.ezencompany.vo.UserVO;


@RequestMapping(value="/board")
@Controller
public class BoardTypeController {
	
	@Autowired
	BoardAuthority boardAuthority;
	
	@Autowired
	BoardTypeService boardTypeService;
	
	@Autowired
	ManagementService managementService;
	
	@Autowired
	SqlSession sqlSession;
	
	@RequestMapping(value="/home")
	public String main(Model model) {
		//Map 형식으로 분류들을 전부 가져온다 -> 키와 벨류가 없는 것도 가져와야해서 리스트로 변경
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		
		int mno = user.getMno();
		List<BoardTypeVO> acceptedList = boardAuthority.getReadableList(mno);

		model.addAttribute("boardType", acceptedList);

		List<BoardVO> board = new ArrayList<BoardVO>();
		for(int i =0; i<acceptedList.size(); i++) {
			int bindex = acceptedList.get(i).getBindex();
			List<BoardVO> boardList= boardTypeService.boardList(bindex);
			board.addAll(boardList);
		};
		
		model.addAttribute("board", board);	
		return "board/main";
	}
	
	@RequestMapping(value="/category/list", method=RequestMethod.GET)
	@ResponseBody
	public List<CategoryVO> getCategorys() {
		return managementService.getCategorys();
	}
	
	@RequestMapping(value="/attribute/list", method=RequestMethod.GET)
	@ResponseBody
	public List<AttributeVO> getAttributes(String code) {
		
		CategoryVO category = managementService.getCategory(code);
		
		if(category == null)
			return null;
		
		return managementService.getAttributes(category.getCidx());
	}
}
