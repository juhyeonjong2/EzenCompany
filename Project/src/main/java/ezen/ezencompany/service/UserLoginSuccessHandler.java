package ezen.ezencompany.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


import ezen.ezencompany.vo.UserVO;

public class UserLoginSuccessHandler implements AuthenticationSuccessHandler{
	


	//로그인성공시 이쪽으로 오게된다
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		
			//authentication = 저장된 모든 정보
			UserVO loginUser = (UserVO)authentication.getPrincipal();
			//System.out.println(loginUser);
			//System.out.println(loginUser.getMno());
			//System.out.println(loginUser.getEmail());
			//System.out.println(loginUser.getMname());
			//System.out.println(loginUser.getMphone());

			if(loginUser.getAuthority().equals("ROLE_ADMIN")) {
				response.sendRedirect(request.getContextPath()+"/admin/home");
			}else {
				response.sendRedirect(request.getContextPath()+"/board/home");
			}
			//System.out.println("로그인 성공");
			//Logger logger = Logger.getLogger("UserLoginSuccessHandler.java");
			//logger.info("로그연습: 로그인 성공");
	}
}
