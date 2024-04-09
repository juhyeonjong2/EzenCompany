package ezen.ezencompany.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ezen.ezencompany.service.BoardService;
import ezen.ezencompany.vo.BoardAttachVO;
import ezen.ezencompany.vo.BoardVO;
import ezen.ezencompany.vo.UserVO;

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
		

		//조회수 증가 service 추가
		
		// 서비스태우고 dao 태워서 쿼리문 실행되게 만들기
		int result= boardService.updateBhit(bno);
		System.out.println("result::"+result);
		System.out.println("bno::"+bno);
		
		BoardVO vo = boardService.selectOneByBno(bno);
		
		
		// 현재 게시글 번호와 일치하는 전체 첨부파일 목록 데이터 화면으로 보내기
		
		
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
	public void modify(BoardVO vo, HttpServletResponse res, Authentication authentication) throws Exception {
		
		
		UserVO loginVO = (UserVO)authentication.getPrincipal();
		
		vo.setMno(loginVO.getMno());
	
		
		
		
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
	public String write(BoardVO vo, List<MultipartFile> uploadFile) throws Exception {
		
		String path = "D:\\EzenCompany\\Project\\src\\main\\webapp\\resources\\upload\\board";
		File dir = new File(path);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		boardService.insert(vo);
		
		for (MultipartFile multipartFile : uploadFile) {
			if(!multipartFile.getOriginalFilename().isEmpty()) {
				String fileNM = multipartFile.getOriginalFilename(); 
				
				String[] fileNMArr= fileNM.split("\\.");
				String ext =  fileNMArr[fileNMArr.length-1];
				
				String realFileNM = fileNMArr[0]+"001."+ext;
				
				multipartFile.transferTo(new File(path,realFileNM));
				BoardAttachVO baVO = new BoardAttachVO();
				baVO.setBno(vo.getBno());
				baVO.setBforeignname(fileNM);
				baVO.setBfrealname(realFileNM);
				 // 게시물 - 첨부파일 연결고리
				
				boardService.insertfile(baVO);
			}
		}
		
		
		
		
		System.out.println("전송형식이 POST인 write.do");
		
		return "redirect:list.do";
	}
	
	
	
	
}
