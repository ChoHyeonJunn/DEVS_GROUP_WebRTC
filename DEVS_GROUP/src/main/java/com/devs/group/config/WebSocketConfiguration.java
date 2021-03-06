package com.devs.group.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.devs.group.common.socket.AlarmSocketHandler;
import com.devs.group.common.socket.ChatSocketHandler;
import com.devs.group.common.socket.RtcSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//		registry.addHandler(new SocketHandler(), "/socket").setAllowedOrigins("*");
		registry
				// handle on "/signal" endpoint
				.addHandler(signalingSocketHandler(), "/signal")//
				.addHandler(chattingSocketHandler(), "/chatsocket")//
				.addHandler(alarmmSocketHandler(), "/alarm")//
				// Allow cross origins
				.setAllowedOrigins("*")
				// Httpsession에 있는 값을 가로채서 WebSocketSession에 똑같이 넣어주는 역할을 한다.
				.addInterceptors(new HttpSessionHandshakeInterceptor());
	}

	@Bean
	public WebSocketHandler signalingSocketHandler() {
		return new RtcSocketHandler();
	}

	@Bean
	public WebSocketHandler chattingSocketHandler() {
		return new ChatSocketHandler();
	}
	
	@Bean
	public WebSocketHandler alarmmSocketHandler() {
		return new AlarmSocketHandler();
	}

}
