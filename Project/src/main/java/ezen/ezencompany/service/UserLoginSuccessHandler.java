package ezen.ezencompany.service;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


public class UserLoginSuccessHandler implements AuthenticationSuccessHandler{
	//로그인성공시 이쪽으로 오게된다
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {;
		
			//authentication = 저장된 모든 정보, loginUser = db에 있는 정보만 있음
			//UserVO loginUser = (UserVO)authentication.getPrincipal();
			
			//System.out.println("로그인 성공");
			Logger logger = Logger.getLogger("UserLoginSuccessHandler.java");
			logger.info("로그연습: 로그인 성공");
			response.sendRedirect(request.getContextPath()+"/admin/home");
	}
}
