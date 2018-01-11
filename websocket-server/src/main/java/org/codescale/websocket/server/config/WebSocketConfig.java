package org.codescale.websocket.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	private static final Logger log = LoggerFactory.getLogger(WebSocketConfig.class);

	public static final String ENDPOINT = "/ws";

	@Bean
	public DefaultHandshakeHandler handshakeHandler() {
		return new DefaultHandshakeHandler(new TomcatRequestUpgradeStrategy());
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(ENDPOINT).setHandshakeHandler(handshakeHandler());
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		super.configureMessageBroker(registry);

		registry.enableSimpleBroker("/topic");
	}

	@EventListener
	public void onSessionSubscription(SessionSubscribeEvent event) {
		log.debug("Subscribe: {}", event);
	}

	@EventListener
	public void onSessionSubscription(SessionUnsubscribeEvent event) {
		log.debug("Unsubscribe: {}", event);
	}
}