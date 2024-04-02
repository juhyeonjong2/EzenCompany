package ezen.ezencompany.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ezen.ezencompany.dao.MemberDAO;
import ezen.ezencompany.service.MemberService;
import ezen.ezencompany.vo.MemberVO;

@Controller
public class SecurityController {
	
	//값을 가져오기 위해 어노테이션 명시
	@Autowired
	MemberService memberService;
	
	@Autowired
	MemberDAO memberDAO;
	
	//이메일 보내는 객체
	@Autowired
	JavaMailSenderImpl mailSender;
	
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
	@RequestMapping(value = "join/{id}", method = RequestMethod.GET)
	public String aa(@PathVariable("id") String id, Model model) {
		model.addAttribute("shortUrl", id);
		return "member/join";
	}
	
	//중복확인을 누른경우
	@RequestMapping(value = "checkID", method = RequestMethod.GET)
	@ResponseBody //ajax를 사용하는경우 @ResponseBody이걸 사용하지 않으면 리턴값으로 보내지 않고 경로로 인식해서 보내버린다
	public String checkID(String text) {
		int idCnt = memberService.checkID(text);
		if(idCnt == 0) {
			return "true";
		}else {
			return "false";
		}
	}
	
	
	//인증번호 받기를 누른경우 짧은경로로 이메일 찾기
	@RequestMapping(value = "getEmail2", method = RequestMethod.POST)
	@ResponseBody //ajax를 사용하는경우 @ResponseBody이걸 사용하지 않으면 리턴값으로 보내지 않고 경로로 인식해서 보내버린다
	public String getEmail(String url) {
		int mno = memberService.requestMno(url);
		String email = memberDAO.getEmail2(mno);
		return email;
	}
	
	//메일발송
	@RequestMapping(value = "certSend", method = RequestMethod.POST)
	@ResponseBody 
	public int emailAuth(String email) {
		//난수의 범위 111111 ~ 999999 (6자리 난수) (0~888888)+111111
		Random random = new Random();
		int checkNum = random.nextInt(888888)+111111;

		//이메일 보낼 양식 
		String setFrom = "vhahaha513@naver.com"; //2단계 인증 x, 메일 설정에서 POP/IMAP 사용 설정에서 POP/SMTP 사용함으로 설정o
		String toMail = email;
		String title = "[이젠컴퍼니]회원가입의 인증번호 입니다.";
		String content = "인증번호는 " + checkNum + " 입니다." +
						 "<br>" +
						 "해당 링크로 들어가서 회원가입을 해주세요.";
		//여기서 이메일 인증테이블에 인증번호와 이메일을 적어서 담아준다
		//단 이 경우에 인증번호를 두번 누른경우 두개가 생성되는데 그걸 전부 친 경우 다 되면 안되니
		//일단 셀렉트로 이메일을 찾은 뒤 없다면 인서트 있다면 업데이트 방식으로 해야할듯하다
		int num = memberService.selectNum(email);
		System.out.println(num);
		if(num == 0) {
		// 저장된 인증번호가 없다면 insert
			HashMap<String, Object> map = new HashMap<>();
			map.put("email", email);
			map.put("num", checkNum);
			memberService.insertNum(map);
		}else {
		// 저장된 인증번호가 있다면 update
			HashMap<String, Object> map = new HashMap<>();
			map.put("email", email);
			map.put("num", checkNum);
			memberService.updateNum(map);
		}
		
		try {
			//System.out.println("트라이 실행");
			MimeMessage message = mailSender.createMimeMessage(); //Spring에서 제공하는 mail API
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content, true);
            //System.out.println("메일 준비 끝");
            mailSender.send(message);
            //System.out.println("메일 보냄");
		}catch (Exception e) {
			//System.out.println("메일실패");
			e.printStackTrace();
		}
		
		return checkNum;
	}
	
	// 경로로 온 경우 값 받아오는 예시
	//@RequestMapping(value = "joinOk/{id}", method = RequestMethod.POST)
	//public void joinOk(String mid, String mpassword, String checkpassword, @PathVariable("id") String id,
	//		HttpServletResponse response,HttpServletRequest request) throws IOException {
	// model 안에 id값을 집어넣는다 그리고 회원가입창을 호출
	
	//회원가입을 누른경우 인증번호 체크
	@RequestMapping(value = "checkCert", method = RequestMethod.POST)
	@ResponseBody //ajax를 사용하는경우 @ResponseBody이걸 사용하지 않으면 리턴값으로 보내지 않고 경로로 인식해서 보내버린다
	public String getEmail(String email, int certNum) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("email", email);
		map.put("certNum", certNum);
		int checkCert = memberService.checkCert(map);
		if(checkCert != 0) {
			//셀렉트 해서 값이 일치한다면
			return "true";
		}else {
			return "false";
		}
	}
	
	//회원가입을 누른경우
	@RequestMapping(value = "joinOk", method = RequestMethod.POST)
	public void joinOk(String mid, String mpassword, String shortUrl,
						HttpServletResponse response,HttpServletRequest request) throws IOException {

		//링크데이터를 mno로 변환(안에 짧은 경로를 집어넣으면됨)
		int mno = memberService.requestMno(shortUrl);
		
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

	} //join
	
	//관리자 로그인 페이지
	@RequestMapping(value = "a_4min")
	public String adminPage() {
		return "member/adminLogin";
	}
	
	//관리자가 로그인 한 경우
	//@RequestMapping(value = "adminLogin")
	//public String adminLogin() {
		//memberService.adminLogin
		//이렇게 만들려고 했지만 그러면 관리자는 따로 세션에 추가하고 그렇게 사용해야 하고 일반 사원은 또 달라서 별로 좋은방법은 아닌듯 하다 
	//	return "member/adminLogin";
	//}
	
}
