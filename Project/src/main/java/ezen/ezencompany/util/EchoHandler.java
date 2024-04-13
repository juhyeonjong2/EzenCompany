package ezen.ezencompany.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import ezen.ezencompany.vo.UserVO;

public class EchoHandler extends TextWebSocketHandler { 
	//로그인 한 전체 session 리스트
	List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	// 현재 로그인 중인 개별 유저
	Map<String, WebSocketSession> users = new ConcurrentHashMap<String, WebSocketSession>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
      String senderId = getMemberId(session); // 접속한 유저의 http세션을 조회하여 id를 얻는 함수
		if(senderId!=null) {	// 로그인 값이 있는 경우만
			log(senderId + " 연결 됨");
			//map타입 users에 아이디를 키로 세션데이터를 집어넣는다
			users.put(senderId, session);   // 로그인중 개별유저 저장
		}
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
	String senderId = getMemberId(session);  

		// 특정 유저에게 보낼 메시지 내용 추출
		String msg = message.getPayload();
		System.out.println(msg+"메세지 옴");
		if(msg != null) {
			String[] strs = msg.split(",");
			log(strs.toString()); //채팅,아이디,내용,경로?
			if(strs != null && strs.length == 4) {
				String type = strs[0];
				String target = strs[1]; // mid 저장
				String content = strs[2];
				String myName = strs[3];
				//users에 키값을 넣어서 해당사람의 세션을 찾아 그 사람에게 보내준다
				WebSocketSession targetSession = users.get(target);  // 메시지를 받을 세션 조회
				
				// 실시간 접속시
				if(targetSession!=null) {
					TextMessage tmpMsg = new TextMessage(msg);
					targetSession.sendMessage(tmpMsg);
				}
			}
		}
	}
	
    //연결이 끊어진 후
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String senderId = session.getId();
		if(senderId!=null) {	// 로그인 값이 있는 경우만
			log(senderId + " 연결 종료됨");
			users.remove(senderId);
			sessions.remove(session);
		}
	}
	
	// 에러 발생시
		@Override
		public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
			log(session.getId() + " 익셉션 발생: " + exception.getMessage());

		}
		// 로그 메시지
		private void log(String logmsg) {
			System.out.println(new Date() + " : " + logmsg);
		}
		// 웹소켓에 id 가져오기
	    // 접속한 유저의 http세션을 조회하여 id를 얻는 함수
		private String getMemberId(WebSocketSession session) {
			
			//웹소켓 세션에서 스프링 시큐리티의 정보를 얻어옴(mid)
			SecurityContextImpl o = (SecurityContextImpl) session.getAttributes().get("SPRING_SECURITY_CONTEXT");
	        UserVO principal = (UserVO) o.getAuthentication().getPrincipal();
	        String mid = principal.getUsername();

			return mid==null? null: mid;
		}
}