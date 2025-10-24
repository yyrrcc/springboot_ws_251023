package com.mycompany.wstest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

// 2. 웹소켓 메시지 처리
@Component // @Component 또는 @Bean 해줘야 함! 
public class ChatHandler extends TextWebSocketHandler{
	
	// 사이트에 접속한 모든 사용자들의 세션을 저장하는 Set 선언 (Set = 중복x, 순서상관x)
	private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());
	

	// 접속한 사용자가 WebSocket에 접속 했을 때 자동으로 호출되는 메서드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session); // 새로 접속한 사용자의 세션을 기존 세션 목록에 추가
		System.out.println("새로운 사용자 접속 : " + session.getId()); // 콘솔에 새로운 사용자의 접속 로그 출력
		//super.afterConnectionEstablished(session);
	}

	// 사용자가 메시지를 보냈을 때 호출되는 메서드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload(); // 메시지 내용 
		//message.getPayloadLength(); // 메시지 길이
		String sender = session.getId(); // 메시지 보낸 사용자의 세션 id
		System.out.println("사용자 : " + sender + " / 내용 : " + payload); // 백엔드 출력
		// for문을 이용해서 sessions에 값을 넣어줘야 함
		for (WebSocketSession webSocketSession : sessions) {
			if(webSocketSession.isOpen()) { // 세션이 아직 연결되어 있는 지 확인
				webSocketSession.sendMessage(new TextMessage(sender + " : " + payload)); // 보낸이와 메시지를 출력하기
			}
		}
	}

	// 사용자가 연결을 끊었을 때 자동으로 호출되는 메서드
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session); // 기존 세션 목록에서 제거
		System.out.println("사용자 연결 종료 : " + session.getId());
		//super.afterConnectionClosed(session, status);
	}

}
