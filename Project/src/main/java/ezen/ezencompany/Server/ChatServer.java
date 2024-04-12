package ezen.ezencompany.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

//웹소켓 사용을 위한 어노테이션 작성(웹소켓 서버 위치)
@ServerEndpoint("/chatserver")
public class ChatServer {
	
	// 현재 채팅 서버에 접속한 클라이언트(WebSocket Session) 목록
	// static 반드시 붙여야 작동한다
	
	//여기서 Session은 기존 세션과 다르게 클라이언트 정보를 가지고 있다(인터페이스)
	private static List<Session> list = new ArrayList<Session>();
	
	private void print(String msg) {
		System.out.printf("[%tT] %s\n", Calendar.getInstance(), msg);
	}
	
	//웹소켓 서버로 들어온 경우
	@OnOpen
	public void handleOpen(Session session) {
		print("클라이언트 연결");
		list.add(session); // 접속자 관리(****)
	}
	
	//클라이언트어게 메세지가 들어간 경우
	@OnMessage
	public void handleMessage(String msg, Session session) {
		// msg는 서버로 온 모든 정보들
		// 로그인할 때: 1#유저명
		// 대화  할 때: 2유저명#메세지		
		int index = msg.indexOf("#", 2); //두번째# 채팅제외한 나머지 값을 index로 반환
		String no = msg.substring(0, 1); //로그인인지 채팅친건지 구분용
		String user = msg.substring(2, index); //2번 부터 구해서 index번 이전 까지 구함(아이디)
		String txt = msg.substring(index + 1); //msg에서 채팅만 분리
		System.out.println("뭔가가 시작 됨");
		
		if (no.equals("1")) {
			System.out.println("접속");
			// 누군가 접속 > 1#아무개
			for (Session s : list) {
				if (s != session) { // 현재 접속자가 아닌 나머지 사람들(내가 보낸 메세지를 내가 받으면 안되기 때문에)(동시에 null이 아닌)
					try {
						//서버? 클라이언트? 로 메세지를 보냄 -> 유저 정보
						s.getBasicRemote().sendText("1#" + user + "#");
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			}
			
		} else if (no.equals("2")) {
			System.out.println("메세지 전송");
			// 누군가 메세지를 전송
			for (Session s : list) {
				if (s != session) { // 현재 접속자가 아닌 나머지 사람들
					try {
						s.getBasicRemote().sendText("2#" + user + ":" + txt);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				
			} 
		} else if (no.equals("3")) {
			// 누군가 접속 > 3#아무개
			for (Session s : list) {
				if (s != session) { // 현재 접속자가 아닌 나머지 사람들
					try {
						s.getBasicRemote().sendText("3#" + user + "#");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
			list.remove(session);
		}
		
	}
	
	//웹소켓과의 연결이 끊긴경우
	@OnClose
	public void handleClose() {
		System.out.println("연결 끊김");
	}
	
	//에러가 난 경우
	@OnError
	public void handleError(Throwable t) {
		
	}
}