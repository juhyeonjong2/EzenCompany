package ezen.ezencompany.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import ezen.ezencompany.vo.UserVO;


public class UserLoginSuccessHandler implements AuthenticationSuccessHandler{
	
	
	//로그인성공시 이쪽으로 오게된다
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {;
		
			//authentication = 저장된 모든 정보, loginUser = db에 있는 정보만 있음
			UserVO loginUser = (UserVO)authentication.getPrincipal();

			// ws comment - 여기서 부터
		     /* db에서 가져와서 데이터 설정해줘야함. 
		      *  임시데이터로 설정한다. */
			loginUser.setMno(1);
			
			// ws comment - 여기까지

			if(loginUser.getAuthority().equals("ROLE_ADMIN")) {
				response.sendRedirect(request.getContextPath()+"/admin/home");
			}else {
				response.sendRedirect(request.getContextPath()+"/blog/home");
			}
			//System.out.println("로그인 성공");
			//Logger logger = Logger.getLogger("UserLoginSuccessHandler.java");
			//logger.info("로그연습: 로그인 성공");
	}
}
