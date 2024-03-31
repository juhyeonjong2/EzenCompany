package ezen.ezencompany.controller;

import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ezen.ezencompany.dao.MemberDAO;
import ezen.ezencompany.service.MemberService;

@Controller
public class MemberController {
	
	//값을 가져오기 위해 어노테이션 명시
	@Autowired
	MemberService memberService;
	
	@Autowired
	MemberDAO memberDAO;
	
	//이메일 보내는 객체
	@Autowired
	JavaMailSenderImpl mailSender;
	
	//비밀번호 재설정버튼을 누른경우
	//email찾기
	@RequestMapping(value = "getEmail", method = RequestMethod.POST)
	@ResponseBody //ajax를 사용하는경우 @ResponseBody이걸 사용하지 않으면 리턴값으로 보내지 않고 경로로 인식해서 보내버린다
	public String getEmail(String mid) {
		String email = memberDAO.getEmail(mid);
		return email;
	}
	
	//메일발송
	@RequestMapping(value = "emailSend", method = RequestMethod.POST)
	@ResponseBody 
	public int emailAuth(String email) {
		//난수의 범위 111111 ~ 999999 (6자리 난수) (0~888888)+111111
		Random random = new Random();
		int checkNum = random.nextInt(888888)+111111;
		
		//이메일 보낼 양식 
		String setFrom = "vhahaha513@naver.com"; //2단계 인증 x, 메일 설정에서 POP/IMAP 사용 설정에서 POP/SMTP 사용함으로 설정o
		String toMail = email;
		String title = "[이젠컴퍼니]비밀번호 재설정 링크 입니다.";
		String content = "인증번호는 " + checkNum + " 입니다." +
						 "<br>" +
						 "해당 링크로 들어가서 비밀번호 재설정을 해주세요.";
		
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
}
