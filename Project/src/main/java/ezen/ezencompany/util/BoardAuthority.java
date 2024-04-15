package ezen.ezencompany.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ezen.ezencompany.service.BoardTypeService;
import ezen.ezencompany.service.MemberService;
import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.BoardTypeVO;
import ezen.ezencompany.vo.MemberVO;

@Component
public class BoardAuthority {

	@Autowired
	BoardTypeService boardTypeService;
	
	@Autowired
	MemberService memberService;
	
	
	// 넘어온 mno에 해당하는 사원이 읽을수 있는 게시판 목록을 가져온다.
	public List<BoardTypeVO> getReadableList(int mno) {
		
		//로그인한 사원의 옵션정보를 가져온다
		List<AttributeVO> option = memberService.getOptions(mno);
		List<BoardTypeVO> list = boardTypeService.getBoardType(); //모든 보드타입 가져옴
		List<BoardTypeVO> acceptedList = new ArrayList<BoardTypeVO>(); //사용할 보드타입 넣기용
		
		 boolean isAdmin = false;
		 MemberVO member = memberService.getMember(mno);
		 if(member.getAuthority().equals("ROLE_ADMIN"))
		 {
			 isAdmin = true;
		 }
		
		//모든 보드타입만큼 반복하면서 읽기권한이 일치하는경우 break로 그 값만 얻어냄
		for(int i = 0; i< list.size(); i++)
		{
			
		  int index = list.get(i).getBindex();
		  boolean isReadable = false; 
		  if(isAdmin) {
			  // 어드민의 경우 무조건 읽기 가능
			  acceptedList.add(list.get(i));
		  }else 
		  {
			  List<AttributeVO> rlist = boardTypeService.getReader(index); // 모든 보드타입의 읽기권한을 가져옴
			  for(int j = 0; j< rlist.size(); j++) 
			  {
				  AttributeVO rr =  rlist.get(j);
				  //category(cidx)가 0인경우 "모두" 처리한다.
				 if(rr.getCidx() == 0) {
					 isReadable = true;
					 break;
				 }
					 
				  for(int k=0;k<option.size();k++)
				  {
					  AttributeVO ev = option.get(k);
					  if(rr.getCidx() ==   ev.getCidx() && rr.getAidx() == ev.getAidx()) 
					  {
						  isReadable = true;
						  break;
					  }
				  }
				  if(isReadable){ 
					  break;
				  }
			  }
			  //트루인 경우 새 리스트에 넣어줌
			  if(isReadable){
				  acceptedList.add(list.get(i));
			  }
		  }
		}
		
		return acceptedList;
	}
	
	// 넘어온 게시판 타입에 대한 읽기 권한이 있는지 확인한다.
	public boolean isReadable(int boardTypeIndex, int mno ) {
		 // 보드타입은 있는데 reader 권한이 없는경우는 관리자만 접근 가능하다. (차후 적용->관리자인지만 파악되면 바로 return true;)
		 // category(cidx)가 0인경우 "모두" 처리한다.
		
		 List<AttributeVO> list = boardTypeService.getReader(boardTypeIndex); // 보드타입의 읽기권한을 가져옴  
		 List<AttributeVO> option = memberService.getOptions(mno);
		 boolean isReadable = false;
		 
		 // admin 확인
		 MemberVO member = memberService.getMember(mno);
		 if(member.getAuthority().equals("ROLE_ADMIN"))
		 {
			 return true;
		 }
		 
		//list 가 null인경우 관리자만 읽기 가능.
		 if(list == null || list.size() == 0) {
			 return false; 
		 }
		 
		 for(AttributeVO attr : list) 
		 {
			 // category(cidx)가 0인경우 "모두" 처리한다.
			 if(attr.getCidx() == 0) {
				 isReadable = true;
				 break;
			 }
			 
			 for(AttributeVO op : option) 
			 { 	
				 if(attr.getCidx() == op.getCidx() && attr.getAidx() == op.getAidx()) 
				 {
				 	 isReadable = true;
			         break;
				 }
			 }
			 
			 if(isReadable)
			 {
				 break;
		 	 }
	  	}
		 
		return isReadable;
	}
	
	// 넘어온 게시판 타입에 대한 읽기 권한이 있는지 확인한다.
	public boolean isWritable(int boardTypeIndex, int mno ) {
		 // 보드타입은 있는데 writer 권한이 없는경우는 관리자만 접근 가능하다. (차후 적용->관리자인지만 파악되면 바로 return true;)
		 // category(cidx)가 0인경우 "모두" 처리한다.
		
		 List<AttributeVO> list = boardTypeService.getWriter(boardTypeIndex); // 보드타입의 쓰기권한을 가져옴
		 List<AttributeVO> option = memberService.getOptions(mno);
		 boolean isWritable = false;
		 int acceptedCnt = 0;
		 
		// admin 확인
		 MemberVO member = memberService.getMember(mno);
		 if(member.getAuthority().equals("ROLE_ADMIN")) {
			 return true;
		 }
		 
		//list 가 null인경우 쓰기 불가능(관리자만)
		 if(list == null || list.size() == 0) {
			 return false; 
		 }
		 
		 HashSet<Integer> cidxSet = new HashSet<Integer>();
		 for(AttributeVO attr : list) 
		 {
			 // category(cidx)가 0인경우 "모두" 처리한다.
			 if(attr.getCidx() == 0) {
				 isWritable = true;
				 break;
			 }
			 cidxSet.add(attr.getCidx());
			 for(AttributeVO op : option) 
			 { 	
				 if(attr.getCidx() == op.getCidx() && attr.getAidx() == op.getAidx()) 
				 {
					acceptedCnt++;
					break; 
				 }
			 }
	  	}
		
		if(isWritable == false) {
			// 모든 카테고리 조건에 부합한다면.
		  if(acceptedCnt == cidxSet.size()) {
			  isWritable = true;
		  }
		}
		
		return isWritable;
	}

}
