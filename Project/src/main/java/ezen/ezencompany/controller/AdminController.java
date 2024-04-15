package ezen.ezencompany.controller;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ezen.ezencompany.dto.BoardPermissionDTO;
import ezen.ezencompany.dto.BoardTypeViewDTO;
import ezen.ezencompany.service.AdminService;
import ezen.ezencompany.service.ManagementService;
import ezen.ezencompany.service.MemberService;
import ezen.ezencompany.util.Path;
import ezen.ezencompany.vo.AttributeVO;
import ezen.ezencompany.vo.BlogReplyVO;
import ezen.ezencompany.vo.BlogVO;
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

		// 업로드 경로 : basePath + upload/아이디}	
		//경로에 사용할 아이디
		String mid = memberService.getId(mno);
		
		String [] subPath =  {"upload", mid};
		//String uploadPath = Path.getUploadPath(subPath);
		
		String basePath = request.getSession().getServletContext().getRealPath("resources");
		//System.out.println(basePath);
		String uploadPath = Path.getUploadPath(basePath,subPath);
		
		if(!profileImg.getOriginalFilename().isEmpty()) { // 파일이 존재하는 경우
			
			//uuid 생성
			String url = UUID.randomUUID().toString();
			
			String fileNM = profileImg.getOriginalFilename(); //원본 파일명
			
			//확장자 구하기
			String[] fileNMArr= fileNM.split("\\.");
			String ext =  fileNMArr[fileNMArr.length-1];
			
			String realFileNM = url +"."+ ext; //실제 파일명
			///블록 ㅡ컨트롤러 패스보고 그대로 사용하기 - 사용자 아이디(사원 수정에 사진 가져오는것도 아이디 추가한 경로로 수정해야함+uuid)
			
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
			profileImg.transferTo(new File(uploadPath,realFileNM));
		}else {
			//프로필이미지를 변경하지 않은 경우
			adminService.totalUpdate(memberMap, list, mno);
		}

		return "redirect:/admin/home";
	}
	
	//사원등록을 누른경우
	@RequestMapping(value="/registration")
	public String registration(HttpServletRequest request, String name, String email, HttpServletResponse response) throws Exception{
		Enumeration names = request.getParameterNames();
		//hasMoreElements 다음에 읽어올 내용이 있다면 true반환 
		//nextElement 사용할 때마다 처음부터 하나씩 name을 반환한다
		
		int checkEm = memberService.checkEm(email);
		if(checkEm != 0) {
			//응답할때 인코딩하기
			response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			
			response.getWriter().append("<script>alert('중복된 이메일이 존재합니다.');location.href='"
			+request.getContextPath()+"/admin/home'</script>").flush();
		}
		
		
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
		//String ip = "";
		//InetAddress local;
		//try {
		//	local = InetAddress.getLocalHost();
		//	ip = local.getHostAddress();
		//} catch (UnknownHostException e1) {
		//	e1.printStackTrace();
		//}

		//메일 발송
		String link = "http://43.201.96.8/join/" + url;
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

		//String link = "http://"+ ip + ":8080/ezencompany/join/" + url;
		//메일 발송
		String link = "http://43.201.96.8/join/" + url;
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
		List<CategoryVO> categoryList = managementService.getCategorys();
		
		List<BoardTypeViewDTO> boardViewList = new ArrayList<BoardTypeViewDTO>();
		for(BoardTypeVO vo : boardList) {
			// 한개의 게시판 정보.
			
			BoardTypeViewDTO dto = new BoardTypeViewDTO();
			dto.setBindex(vo.getBindex());
			dto.setBtname(vo.getBtname());
			
			int bindex = vo.getBindex(); // 성능향상을 위한 캐싱
			
			//reader
			HashMap<Integer, ArrayList<Integer>> readerPermissionMap = getReaderMap(bindex);
			List<BoardPermissionDTO> readers = getPermissionList(readerPermissionMap,categoryList, attributeList );
			dto.setReaders(readers);
			
			//writer
			HashMap<Integer, ArrayList<Integer>> writerPermissionMap = getWriterMap(bindex);
			List<BoardPermissionDTO> writers = getPermissionList(writerPermissionMap,categoryList, attributeList );
			dto.setWriters(writers);
			
			boardViewList.add(dto);
		}
		
		model.addAttribute("boardtype",boardViewList );
		
		
		return "admin/board";
	}
	
	private List<BoardPermissionDTO> parsePermissions(String json) throws JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		//BoardPermissionDTO[] permissions = objectMapper.readValue(reader, BoardPermissionDTO[].class);
		return Arrays.asList(mapper.readValue(json, BoardPermissionDTO[].class));
	}
	
	private void printPermissions(List<BoardPermissionDTO> permissions) {
		for(BoardPermissionDTO p : permissions) {
			CategoryVO cate = p.getCategory();
			System.out.println("Category : (" + cate.getCode() +"/" + cate.getValue() +"/" + cate.getCidx() +"/" + cate.getBlogView() +")");
			
			for(AttributeVO attr : p.getAttributes()) {
				System.out.println("  Attribute : (" + attr.getOtkey() + "/" + attr.getValue() + "/" + attr.getAidx() + "/" + attr.getCidx() +")");
			}
			
		}
	}
	
	private CategoryVO findCategory(String code ,List<CategoryVO> categoroyList) {
		if(categoroyList == null)
			return null;
		
		// 0인경우 : 모두
		if(code.toLowerCase() == "all") {
			CategoryVO allCategory = new  CategoryVO(); // 메모리 낭비라 캐싱하면 좋음.
			allCategory.setCidx(0);
			allCategory.setCode("ALL");
			allCategory.setValue("모두");
			return allCategory;
		}
		
		Iterator<CategoryVO> it = categoroyList.iterator();
		while(it.hasNext()) {
			CategoryVO category = it.next();
			if(category.getCode().equals(code)) {
				return category;
			}
		}
		return null;
		
	}
	
	private AttributeVO findAttribute(int cidx, String otkey ,List<AttributeVO> attributeList) {
		if(attributeList == null)
			return null;
		
		// 0인경우 : 모두
		if(otkey.toLowerCase() == "all" ) {
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
			if(attribute.getCidx() == cidx && attribute.getOtkey().equals(otkey)) {
				return attribute;
			}
		}
		return null;
		
	}
	
	private void fillPermissionData(List<BoardPermissionDTO> permissions, List<CategoryVO> categoryList ,List<AttributeVO> attributeList) {
		for(BoardPermissionDTO p : permissions) {
			CategoryVO cate = p.getCategory();
			CategoryVO findCate = findCategory(cate.getCode(), categoryList);
			if(findCate != null) {
				cate.setCidx(findCate.getCidx());
				cate.setCode(findCate.getCode());
				cate.setValue(findCate.getValue());
				cate.setBlogView(findCate.getBlogView());
			}
			
			int cidx =cate.getCidx();
			for(AttributeVO attr : p.getAttributes()) {
				AttributeVO findAttr = findAttribute(cidx, attr.getOtkey(), attributeList);
				if(findAttr != null) {
					attr.setAidx(findAttr.getAidx());
					attr.setCidx(findAttr.getCidx());
					attr.setOtkey(findAttr.getOtkey());
					attr.setValue(findAttr.getValue());
				}
			}
			
		}
	}
	
	private List<BoardReaderVO> toBoardReader(int bindex, List<BoardPermissionDTO> permissions){
		List<BoardReaderVO> list = new ArrayList<BoardReaderVO>();
		for(BoardPermissionDTO p : permissions) 
		{
			List<AttributeVO> attributes = p.getAttributes();
			if(attributes == null || attributes.size() == 0) {
				// category 확인 : 모두일수 있음
				if(p.getCategory().getCidx() == 0) {
					// 모두의 경우
					BoardReaderVO vo = new BoardReaderVO();
					vo.setAidx(0);
					vo.setCidx(0);
					vo.setBindex(bindex);
					list.add(vo);
				}
			}
			else {
				for(AttributeVO attr : attributes) 
				{
					BoardReaderVO vo = new BoardReaderVO();
					vo.setAidx(attr.getAidx());
					vo.setCidx(attr.getCidx());
					vo.setBindex(bindex);
					list.add(vo);
				}
			}
		}
		return list;
	}
	
	private List<BoardWriterVO> toBoardWriter(int bindex, List<BoardPermissionDTO> permissions){
		List<BoardWriterVO> list = new ArrayList<BoardWriterVO>();
		for(BoardPermissionDTO p : permissions) 
		{
			List<AttributeVO> attributes = p.getAttributes();
			if(attributes == null || attributes.size() == 0) {
				// category 확인 : 모두일수 있음
				if(p.getCategory().getCidx() == 0) {
					// 모두의 경우
					BoardWriterVO vo = new BoardWriterVO();
					vo.setAidx(0);
					vo.setCidx(0);
					vo.setBindex(bindex);
					list.add(vo);
				}
			}
			else {
				for(AttributeVO attr : p.getAttributes()) 
				{
					BoardWriterVO vo = new BoardWriterVO();
					vo.setAidx(attr.getAidx());
					vo.setCidx(attr.getCidx());
					vo.setBindex(bindex);
					list.add(vo);
				}
			}
			
		}
		return list;
	}
	
	@RequestMapping(value="/board/write", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> boardWrite(String name, String reader, String writer) throws JsonParseException, JsonMappingException, IOException {
		
		// 1. 퍼미션 데이터들을 채운다. 
		List<AttributeVO> attributeList = managementService.getAttributes();
		List<CategoryVO> categoryList = managementService.getCategorys();
		List<BoardPermissionDTO> readPermissions = parsePermissions(reader);
		List<BoardPermissionDTO> writePermissions = parsePermissions(writer);
		fillPermissionData(readPermissions, categoryList, attributeList);
		fillPermissionData(writePermissions, categoryList, attributeList);
		// 디버그용 프린트
		//printPermissions(readPermissions);
		//printPermissions(writePermissions);
		
		// 2. 보드를 한개 인서트하고 해당 키를 받는다.
		BoardTypeVO vo = new BoardTypeVO();
		vo.setBtname(name);
		int res = managementService.addBoardType(vo); // 완료후 키들어있음.
		if(res > 0) {
			int bindex = vo.getBindex();
			// 3. 읽기 권한을 설정한다.
			List<BoardReaderVO> readers = toBoardReader(bindex, readPermissions);
			if(readers.size() > 0 ) {
				int readerRes = managementService.addBoardReaders(readers);
			}
			// 4. 쓰기 권한을 설정한다.
			List<BoardWriterVO> writers = toBoardWriter(bindex, writePermissions);
			if(writers.size() > 0) {
				int writerRes = managementService.addBoardWriters(writers);
			}
		}
		
		// return
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap.put("result", "SUCCESS"); //  성공
		return resMap;
	}
	
	@RequestMapping(value="/board/info", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> boardInfo(int btno){
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		BoardTypeVO vo = managementService.getBoardType(btno);
		if(vo != null) {
			List<AttributeVO> attributeList = managementService.getAttributes();
			List<CategoryVO> categoryList = managementService.getCategorys();

			int bindex = vo.getBindex();
			
			BoardTypeViewDTO dto = new BoardTypeViewDTO();
			dto.setBindex(bindex);
			dto.setBtname(vo.getBtname());
			
			//reader
			HashMap<Integer, ArrayList<Integer>> readerPermissionMap = getReaderMap(bindex);
			List<BoardPermissionDTO> readers = getPermissionList(readerPermissionMap,categoryList, attributeList );
			dto.setReaders(readers);
			
			//writer
			HashMap<Integer, ArrayList<Integer>> writerPermissionMap = getWriterMap(bindex);
			List<BoardPermissionDTO> writers = getPermissionList(writerPermissionMap,categoryList, attributeList );
			dto.setWriters(writers);
			
			resMap.put("data", dto);
		}
		else {
			resMap.put("result", "FAIL"); //  성공
			
		}
		return resMap;
	}
	
	@RequestMapping(value="/board/modify", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> boardModify(int btno, String name, String reader, String writer ) throws JsonParseException, JsonMappingException, IOException{
		
		// 1. 퍼미션 데이터들을 채운다. 
		List<AttributeVO> attributeList = managementService.getAttributes();
		List<CategoryVO> categoryList = managementService.getCategorys();
		List<BoardPermissionDTO> readPermissions = parsePermissions(reader);
		List<BoardPermissionDTO> writePermissions = parsePermissions(writer);
		fillPermissionData(readPermissions, categoryList, attributeList);
		fillPermissionData(writePermissions, categoryList, attributeList);
		// 디버그용 프린트
		//printPermissions(readPermissions);
		//printPermissions(writePermissions);
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		BoardTypeVO vo = managementService.getBoardType(btno);
		if(vo != null) {
			int res = 0;
			vo.setBindex(btno);
			vo.setBtname(name);
			res = managementService.modifyBoardType(vo); // 게시판 이름 변경.
			System.out.println(res);
			
			// 읽기 권한 업데이트 (삭제후 다시 추가)			
			List<BoardReaderVO> readers = toBoardReader(btno, readPermissions);
			if(readers.size() > 0 ) {
				res = managementService.removeBoardReader(btno);
				System.out.println(res);
				res = managementService.addBoardReaders(readers);
			}
			// 쓰기 권한 업데이트 (삭제후 다시 추가)
			List<BoardWriterVO> writers = toBoardWriter(btno, writePermissions);
			if(writers.size() > 0) {
				res = managementService.removeBoardWriter(btno);
				res = managementService.addBoardWriters(writers);
			}
			resMap.put("result", "SUCCESS"); //  성공
		} else {
			resMap.put("result", "FAIL"); //  성공
		}
		
		return resMap;
	}
	
	@RequestMapping(value = "/board/remove", method = RequestMethod.POST)
	public String boardRemoveOk(int btno) {
		
		int res = managementService.removeBoardReader(btno);
		res =managementService.removeBoardWriter(btno);
		res = managementService.removeBoardType(btno);
		
		return "redirect:/admin/board";
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
