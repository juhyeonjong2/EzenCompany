package ezen.ezencompany.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
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
	
		
		List<BoardReaderVO> list = boardTypeService.getBoardType(); //모든 보드타입 가져옴
		List<BoardReaderVO> acceptedList = new ArrayList<BoardReaderVO>(); //사용할 보드타입 넣기용

		//모든 보드타입만큼 반복하면서 읽기권한이 일치하는경우 break로 그 값만 얻어냄
		for(int i = 0; i< list.size(); i++){
		  int index = list.get(i).getBindex();
		  boolean isReadable = false; 
		  List<AttributeVO> rlist = boardTypeService.getReader(index); // 모든 보드타입의 읽기권한을 가져옴
		  for(int j = 0; j< rlist.size(); j++) {
	
				  AttributeVO rr =  rlist.get(j); 
			       for(int k=0;k<option.size();k++){ 	
		    	     AttributeVO ev = option.get(k);
					 if(rr.getCidx() ==   ev.getCidx() && rr.getAidx() == ev.getAidx()) {
					 	 isReadable = true;
				         break;
					 }
			       }
			       
			       //만약 트루라면 브레이크(모든 보드만큼 진행하다가)
			       if(isReadable){ 
			    	   break;
			       }
		  	}
		  //트루인 경우 새 리스트에 넣어줌
		  if(isReadable){
		  acceptedList.add(list.get(i));
		  }
		  
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
