package ezen.ezencompany.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


public class userLoginSuccessHandler implements AuthenticationSuccessHandler{
	//로그인성공시 이쪽으로 오게된다
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {;
		

		response.sendRedirect(request.getContextPath());
	}
}
