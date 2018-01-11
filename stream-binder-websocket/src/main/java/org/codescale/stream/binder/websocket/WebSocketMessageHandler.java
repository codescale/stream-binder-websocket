/**
 * Copyright (c) 2018 Matthias Kappeller<br/>
 * All rights reserved.
 */
package org.codescale.stream.binder.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * {@link MessageHandler} that forwards {@link Message messages} to a
 * {@link StompSession}.
 * 
 * @author Matthias Kappeller
 * @since 0.0.1
 */
public class WebSocketMessageHandler implements MessageHandler, ListenableFutureCallback<StompSession> {

	private static final Logger log = LoggerFactory.getLogger(WebSocketMessageHandler.class);

	private StompSession stompSession;

	private final String destination;

	/**
	 * @param stompClient
	 *            used to connect to the WebSocket-Broker.
	 * @param destination
	 *            to send the {@link Message} to.
	 */
	public WebSocketMessageHandler(WebSocketStompClientWrapper stompClient, ProducerDestination destination) {
		new KeepAliveStompSession(stompClient, this);
		this.destination = destination.getName();
	}

	@Override
	public void onSuccess(StompSession stompSession) {
		this.stompSession = stompSession;
	}

	@Override
	public void onFailure(Throwable ex) {
		this.stompSession = null;
	}

	@Override
	public void handleMessage(Message<?> message) {
		log.debug("Send message <{}>", message.getPayload());

		// If a session is available
		if (stompSession != null && stompSession.isConnected()) {
			// Send the message to the given destination
			stompSession.send(destination, message.getPayload());
		}
	}

}
