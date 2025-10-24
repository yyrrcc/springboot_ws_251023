package com.mycompany.wstest;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration // 1. 웹소켓 설정
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	
	
	// 3. autowired 하거나 final+생성자 해주거나 -> ChatHandler에 @Component나 @Bean 적어줘야 함!!
	private final ChatHandler chatHandler;
	public WebSocketConfig(ChatHandler chatHandler) {
		this.chatHandler = chatHandler;
	}


	// 1. WebSocket 핸들러 등록 메서드
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// 4. 핸들러 받아주고, path 적어주기
		registry.addHandler(chatHandler, "/ws/chat")
		// 5. 모든 도메인에서 들어오는 요청 허용
		.setAllowedOrigins("*")
		.setAllowedOriginPatterns("*")
		;
	}

}
