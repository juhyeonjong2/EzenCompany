package ezen.ezencompany.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ezen.ezencompany.service.BoardService;
import ezen.ezencompany.vo.BoardVO;

@Controller
public class BoardController {
	
	@Autowired
	BoardService boardService;

	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public String list(Model model) throws Exception {
		// board 테이블의 데이터 목록 가져오기
		List<BoardVO> list = boardService.list();
		model.addAttribute("list", list);
		return "board/list";
	}
		
	@RequestMapping(value = "/list2.do", method = RequestMethod.GET)
	public String list2(Model model) {
		// 비지니스 로직 -> DB에 존재하는 게시글 목록 가져오기
		List<String> slist = new ArrayList<String>();
		slist.add("첫번째 데이터");
		slist.add("두번째 데이터");
		slist.add("세번째 데이터");

		// Model 객체는 controller 에서 화면으로 데이터를 전달 하는 객체 이다.
		// Model 객체는 반드시 dispatcherservlet이 전달하는 객체로만 데이터를 전송할 수 있다.
		// dispatcherservlet을 통해 Model 객체를 받는 방법은 매개변수이다.

		model.addAttribute("list", slist);// request.setAttribute("list",slist);

		return "board/list";
}
}