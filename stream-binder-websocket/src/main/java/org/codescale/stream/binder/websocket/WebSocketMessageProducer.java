/**
 * Copyright (c) 2018 Matthias Kappeller<br/>
 * All rights reserved.
 */
package org.codescale.stream.binder.websocket;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.MimeType;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * {@link MessageProducer} that subscribes on a destination of a
 * WebSocket-Broker and forwards Stomp-Messages to the
 * {@link #getOutputChannel()} using {@link #sendMessage(Message)}.
 * 
 * @author Matthias Kappeller
 * @since 0.0.1
 */
public class WebSocketMessageProducer extends MessageProducerSupport
		implements StompFrameHandler, ListenableFutureCallback<StompSession> {

	private final String destination;

	/**
	 * @param stompClient
	 *            used to subscribe to the given destination.
	 * @param destination
	 *            to subscribe onto.
	 */
	public WebSocketMessageProducer(WebSocketStompClientWrapper stompClient, ConsumerDestination destination) {
		new KeepAliveStompSession(stompClient, this);
		this.destination = destination.getName();
	}

	@Override
	public void onSuccess(StompSession stompSession) {
		stompSession.subscribe(destination, this);
	}

	@Override
	public void onFailure(Throwable ex) {
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {

		// Remove any parameters from the given mime-type.
		// org.springframework.cloud.stream.binder.AbstractBinder.deserializePayload(byte[],
		// MimeType)
		// expects 'application/json' or some given 'class-type' to deserialize
		// the bytes[]
		Map<String, Object> messageHeaders = new HashMap<>();
		MimeType contentType = headers.getContentType();
		MimeType newContentType = new MimeType(contentType.getType(), contentType.getSubtype());
		messageHeaders.put(MessageHeaders.CONTENT_TYPE, newContentType);

		Message<?> message = new MappingJackson2MessageConverter().toMessage(payload,
				new MessageHeaders(messageHeaders));

		// Forward the message onto the output-channel
		sendMessage(message);
	}

	@Override
	public Type getPayloadType(StompHeaders headers) {
		return Object.class;
	}
}