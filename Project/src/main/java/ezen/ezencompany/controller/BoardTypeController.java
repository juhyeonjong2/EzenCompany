package ezen.ezencompany.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ezen.ezencompany.vo.UserVO;


@RequestMapping(value="/board")
@Controller
public class BoardTypeController {
	
	@Autowired
	BoardTypeService boardTypeService;
	
	@RequestMapping(value="/home")
	public String main(Model model) {
		//Map 형식으로 분류들을 전부 가져온다 -> 키와 벨류가 없는 것도 가져와야해서 리스트로 변경
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		
		System.out.println(user.getMno()+"팔번이 나와야해");
		int mno = user.getMno();
		//로그인한 사원의 옵션정보를 가져온다
		List<Object> option 
		= sqlSession.selectList("ezen.ezencompany.mapper.memberMapper.getOption", mno);
		System.out.println(option+"사원옵션"); //이거 체크해봐
		
		//model에 넣을 리스트 생성
		List boardType = new ArrayList();
		List board = new ArrayList();
		//사원옵션만큼 반복해서 내가 읽을 수 있는걸 찾음
		for(int i = 0; i<option.size(); i++) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("cidx", option.cidx[i]);
			map.put("aidx", option.aidx[i]);
			List<BoardReaderVO> vo = boardTypeService.searchRead(map);
			boardType.add(vo);
		}
		System.out.println(boardType+"아아아");
		model.addAttribute("boardType", boardType);
		
		//내가 읽을 수 있는 게시판들을 반복해서 글 5개를 찾음
		for(int i = 0; i<board.size(); i++) {
			List<BoardVO2> boardList= boardTypeService.boardList(board.bindex[i]);
			board.add(boardList);
		}
		
		System.out.println(board+"가가가");
		model.addAttribute("board", board);
		//이렇게 둘다 따로 찾아서 읽을수 있는건 전부 보내고 if문으로 맞는것만 찾아서 나열하면 될듯하다
		
		return "board/main";
	}
}
