/**
 * Copyright (c) 2018 Matthias Kappeller<br/>
 * All rights reserved.
 */
package org.codescale.stream.binder.websocket.config;

import java.net.URI;

import org.codescale.stream.binder.websocket.WebSocketMessageChannelBinder;
import org.codescale.stream.binder.websocket.WebSocketStompClientWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

/**
 * Spring-Configuration to create and configure
 * {@link WebSocketMessageChannelBinder}.
 * 
 * @author Matthias Kappeller
 * @since 0.0.1
 */
@Configuration
@Import({ PropertyPlaceholderAutoConfiguration.class })
public class WebSocketMessageChannelBinderConfiguration {

	@Value("${stream.binder.websocket.uri}")
	private URI uri;

	/**
	 * @return a new {@link WebSocketStompClient}, based on the
	 *         {@link StandardWebSocketClient} and using the
	 *         {@link MappingJackson2MessageConverter} by default.
	 */
	@Bean
	public WebSocketStompClientWrapper stompClient() {
		WebSocketClient client = new StandardWebSocketClient();
		WebSocketStompClientWrapper stompClient = new WebSocketStompClientWrapper(client, uri);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());

		return stompClient;
	}

	/**
	 * @return a new {@link Binder} to broadcast messages by WebSockets with
	 *         STOMP.
	 */
	@Bean
	WebSocketMessageChannelBinder WebSocketMessageChannelBinder() {
		return new WebSocketMessageChannelBinder(stompClient());
	}
}