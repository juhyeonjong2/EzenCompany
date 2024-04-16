package ezen.ezencompany.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ezen.ezencompany.service.BoardService;
import ezen.ezencompany.service.BoardTypeService;
import ezen.ezencompany.service.MemberService;
import ezen.ezencompany.util.BoardAuthority;
import ezen.ezencompany.util.Path;
import ezen.ezencompany.vo.BoardAttachVO;
import ezen.ezencompany.vo.BoardReplyVO;
import ezen.ezencompany.vo.BoardTypeVO;
import ezen.ezencompany.vo.BoardVO;
import ezen.ezencompany.vo.MemberVO;
import ezen.ezencompany.vo.UserVO;

@RequestMapping(value = "/board")
@Controller
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	BoardTypeService boardTypeService;
	
	@Autowired
	BoardAuthority boardAuthority;
	
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public String list(Model model, String bindex) throws Exception {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		int mno = user.getMno();
		
		int bindexInt = 1;
		if(bindex != null) {
			bindexInt = Integer.parseInt(bindex);
		}
		
		boolean isWritable = boardAuthority.isWritable(bindexInt, mno);
		model.addAttribute("isWritable", isWritable);
		
		List<BoardVO> list = boardService.list(bindexInt);
		model.addAttribute("list", list);

		List<BoardTypeVO> acceptedList = boardAuthority.getReadableList(mno);

		model.addAttribute("boardType", acceptedList);

		List<BoardVO> board = new ArrayList<BoardVO>();
		for(int i =0; i<acceptedList.size(); i++) {
			int bindex2 = acceptedList.get(i).getBindex();
			List<BoardVO> boardList= boardTypeService.boardList(bindex2);
			board.addAll(boardList);
		};
		
		model.addAttribute("board", board);
		
		
		
		String btname = boardTypeService.getBtname(bindexInt);
		model.addAttribute("btname", btname);
		
		
		
		
		
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

		//조회수 증가 service 추가
		
		// 서비스태우고 dao 태워서 쿼리문 실행되게 만들기
		int result= boardService.updateBhit(bno);
		System.out.println("result::"+result);
		System.out.println("bno::"+bno);
		
		BoardVO vo = boardService.selectOneByBno(bno);
		
		
		// 현재 게시글 번호와 일치하는 전체 첨부파일 목록 데이터 화면으로 보내기
		
		
		model.addAttribute("vo",vo);
		
		
		List<BoardAttachVO> files = boardService.getFiles(bno);
		model.addAttribute("files", files);
		
		return "board/view";
		
	}
	@RequestMapping(value="/modify.do",method=RequestMethod.GET) 
	public String modify(@RequestParam(value="bno") int bno,Model model) throws Exception {
		
		
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
		
		
		
		BoardVO vo = boardService.selectOneByBno(bno);
		model.addAttribute("vo",vo);
		
		//2개 값받았을때 비교해서 맞으면 트루 아니면 폴스로 권한 설정
		
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
	public String write(@RequestParam(value="bindex") int btno, Model model ) {
		
		
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
		
		
		
		boolean isWritable = boardAuthority.isWritable(btno, mno);
		if(isWritable == false) {
			return "redirect:list.do";
		}
		
		
		return "board/write";
	}
	
	@RequestMapping(value="/write.do",method=RequestMethod.POST)
	public String write(HttpServletRequest request, BoardVO vo, List<MultipartFile> uploadFile) throws Exception {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserVO user = (UserVO) authentication.getPrincipal();
		int mno = user.getMno();
		
		vo.setMno(mno);
		
		int result = boardService.insert(vo);
		if(result > 0) {
		for (MultipartFile multipartFile : uploadFile) {
			if(!multipartFile.getOriginalFilename().isEmpty()) {
				
				String originfileName = multipartFile.getOriginalFilename();
				
				// 업로드 경로 : basePath + upload/blog/{bgno}
				String [] subPath =  {"upload", "board", Integer.toString(vo.getBno())};
				String basePath = request.getSession().getServletContext().getRealPath("resources");
				String fileName = UUID.randomUUID().toString(); // 중복방지를 위한 랜덤 이름.
				StringBuffer buffer = new StringBuffer();
				buffer.append(fileName);
				buffer.append("_");
				buffer.append(originfileName); // 확장자까지
				String realFileName = buffer.toString();
				
				
				String uploadPath = Path.getUploadPath(basePath, subPath);
				multipartFile.transferTo(new File(uploadPath,realFileName)); // 저장
				
				BoardAttachVO attach = new BoardAttachVO();
				attach.setBno(vo.getBno());
				attach.setBforeignname(originfileName);
				attach.setBfrealname(realFileName);
				
				boardService.insertfile(attach);
			}
		}
		}
		return "redirect:list.do?bindex="+vo.getBindex();
	}
		@RequestMapping(value="/reply/write", method=RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> replyWriteOk(int bno, int rpno, String content) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserVO user = (UserVO) authentication.getPrincipal();
			int mno = user.getMno();
			
			BoardVO board = boardService.selectOne(bno, true);
			int ownerMno = board.getMno();
			
			
			BoardReplyVO vo = new BoardReplyVO();
			vo.setMno(mno);
			vo.setBno(bno);
			vo.setRpno(rpno);
			vo.setRcontent(content);
			
			int result = boardService.insertReply(vo);
			
			// 반환값 생성 
			Map<String,Object> resMap = new HashMap<String,Object>();
			if(result > 0 ) {
				MemberVO member = memberService.getMember(mno);
				vo = boardService.getReply(vo.getBno());
				vo.setAuthor(member.getMname());	
				vo.setEditable(true);
				
				// 블로그 주인장이 작성한 댓글
				if(mno == ownerMno) {
					vo.setMaster(true);
				}

				resMap.put("result", "SUCCESS"); //  성공
				resMap.put("total", boardService.boardReplyList(bno).size()); //  갯수
				resMap.put("data", vo); 
				
			}
			else {
				resMap.put("result", "FAIL"); //  실패
			}
			
			return resMap;
		
		}
		
		@RequestMapping(value="/reply/list", method=RequestMethod.GET)
		@ResponseBody
		public Map<String,Object> replyList(int bno) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserVO user = (UserVO) authentication.getPrincipal();
			int mno = user.getMno();
			
			BoardVO board = boardService.selectOne(bno, true);
			int ownerMno = board.getMno();
			
			List<BoardReplyVO> list =   boardService.boardReplyList(bno);
			for(BoardReplyVO vo : list) {
				MemberVO member = memberService.getMember(vo.getMno());
				vo.setAuthor(member.getMname());
				
				// 내가 작성한 댓글.
				if(vo.getMno() == mno) {
					vo.setEditable(true);
				}
				
				// 블로그 주인장이 작성한 댓글
				if(vo.getMno() == ownerMno) {
					vo.setMaster(true);
				}
				
				// 삭제된 댓글 확인.
				if(vo.getDelyn().equals("y")) {
					vo.setRcontent("삭제된 글입니다.");
					vo.setEditable(false);
				}
			}
			
			// 반환값 생성 
			Map<String,Object> resMap = new HashMap<String,Object>();
			resMap.put("total", list.size()); //  갯수
			resMap.put("list", list); //  
			
			
			return resMap;
		
		}
		
		@RequestMapping(value="/reply/modify", method=RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> replyModifyOk(int rno, String content) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserVO user = (UserVO) authentication.getPrincipal();
			int mno = user.getMno();
			
			Map<String,Object> resMap = new HashMap<String,Object>();
			
			/// 내가 쓴 reply인지 확인.
			BoardReplyVO vo = boardService.getReply(rno);
			if(vo.getMno() != mno) {
				
				// 타인이 작성한 댓글인데 수정이 들어옴 : 오류 처리
				resMap.put("result", "FAIL"); //  실패
			} else {
				
				// 내가 작성한 글이 맞음.
				vo.setRcontent(content);
				
				// 수정
				int result = boardService.modifyReply(vo);
				if(result > 0 ) {
					vo = boardService.getReply(vo.getRno());
					
					BoardVO board = boardService.selectOne(vo.getBno(), true);
					int ownerMno = board.getMno();
					MemberVO member = memberService.getMember(mno);
					vo.setAuthor(member.getMname());
					vo.setEditable(true);
					// 블로그 주인장이 작성한 댓글
					if(mno == ownerMno) {
						vo.setMaster(true);
					}
					
					
					resMap.put("result", "SUCCESS"); //  성공
					resMap.put("total", boardService.boardReplyList(vo.getBno()).size()); //  갯수
					resMap.put("data", vo); 
				}
				else {
					resMap.put("result", "FAIL"); //  실패
				}
			}
			
			return resMap;
		
		}
		
		@RequestMapping(value="/reply/remove", method=RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> replyRemoveOk(int rno) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserVO user = (UserVO) authentication.getPrincipal();
			int mno = user.getMno();
			
			System.out.println("replyRemoveOk");
			Map<String,Object> resMap = new HashMap<String,Object>();
			
			/// 내가 쓴 reply인지 확인.
			BoardReplyVO vo = boardService.getReply(rno);
			if(vo.getMno() != mno) {
				
				// 타인이 작성한 댓글인데 수정이 들어옴 : 오류 처리
				resMap.put("result", "FAIL"); //  실패
			} else {
				
				int result = boardService.removeReply(vo.getRno());
				if(result > 0 ) {
					vo = boardService.getReply(vo.getRno());
					
					BoardVO board = boardService.selectOne(vo.getBno(), true);
					int ownerMno = board.getMno();
					MemberVO member = memberService.getMember(mno);
					vo.setAuthor(member.getMname());
					vo.setEditable(false); // 이미 삭제된 댓글이므로 수정/삭제 버튼 x
					
					// 블로그 주인장이 작성한 댓글
					if(mno == ownerMno) {
						vo.setMaster(true);
					}
					

					// 삭제된 댓글 확인.
					if(vo.getDelyn().equals("y")) {
						vo.setRcontent("삭제된 글입니다.");
					}
					
					resMap.put("result", "SUCCESS"); //  성공
					resMap.put("total", boardService.boardReplyList(vo.getBno()).size()); //  갯수
					resMap.put("data", vo); 
				}
				else {
					resMap.put("result", "FAIL"); //  실패
				}
			}
			
			return resMap;
		
		}
		
		
		
		
	
	
	@GetMapping("/download/{bfno}")
	public void download(HttpServletRequest request, Model model, @PathVariable int bfno, HttpServletResponse response) throws Exception {
		
			 BoardAttachVO vo  = boardService.getFile(bfno);
			 
			 // 실제 파일경로 구하기.
			 String [] subPath =  {"upload", "board", Integer.toString(vo.getBno())};
			 String basePath = request.getSession().getServletContext().getRealPath("resources");
			 
			 String uploadPath = Path.getUploadPath(basePath, subPath);
			 
			 File downloadFile = new File(uploadPath,vo.getBfrealname());
			 byte fileByte[] =  FileUtils.readFileToByteArray(downloadFile);
			 response.setContentType("application/octet-stream");
			 response.setContentLength(fileByte.length);
			 response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(vo.getBforeignname(),"UTF-8") + "\";" );	
			 response.setHeader("Content-Transfer-Encoding", "binary;");
			 response.getOutputStream().write(fileByte);
			 response.getOutputStream().flush();
			 response.getOutputStream().close();
	}
	
	
	
}
