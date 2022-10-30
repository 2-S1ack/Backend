package com.clone.s1ack.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // React 에서 new SockJS("/ws/chat") 을 했을 때 새로운 핸드쉐이크 커넥션(WebSocket 에 연결)을 생성할때
        registry.addEndpoint("/chat") // SockJS 연결 주소
//                .setAllowedOrigins("*")
                .withSockJS();
        // URL: ws://localhost:8080/chat
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /**
         * pub -> Message Broker -> sub
         */

        // /queue와 /topic이 붙은 요청이 오면 messageBroker가 잡아서 해당 채팅방을 구독하고 있는 클라이언트에게 메시지를 전달하는데
        // STOMP 메시지의 destination 헤더는 @Controller 객체의 @MessageMapping 메서드로 라우팅된다.
        registry.enableSimpleBroker("/sub");

        // Client(publisher)에서 보낸 메세지를 받을 경로
        registry.setApplicationDestinationPrefixes("/pub");
    }
}

