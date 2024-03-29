package ezen.ezencompany.controller;

import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ezen.ezencompany.service.AdminService;
import ezen.ezencompany.service.MemberService;
import ezen.ezencompany.vo.MemberVO;

@RequestMapping(value="/admin")
@Controller
public class AdminController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	AdminService adminService;
	
	//이메일 보내는 객체
	@Autowired
	JavaMailSenderImpl mailSender;
	
	@Autowired
	SqlSession sqlSession;
	//로그 사용법 공부 필요
	//Logger logger = Logger.getLogger("AdminController.java");
	
	@RequestMapping(value="/home")
	public String main(Model model) {
		
		//모달안에 사원들 값 넣어두고 보낸다
		List<MemberVO> list = memberService.employeeList();
		model.addAttribute("list", list);
		
		//Map 형식으로 분류들을 전부 가져온다 -> 키와 벨류가 없는 것도 가져와야해서 리스트로 변경
		List<Object> cate 
			=  sqlSession.selectList("ezen.ezencompany.mapper.categoryMapper.selectCategory", null);
		model.addAttribute("cate", cate);
		//logger.info(cate);
		//Map 형식으로 속성들을 전부 가져온다 -> 키와 벨류가 없는 것도 가져와야해서 리스트로 변경
		List<Object> attr 
			= sqlSession.selectList("ezen.ezencompany.mapper.categoryMapper.selectAttribute", null);
		model.addAttribute("attr", attr);
		//logger.info(attr);
		return "admin/main";
	}
	
	@RequestMapping(value="/registration")
	public String registration(HttpServletRequest request, String name, String email) throws Exception{
		Enumeration names = request.getParameterNames();
		//hasMoreElements 다음에 읽어올 내용이 있다면 true반환 
		//nextElement 사용할 때마다 처음부터 하나씩 name을 반환한다
		
		//이름 이메일 담을 list
		Map<String, Object> aaa = new HashMap<>();
		aaa.put("name", name);
		aaa.put("email",email);
		
		// map들을 담을 리스트 생성
		List <HashMap<String, Object>> list = new ArrayList<>();
 		System.out.println(names.nextElement());
		System.out.println(names.nextElement()+"코드가 후져서 대안 생각해야할듯 일단은 이렇게하면 되긴함");
		//이곳에서 member에 집어넣고 아래 반복문으로 집어넣으면 문제는 트랜잭션이 안됨
		while (names.hasMoreElements()) {
			HashMap<String, Object> map = new HashMap<>();
			String aa = (String) names.nextElement();
			String bb = request.getParameter(aa);
			map.put(bb, aa);
			list.add(map);
		}
		
		//트랜잭션 들어간 서비스 호출
		adminService.employeeRegistration(aaa, list);
		//메일발송 여기메일에 추가로 /uuid 인가 그거 설정해서 같이 보내야함
		String id = UUID.randomUUID().toString();
		System.out.println(id);
		//이 uuid를 db에 넣음(mno는 방금 email로 찾음 insert()시도 해보자)
		String setFrom = "vhahaha513@naver.com"; //2단계 인증 x, 메일 설정에서 POP/IMAP 사용 설정에서 POP/SMTP 사용함으로 설정o
		String toMail = email;
		String title = "[이젠컴퍼니]회원가입 링크입니다.";
		String content = "이젠컴퍼니 회원가입 링크는 " + email +"/"+ id + " 입니다." +
						 "<br>" +
						 "해당 링크로 들어가서 회원가입을 해주세요.";
		
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
		
		//컨트롤러 간 이동을 할때(view resolver에 걸리지 않게 하려면)리다이렉트를 사용함
		return "redirect:/admin/home";
	}
}
