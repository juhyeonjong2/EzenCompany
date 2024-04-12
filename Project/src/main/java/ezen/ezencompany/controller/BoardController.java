package ezen.ezencompany.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import ezen.ezencompany.service.MemberService;
import ezen.ezencompany.util.Path;
import ezen.ezencompany.vo.BlogAttachVO;
import ezen.ezencompany.vo.BlogVO;
import ezen.ezencompany.vo.BoardAttachVO;
import ezen.ezencompany.vo.BoardReplyVO;
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
	
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public String list(Model model, String bindex) throws Exception {
		int bindexInt = 1;
		if(bindex != null) {
			bindexInt = Integer.parseInt(bindex);
		}
		 
		
		
		List<BoardVO> list = boardService.list(bindexInt);
		model.addAttribute("list", list);
		return "board/list";
		
		model.addAttribute("retiredEmployees",retiredEmployees);
		model.addAttribute("blogUsers", blogUsers);
				
		// 작성자 이름 및 프로필 정보
		model.addAttribute("writer", user.getMname());
		String profileSrc = request.getContextPath() + "/resources/icon/user.png"; // 기본 아이콘이고 변경 해야함.
		model.addAttribute("profileImage", profileSrc);
		
		// 가장최근에 쓴 블로그
		BlogVO vo = blogService.getLastOne(user.getMno(), true);
		model.addAttribute("vo", vo);
		if(vo!=null) {
		model.addAttribute("bno", vo.getBgno()); // bgno
		
		// 첨부 파일들
		List<BlogAttachVO> files = blogService.getFiles(vo.getBgno());
		model.addAttribute("files", files);
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
				
				String realNM = fileNMArr[0]+"001."+ext;
				
				multipartFile.transferTo(new File(path,realNM));
				BoardAttachVO baVO = new BoardAttachVO();
				baVO.setBno(vo.getBno());
				baVO.setBforeignname(fileNM);
				baVO.setBfrealname(realNM);
				 // 게시물 - 첨부파일 연결고리
				
				boardService.insertfile(baVO);
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
	public void download(Model model, @PathVariable int bfno, HttpServletResponse response) throws Exception {
		
			 BoardAttachVO vo  = boardService.getFile(bfno);
			 
			 // 실제 파일경로 구하기.
			 String [] subPath =  {"upload", "board", Integer.toString(vo.getBno())};
			 String uploadPath = Path.getUploadPath(subPath);
			 
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
