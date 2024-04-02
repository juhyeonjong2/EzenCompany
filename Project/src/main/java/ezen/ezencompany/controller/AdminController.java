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
	
	//사원 수정을 누른경우
	@RequestMapping(value="/memberModify", method = RequestMethod.POST)
	public String memberModify(MultipartFile uploadFile, HttpServletRequest request)  throws IllegalStateException, IOException{
		//받아와야 할 정보 
		//1.member 테이블에 업뎃할 거
		//2.사원정보에 업뎃 할 거
		//3.이미지파일 -> 멤버vo에 넣어두면 될듯함 -> 위 방식이 아니면 안 읽어지는듯 하다
		
		//작업 순서
		//모든 정보를 1개의 서비스안에 집어넣어서 실행시킨다 (서비스 안에서는 정보를 분해해서 3개 실행 후 트랜잭션 적용)
		//프사정보는 따로 가져오는로직 작성
		//3 테이블에 전부 집어 넣으면 됨
//		String saveDir = "upload/" + member.getMnick(); // member 닉네임
//		String saveDirectoryPath = application.getRealPath(saveDir); // 절대 경로 안쓰기위해 톰캣쪽에 저장됨. (디비 합치면 못씀.) 
		// 위 경로에  폴더가 있는지 확인 후 없다면 폴더 생성
//		String path = saveDirectoryPath;
//		File Folder = new File(path);

		 //해당 디렉토리가 없을경우 디렉토리를 생성합니다.
//		if (!Folder.exists()) {
//			try{
//			    Folder.mkdir(); //폴더 생성합니다.
		//	    System.out.println("폴더가 생성되었습니다.");
		        //} 
		        //catch(Exception e){
			    //e.getStackTrace();
			//}        
	        // }else {
		//	System.out.println("이미 폴더가 생성되어 있습니다.");
		//}
		
		
		//System.out.println(saveDirectoryPath);
	//	int sizeLimit = 100*1024*1024;//100mb 제한
		
	//	MultipartRequest multi = new MultipartRequest(request
	//										, saveDirectoryPath //톰캣 경로
	//										,sizeLimit
	//										,"UTF-8"
	//										, new DefaultFileRenamePolicy());
		
		//여기까지가 전프로젝트 정보
		
		
		//톰캣의 가상경로를 사용한 경로로 수정
			String path = request.getSession()
			 		 .getServletContext()
			 		 .getRealPath("/resources/upload");
			//여기는 풀 경로인듯?
			path = "D:\\ohm\\spring\\springAjaxFileuploadPjt\\src\\main\\webapp\\resources\\upload";
			System.out.println(path);
			
			//upload 폴더가 없다면 생성해 주세요
			File dir = new File(path);
			if(!dir.exists()) {
			dir.mkdirs(); //존재하지 않는 모든 상위 폴더 생성
			}
			
			if(!uploadFile.getOriginalFilename().isEmpty()) { // 파일이 존재하는 경우
			String fileNM = uploadFile.getOriginalFilename(); //원본 파일명
			
			String[] fileNMArr= fileNM.split(".");
			String ext =  fileNMArr[fileNMArr.length-1];
			
			String realFileNM = fileNMArr[0]+"001."+ext; //실제 파일명
			
			uploadFile.transferTo(new File(path,realFileNM));
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
 		System.out.println(names.nextElement());
		System.out.println(names.nextElement());
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
			System.out.println("local ip : "+ip);
			//이걸로 필요한 정보를 찾은 뒤 그걸 추가해서 아래에 더해줌(집에서 시험이 안되어서 학언에서 아침에 시험)(잘 되면 비번찾기에도 복붙)
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
}
