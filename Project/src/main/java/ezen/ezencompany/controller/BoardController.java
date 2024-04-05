package ezen.ezencompany.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ezen.ezencompany.service.BoardService;
import ezen.ezencompany.vo.BoardVO;

@RequestMapping(value = "/board")
@Controller
public class BoardController {

	@Autowired
	BoardService boardService;

	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public String list(Model model, String bindex) throws Exception {
		int bindexInt = 1;
		if(bindex != null) {
			bindexInt = Integer.parseInt(bindex);
		}
		 
		List<BoardVO> list = boardService.list(bindexInt);
		model.addAttribute("list", list);
		return "board/list";
	}

	@RequestMapping(value = "/list2.do", method = RequestMethod.GET)
	public String list2(Model model) {
		
		List<String> slist = new ArrayList<String>();
		slist.add("첫번째 데이터");
		slist.add("두번째 데이터");
		slist.add("세번째 데이터");


		model.addAttribute("list", slist);

		return "board/list";
	}
	@RequestMapping(value="/view.do")
	public String view(@RequestParam(value="bno") int bno,Model model) throws Exception {
		
		BoardVO vo = boardService.selectOneByBno(bno);
		
		model.addAttribute("vo",vo);
		
		
		return "board/view";
		
	}
	@RequestMapping(value="/modify.do",method=RequestMethod.GET) 
	public String modify(@RequestParam(value="bno") int bno,Model model) throws Exception {
		
		
		BoardVO vo = boardService.selectOneByBno(bno);
		model.addAttribute("vo",vo);
		
		
		
		return "board/modify";
	}
	
	@RequestMapping(value="/modify.do",method=RequestMethod.POST) 
	public void modify(BoardVO vo, HttpServletResponse res) throws Exception {
		
		
		int result = boardService.update(vo);
		
		
		
		
		res.setContentType("text/html; charset=utf-8");
		res.setCharacterEncoding("UTF-8");
		if(result>0) {
			res.getWriter().append("<script>alert('수정되었습니다.');location.href='list.do'</script>");
		}else {
			res.getWriter().append("<script>alert('수정되지않았습니다.');location.href='list.do'</script>");
		}
		res.getWriter().flush();
		
		
	}
	
	@RequestMapping(value="/delete.do",method=RequestMethod.POST)  
	public String delete(@RequestParam(value="bno") int bno,Model model) throws Exception { 
		
		
		int result = boardService.delete(bno); 
		
		return "redirect:list.do"; 
	}
	
	@RequestMapping(value="/write.do",method=RequestMethod.GET)
	public String write() {
		System.out.println("전송형식이 GET인 write.do");
		return "board/write";
	}
	@RequestMapping(value="/write.do",method=RequestMethod.POST)
	public String write(BoardVO vo) throws Exception {
		boardService.insert(vo);
		
		System.out.println("전송형식이 POST인 write.do");

		return "redirect:list.do";
	}
	
}
