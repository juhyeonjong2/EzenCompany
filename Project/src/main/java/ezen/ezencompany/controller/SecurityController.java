package ezen.ezencompany.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
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
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "member/login";
	}
	
	//비번 찾기로 가기
	@RequestMapping(value = "searchPW", method = RequestMethod.GET)
	public String searchPW() {
		return "member/passwordForget";
	}
	
	//비번 수정 링크로 온 경우
	@RequestMapping(value = "ChangePW", method = RequestMethod.GET)
	public String ChangePW() {
		return "member/passwordModify";
	}
	
	//회원가입으로 가기(임시)
	@RequestMapping(value = "aa", method = RequestMethod.GET)
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
	
	//회원가입을 누른경우
	@RequestMapping(value = "joinOk", method = RequestMethod.POST)
	public String joinOk(MemberVO vo, String checkpassword, HttpServletResponse response,HttpServletRequest request) throws IOException {

		//비번과 비번확인이 일치하는 경우 
		if(vo.getMpassword() == checkpassword) {
			//  링크에 아무 정보 1개가 들어있는 상황이라면
			//	그 정보를 분해해서 업데이트를 한다
		}else {
			response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			
			response
			.getWriter()
			.append("<script>alert('비밀번호가 일치하지 않습니다');location.href='"
					+request.getContextPath()+"/login'</script>").flush();
			//request.getContextPath()이게 없다면 domain/controller/member까지 고정에 +경로이다
			//(다른 컨트롤러에서 이동하는 매퍼사용시 그거 쓸려면 get.context사용해야해(컨트롤러에서 컨트롤러로 이동))
		}
		
		
		
		return "member/login";
	}
	
}
