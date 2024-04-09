package ezen.ezencompany.controller;

import java.io.File;

import java.io.IOException;
import java.net.InetAddress;

import java.net.UnknownHostException;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ezen.ezencompany.service.AdminService;
import ezen.ezencompany.service.MemberService;
import ezen.ezencompany.util.Path;
import ezen.ezencompany.vo.AttributeVO;
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
	
	//회원의 상세보기를 클릭한 경우
	@RequestMapping(value = "/getMember", method = RequestMethod.GET)
	@ResponseBody //ajax를 사용하는경우 @ResponseBody이걸 사용하지 않으면 리턴값으로 보내지 않고 경로로 인식해서 보내버린다
	public MemberVO getEmail(String email) {
		//이메일을 통해 정보를 얻어 member로 해서 넘겨준다
		MemberVO member = new MemberVO();
		member = memberService.getMember(email);
		return member;
	}
	//상세보기에서 이미지 파트
	@RequestMapping(value = "/getImg", method = RequestMethod.GET)
	@ResponseBody //ajax를 사용하는경우 @ResponseBody이걸 사용하지 않으면 리턴값으로 보내지 않고 경로로 인식해서 보내버린다
	public String getImg(String email) {
		int mno = adminService.getMno(email);
		String img = memberService.getImg(mno);
		return img;
	}
	
	//상세보기에서 분류 파트
	@RequestMapping(value = "/getCategory", method = RequestMethod.GET)
	@ResponseBody //ajax를 사용하는경우 @ResponseBody이걸 사용하지 않으면 리턴값으로 보내지 않고 경로로 인식해서 보내버린다
	public List<AttributeVO> getCategory(String email) {
		int mno = adminService.getMno(email);
		
		List<AttributeVO> option 
		= sqlSession.selectList("ezen.ezencompany.mapper.memberMapper.getOption", mno);

		return option;
	}
	
	//사원 수정을 누른경우
	@RequestMapping(value="/memberModify", method = RequestMethod.POST)
	public String memberModify(MultipartFile profileImg, HttpServletRequest request)  throws IllegalStateException, IOException{

		//form안에 넘겨져온 모든 이름들을 가져온다 
		Enumeration names = request.getParameterNames();
		//순서대로 넣어야 함(사용할 때마다 다음 이름으로 넘어가지니까)
		
		// member안에 넣을 정보들
		String mname = request.getParameter((String) names.nextElement());
		String email = request.getParameter((String) names.nextElement());
		String mphone = request.getParameter((String) names.nextElement());
		String realEmail = request.getParameter((String) names.nextElement());
		String authority = request.getParameter((String) names.nextElement());
		String enabled = request.getParameter((String) names.nextElement());
		
		//이미지등 여러곳에서 사용할 mno구하기
		int mno = adminService.getMno(realEmail);
		HashMap<String, Object> memberMap = new HashMap<>();
		memberMap.put("mname",mname);
		memberMap.put("email",email);
		memberMap.put("mphone",mphone);
		memberMap.put("authority",authority);
		memberMap.put("enabled",enabled);
		memberMap.put("mno",mno);
		
		// employeeoption 안에 넣을 정보들
		List <HashMap<String, Object>> list = new ArrayList<>();
		while (names.hasMoreElements()) {
			HashMap<String, Object> map = new HashMap<>();
			String cidx = (String) names.nextElement();
			String aidx = request.getParameter(cidx);
			map.put(aidx, cidx);
			list.add(map);
		}

		//톰캣 상대경로를 사용하려 했지만 스프링은 상대경로 사용시 배포등등 여러문제가 발생해 절대경로를 쓰는게 좋아보인다
		//정보 출처 : https://stir.tistory.com/147
		// ws comment : D드라이브가 없는 컴퓨터가 있을 수 있다. 이런경우 절대경로를 사용하려면 xml등과 같은 설정파일로 압부분 경로를 변동 가능하게 해야 한다.
		//              ex) D:\\EzenCompany\\Project\\src\\main\\webapp 의 경로를 특정 설정파일에서 불러오고
		//                  불러온 경로 Path + \\resources\\upload" 연결해서 사용하는것이 좋다. (아마 이런식으로 설정할 수있을 것임)
		//                  설정파일을 쓰기 싫다면 디비에 설정정보(경로)를 저장하고, 서버가 올라갈때 전역 변수로 BasePath를 넣는 방법도 있다.
		
		//String path = request.getSession().getServletContext().getRealPath("/resources/upload");

		 //path = "D:\\EzenCompany\\Project\\src\\main\\webapp\\resources\\upload";
		
		String path = Path.getPath()+"\\resources\\upload";
		
		if(!profileImg.getOriginalFilename().isEmpty()) { // 파일이 존재하는 경우
			
			String fileNM = profileImg.getOriginalFilename(); //원본 파일명
			
			//확장자 구하기
			String[] fileNMArr= fileNM.split("\\.");
			String ext =  fileNMArr[fileNMArr.length-1];
			
			String realFileNM = fileNMArr[0]+"."+ ext; //실제 파일명
			
			int searchMno = adminService.searchMno(mno);
			if(searchMno == 0) {
				//정보가 없다면 insert
				HashMap<String, Object> map = new HashMap<>();
				map.put("mfrealname", realFileNM);
				map.put("mforeignname", fileNM);
				map.put("mno", mno);
				adminService.totalInsertImg(map, memberMap, list, mno);
			}else {
				//정보가 있다면 update
				HashMap<String, Object> map = new HashMap<>();
				map.put("mfrealname", realFileNM);
				map.put("mforeignname", fileNM);
				map.put("mno", mno);
				adminService.totalUpdateImg(map, memberMap, list, mno);
			}
			//파일 업로드
			profileImg.transferTo(new File(path,realFileNM));
		}else {
			//프로필이미지를 변경하지 않은 경우
			adminService.totalUpdate(memberMap, list, mno);
		}

		return "redirect:/admin/home";
	}
	
	//사원등록을 누른경우
	@RequestMapping(value="/registration")
	public String registration(HttpServletRequest request, String name, String email) throws Exception{
		Enumeration names = request.getParameterNames();
		//hasMoreElements 다음에 읽어올 내용이 있다면 true반환 
		//nextElement 사용할 때마다 처음부터 하나씩 name을 반환한다
		
		//이름 이메일 담을 list
		Map<String, Object> member = new HashMap<>();
		member.put("name", name);
		member.put("email",email);
		
		// 분류<map>들을 담을 리스트 생성
		List <HashMap<String, Object>> list = new ArrayList<>();
 		names.nextElement(); //sysout삭제
		names.nextElement();
		while (names.hasMoreElements()) {
			HashMap<String, Object> map = new HashMap<>();
			String cidx = (String) names.nextElement();
			String aidx = request.getParameter(cidx);
			map.put(aidx, cidx);
			list.add(map);
		}
		
		//트랜잭션 들어간 서비스 호출
		adminService.employeeRegistration(member, list);
		//uuid 생성
		String url = UUID.randomUUID().toString();
		//여기서 id(짧은 경로)를 디비에 넣어줌
		adminService.insertShortUrl(member, url);
		
		//생각해보니 자신의 ip주소를 구한다고 하더라도 자신이 타인이 세팅안하면 못쓰니 시험용으로만 사용하고 aws배운뒤 그걸로 수정필요
		String ip = "";
		InetAddress local;
		try {
			local = InetAddress.getLocalHost();
			ip = local.getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		//메일 발송
		String link = "http://"+ ip + ":8080/ezencompany/join/" + url;
		String setFrom = "vhahaha513@naver.com"; //2단계 인증 x, 메일 설정에서 POP/IMAP 사용 설정에서 POP/SMTP 사용함으로 설정o
		String toMail = email;
		String title = "[이젠컴퍼니]회원가입 링크입니다."; 
		String content = "이젠컴퍼니 회원가입 <a href='"+ link +"'> 링크 </a> 입니다." +
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
	
	//회원탈퇴를 누른경우
	@RequestMapping(value = "/deleteMember", method = RequestMethod.GET)
	@ResponseBody //ajax를 사용하는경우 @ResponseBody이걸 사용하지 않으면 리턴값으로 보내지 않고 경로로 인식해서 보내버린다
	public String deleteMember(String email) {
		int mno = adminService.getMno(email);
		//enabled를 2로 수정함
		adminService.deleteMember(mno);
		//수정완료를 확인
		int checkDel = adminService.checkDelete(mno);
		if(checkDel != 1) {
			return "true";
		}else {
			return "false";
		}
	}
	
	//메일 재전송을 누른 경우
	@RequestMapping(value = "/reSend", method = RequestMethod.GET)
	@ResponseBody //ajax를 사용하는경우 @ResponseBody이걸 사용하지 않으면 리턴값으로 보내지 않고 경로로 인식해서 보내버린다
	public String reSend(String email) {
		
		//mno구하기
		int mno = adminService.getMno(email);
		//mno로 짧은 경로 구하기
		String url = adminService.getUrl(mno);
		
		String ip = "";
		InetAddress local;
		try {
			local = InetAddress.getLocalHost();
			ip = local.getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		//메일 발송
		String link = "http://"+ ip + ":8080/ezencompany/join/" + url;
		String setFrom = "vhahaha513@naver.com"; //2단계 인증 x, 메일 설정에서 POP/IMAP 사용 설정에서 POP/SMTP 사용함으로 설정o
		String toMail = email;
		String title = "[이젠컴퍼니]회원가입 링크입니다."; 
		String content = "이젠컴퍼니 회원가입 <a href='"+ link +"'> 링크 </a> 입니다." +
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
		
		return "true";
		
	}
}
