package ezen.ezencompany.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ezen.ezencompany.service.MemberService;
import ezen.ezencompany.vo.MemberVO;

@Controller
public class SecurityController {
	
	//값을 가져오기 위해 어노테이션 명시
	@Autowired
	MemberService memberService;
	
	//로그인으로 돌아오기
	@RequestMapping(value = "/login")
	public String login() {
		return "member/login";
	}
	
	//비번 찾기로 가기
	@RequestMapping(value = "pwSearch", method = RequestMethod.GET)
	public String searchPW() {
		return "member/passwordSearch";
	}
	
	//비번 수정 링크로 온 경우
	@RequestMapping(value = "pwChange", method = RequestMethod.GET)
	public String ChangePW() {
		return "member/passwordModify";
	}
	
	//회원가입으로 가기(임시)
	@RequestMapping(value = "join", method = RequestMethod.GET)
	public String aa() {
		return "member/join";
	}
	
	//중복확인을 누른경우
	@RequestMapping(value = "checkID.do", method = RequestMethod.GET)
	@ResponseBody //ajax를 사용하는경우 @ResponseBody이걸 사용하지 않으면 리턴값으로 보내지 않고 경로로 인식해서 보내버린다
	public String checkID(String text) {
		int idCnt = memberService.checkID(text);
		if(idCnt == 0) {
			return "true";
		}else {
			return "false";
		}
	}
	// 경로로 온 경우 값 받아오는 예시
	//@RequestMapping(value = "joinOk/{id}", method = RequestMethod.POST)
	//public void joinOk(String mid, String mpassword, String checkpassword, @PathVariable("id") String id,
	//		HttpServletResponse response,HttpServletRequest request) throws IOException {
	
	
	//회원가입을 누른경우
	@RequestMapping(value = "joinOk", method = RequestMethod.POST)
	public void joinOk(String mid, String mpassword, String checkpassword,
						HttpServletResponse response,HttpServletRequest request) throws IOException {

		//비번과 비번확인이 일치하는 경우 (String타입이기에 내용비교함(참조경로는 달라서))
		if(mpassword.equals(checkpassword)) {
			//  링크에 아무 정보 1개가 들어있는 상황이라면(aws하는법 배우고 적용)
			//	그 정보를 분해해서 업데이트를 한다 (메일발송테이블에 asdfghjkl정보가 있고(db에 임시로 넣어둠) 이걸 mno로 수정후 업데이트)
			
			//링크데이터를 mno로 변환(안에 짧은 경로를 집어넣으면됨)
			int mno = memberService.requestMno("asdfghjkl6");
			
			//비밀번호 인코딩
			BCryptPasswordEncoder epwe = new BCryptPasswordEncoder();
			
			//객체를 만들어서 파라미터 값을 담는다
			MemberVO vo = new MemberVO();
			vo.setMid(mid);
			vo.setMpassword(epwe.encode(mpassword));
			vo.setMno(mno);
			
			//관리자에 의해 만들어진 계정에 업데이트
			int result = memberService.joinOk(vo);
			
			//회원가입 성공메세지와 로그인창으로 이동
			//응답할때 인코딩하기
			response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			
			response
			.getWriter()
			.append("<script>alert('회원가입에 성공하셨습니다. 로그인을 해주세요');location.href='"
					+request.getContextPath()+"/login'</script>").flush();
			
		}else {
			//응답할때 인코딩하기
			response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			
			response
			.getWriter() //servlet에서의 응답은 text로 보내기때문에 택스트로 코드를 짜서 보낸다
			.append("<script>alert('비밀번호가 일치하지 않습니다');location.href='"
					+request.getContextPath()+"/join'</script>").flush();
			//request.getContextPath()이게 없다면 domain/controller/member까지 고정에 +경로이다
			//(다른 컨트롤러에서 이동하는 매퍼사용시 그거 쓸려면 get.context사용해야해(컨트롤러에서 컨트롤러로 이동))
		}

	} //join
	
}
