package ezen.ezencompany.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


import ezen.ezencompany.vo.UserVO;

public class UserLoginSuccessHandler implements AuthenticationSuccessHandler{
	


	//濡쒓렇�씤�꽦怨듭떆 �씠履쎌쑝濡� �삤寃뚮맂�떎
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		
			//authentication = ���옣�맂 紐⑤뱺 �젙蹂�
			UserVO loginUser = (UserVO)authentication.getPrincipal();
			//System.out.println(loginUser);
			//System.out.println(loginUser.getMno());
			//System.out.println(loginUser.getEmail());
			//System.out.println(loginUser.getMname());
			//System.out.println(loginUser.getMphone());

			if(loginUser.getAuthority().equals("ROLE_ADMIN")) {
				response.sendRedirect(request.getContextPath()+"/admin/home");
			}else {
				response.sendRedirect(request.getContextPath()+"/board/list");
			}
			//System.out.println("濡쒓렇�씤 �꽦怨�");
			//Logger logger = Logger.getLogger("UserLoginSuccessHandler.java");
			//logger.info("濡쒓렇�뿰�뒿: 濡쒓렇�씤 �꽦怨�");
	}
}
