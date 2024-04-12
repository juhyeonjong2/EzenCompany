package ezen.ezencompany.controller;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ezen.ezencompany.dto.BoardPermissionDTO;
import ezen.ezencompany.dto.BoardTypeViewDTO;
import ezen.ezencompany.service.AdminService;
import ezen.ezencompany.service.ManagementService;
import ezen.ezencompany.service.MemberService;
import ezen.ezencompany.util.Path;
import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.BoardReaderVO;
import ezen.ezencompany.vo.BoardTypeVO;
import ezen.ezencompany.vo.BoardWriterVO;
import ezen.ezencompany.vo.CategoryVO;
import ezen.ezencompany.vo.MemberVO;

@RequestMapping(value="/admin")
@Controller
public class AdminController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	ManagementService managementService;
	
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
	////// board /////////
	
	private CategoryVO findCategory(int findCidx ,List<CategoryVO> categoroyList) {
		if(categoroyList == null)
			return null;
		
		// 0인경우 : 모두
		if(findCidx == 0) {
			CategoryVO allCategory = new  CategoryVO(); // 메모리 낭비라 캐싱하면 좋음.
			allCategory.setCidx(0);
			allCategory.setCode("ALL");
			allCategory.setValue("모두");
			return allCategory;
		}
		
		Iterator<CategoryVO> it = categoroyList.iterator();
		while(it.hasNext()) {
			CategoryVO category = it.next();
			if(category.getCidx() == findCidx) {
				return category;
			}
		}
		return null;
		
	}
	
	private AttributeVO findAttribute(int findAidx ,List<AttributeVO> attributeList) {
		if(attributeList == null)
			return null;
		
		// 0인경우 : 모두
		if(findAidx == 0) {
			AttributeVO allAttribute = new  AttributeVO(); // 메모리 낭비라 캐싱하면 좋음.
			allAttribute.setAidx(0);
			allAttribute.setCidx(0);
			allAttribute.setOtkey("ALL");
			allAttribute.setValue("모두");
			return allAttribute;
		}
		
		Iterator<AttributeVO> it = attributeList.iterator();
		while(it.hasNext()) {
			AttributeVO attribute = it.next();
			if(attribute.getAidx() == findAidx) {
				return attribute;
			}
		}
		return null;
		
	}
	
	private List<BoardPermissionDTO> getPermissionList(
			HashMap<Integer, ArrayList<Integer>> permissionMap, 
			List<CategoryVO> categoroyList, 
			List<AttributeVO> attributeList)
	{
		
		List<BoardPermissionDTO> permissions = new ArrayList<BoardPermissionDTO>();
		Set<Integer> categoryKeySet = permissionMap.keySet();
		for( Integer cidx : categoryKeySet) {
			BoardPermissionDTO permission = new BoardPermissionDTO();
			permission.setCategory(findCategory(cidx, categoroyList));
			
			List<AttributeVO> attributes = new ArrayList<AttributeVO>();
			ArrayList<Integer> attributeKeys = permissionMap.get(cidx);
			for(Integer aidx : attributeKeys) {
				attributes.add(findAttribute(aidx, attributeList));
			}
			permission.setAttributes(attributes);
			
			permissions.add(permission);
		}
		
		return permissions;
	}
	
	private HashMap<Integer, ArrayList<Integer>> getReaderMap(int bindex){
		
		HashMap<Integer, ArrayList<Integer>> permissionMap = new HashMap<Integer, ArrayList<Integer>>();
		List<BoardReaderVO> boardReaders = managementService.getBoardReaderList(bindex);
		for(BoardReaderVO br : boardReaders)
		{
			int cidx = br.getCidx();
			int aidx = br.getAidx();
			if(permissionMap.containsKey(cidx)) {
				permissionMap.get(cidx).add(aidx);
			} else {
				permissionMap.put(cidx, new ArrayList<Integer>());
				permissionMap.get(cidx).add(aidx);
			}
		}
		
		return permissionMap;
	}
	

	private HashMap<Integer, ArrayList<Integer>> getWriterMap(int bindex){
		
		HashMap<Integer, ArrayList<Integer>> permissionMap = new HashMap<Integer, ArrayList<Integer>>();
		List<BoardWriterVO> boardWriters = managementService.getBoardWriterList(bindex);
		for(BoardWriterVO bw : boardWriters)
		{
			int cidx = bw.getCidx();
			int aidx = bw.getAidx();
			if(permissionMap.containsKey(cidx)) {
				permissionMap.get(cidx).add(aidx);
			} else {
				permissionMap.put(cidx, new ArrayList<Integer>());
				permissionMap.get(cidx).add(aidx);
			}
		}
		
		return permissionMap;
	}

	@RequestMapping(value="/board")
	public String board(Model model) {
		//1. boardType 목록을 가져옴
		List<BoardTypeVO> boardList = managementService.getBoardTypeList();
		List<AttributeVO> attributeList = managementService.getAttributes();
		List<CategoryVO> categoroyList = managementService.getCategorys();
		
		List<BoardTypeViewDTO> boardViewList = new ArrayList<BoardTypeViewDTO>();
		for(BoardTypeVO vo : boardList) {
			// 한개의 게시판 정보.
			
			BoardTypeViewDTO dto = new BoardTypeViewDTO();
			dto.setBindex(vo.getBindex());
			dto.setBtname(vo.getBtname());
			
			int bindex = vo.getBindex(); // 성능향상을 위한 캐싱
			
			//reader
			HashMap<Integer, ArrayList<Integer>> readerPermissionMap = getReaderMap(bindex);
			List<BoardPermissionDTO> readers = getPermissionList(readerPermissionMap,categoroyList, attributeList );
			dto.setReaders(readers);
			
			//writer
			HashMap<Integer, ArrayList<Integer>> writerPermissionMap = getWriterMap(bindex);
			List<BoardPermissionDTO> writers = getPermissionList(writerPermissionMap,categoroyList, attributeList );
			dto.setWriters(writers);
			
			boardViewList.add(dto);
		}
		
		model.addAttribute("boardtype",boardViewList );
		
		
		return "admin/board";
	}
	
	///// category //////////
	@RequestMapping(value="/category")
	public String category(Model model) {
		List<CategoryVO> list = managementService.getCategorys();
		
		model.addAttribute("categorys",list);
		
		return "admin/category";
	}
	
	@RequestMapping(value="/category/info", method=RequestMethod.GET)
	@ResponseBody
	public CategoryVO categoryInfo(int cidx) {
		
		return managementService.getCategory(cidx);
	}
	
	
	@RequestMapping(value = "/category/write", method = RequestMethod.POST)
	public String categoryWriteOk(CategoryVO vo) {
		
		int result = managementService.addCategory(vo);
		
		// write완료후 리로드하도록 category로 보냄.
		return "redirect:/admin/category"; 
	}
	
	@RequestMapping(value = "/category/modify", method = RequestMethod.POST)
	public String categoryModifyOk(CategoryVO vo) {
	
		int result = managementService.modifyCategory(vo);
		
		// write완료후 리로드하도록 category로 보냄.
		return "redirect:/admin/category"; 
	}
	
	@RequestMapping(value = "/category/remove", method = RequestMethod.POST)
	public String categoryRemoveOk(int cidx) {
		
		int result = managementService.removeCategory(cidx);
		
		// write완료후 리로드하도록 category로 보냄.
		return "redirect:/admin/category"; 
	}
	

	//// attribute ///////
	@RequestMapping(value="/attribute")
	public String attribute(Model model) {
		
		List<CategoryVO> list = managementService.getCategorys();
		model.addAttribute("categorys",list);
		
		return "admin/attribute";
	}
	
	@GetMapping(value="/attributes/{cidx}")
	public String attributeList(Model model, @PathVariable int cidx) {
		List<AttributeVO> list = managementService.getAttributes(cidx);
		model.addAttribute("attributes",list);
		
		CategoryVO category = managementService.getCategory(cidx);
		model.addAttribute("category",category);
		
		return "admin/attributeList";
	}
	
	@RequestMapping(value="/attribute/info", method=RequestMethod.GET)
	@ResponseBody
	public AttributeVO attributeInfo(int aidx) {
		return managementService.getAttribute(aidx);
	}
	

	@RequestMapping(value = "/attribute/write", method = RequestMethod.POST)
	public String attributeWriteOk(AttributeVO vo) {
		
		int result = managementService.addAttribute(vo);
		return "redirect:/admin/attributes/" + vo.getCidx(); 
	}
	
	@RequestMapping(value = "/attribute/modify", method = RequestMethod.POST)
	public String attributeModifyOk(AttributeVO vo) {
		
		int result = managementService.modifyAttribute(vo);
		return "redirect:/admin/attributes/" + vo.getCidx();
	}
	
	@RequestMapping(value = "/attribute/remove", method = RequestMethod.POST)
	public String attributeRemoveOk(int aidx) {
		AttributeVO vo = managementService.getAttribute(aidx);
		int cidx = vo.getCidx();
		
		int result = managementService.removeAttribute(aidx);
		return "redirect:/admin/attributes/" + cidx;
	}
	
	
}
