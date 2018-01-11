/**
 * Copyright (c) 2018 Matthias Kappeller<br/>
 * All rights reserved.
 */
package org.codescale.stream.binder.websocket;

import java.net.URI;

import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.util.Assert;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

/**
 * @author Matthias Kappeller
 * @since 0.0.1
 */
public class WebSocketStompClientWrapper extends WebSocketStompClient {

	private URI webSocketServerURI;

	/**
	 * @param webSocketClient
	 * @param url
	 */
	public WebSocketStompClientWrapper(WebSocketClient webSocketClient, URI webSocketServerURI) {
		super(webSocketClient);

		Assert.notNull(webSocketServerURI, "WebSocketServer-URI is required");
		this.webSocketServerURI = webSocketServerURI;
	}

	public ListenableFuture<StompSession> connect(StompSessionHandler handler) {
		return super.connect(webSocketServerURI, null, null, handler);
	}

}
