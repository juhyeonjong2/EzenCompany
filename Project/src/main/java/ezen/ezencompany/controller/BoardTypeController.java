package ezen.ezencompany.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ezen.ezencompany.service.BoardTypeService;
import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.BoardReaderVO;
import ezen.ezencompany.vo.BoardVO2;
import ezen.ezencompany.vo.UserVO;


@RequestMapping(value="/board")
@Controller
public class BoardTypeController {
	
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
		//로그인한 사원의 옵션정보를 가져온다
		List<AttributeVO> option 
		= sqlSession.selectList("ezen.ezencompany.mapper.memberMapper.getOption", mno);
		
		//model에 넣을 리스트 생성
		List<BoardReaderVO> boardType = new ArrayList<BoardReaderVO>();
		List<BoardVO2> board = new ArrayList<BoardVO2>();
		//사원옵션만큼 반복해서 내가 읽을 수 있는걸 찾음
		for(int i = 0; i<option.size(); i++) {		
			HashMap<String, Object> map = new HashMap<>();
			map.put("cidx", option.get(i).getCidx());
			map.put("aidx", option.get(i).getAidx());
			List<BoardReaderVO> vo = boardTypeService.searchRead(map);
			boardType.addAll(vo);
		}
		System.out.println(boardType);
		model.addAttribute("boardType", boardType);
		
		//내가 읽을 수 있는 게시판들을 반복해서 글 5개를 찾음
		for(int i = 0; i<boardType.size(); i++) {
			System.out.println(boardType.get(i)); //6개 정보
			System.out.println(boardType.get(i).getBindex()); //1,1,2
			List<BoardVO2> boardList= boardTypeService.boardList(boardType.get(i).getBindex());
			board.addAll(boardList);
		}
		
		System.out.println(board+"가가가"); //3개정보
		model.addAttribute("board", board);
		//이렇게 둘다 따로 찾아서 읽을수 있는건 전부 보내고 if문으로 맞는것만 찾아서 나열하면 될듯하다
		
		return "board/main";
	}
}
