/**
 * Copyright (c) 2018 Matthias Kappeller<br/>
 * All rights reserved.
 */
package org.codescale.stream.binder.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.binder.AbstractMessageChannelBinder;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.integration.core.MessageProducer;
import org.springframework.messaging.MessageHandler;

/**
 * {@link Binder} to forward and receive messages from a WebSocket-Broker. (like
 * websocket-server)
 * 
 * @author Matthias Kappeller
 * @since 0.0.1
 */
public class WebSocketMessageChannelBinder
		extends AbstractMessageChannelBinder<ConsumerProperties, ProducerProperties, WebSocketDestinationProvider> {

	private static final Logger log = LoggerFactory.getLogger(WebSocketMessageChannelBinder.class);

	private final WebSocketStompClientWrapper stompClient;

	/**
	 * Creates a new {@link WebSocketMessageChannelBinder}.
	 * 
	 * @param stompClient
	 *            used to connect to a WebSocket-Broker supporting STOMP.
	 */
	public WebSocketMessageChannelBinder(WebSocketStompClientWrapper stompClient) {
		super(true, new String[] {}, new WebSocketDestinationProvider());
		this.stompClient = stompClient;
	}

	@Override
	protected MessageHandler createProducerMessageHandler(ProducerDestination destination,
			ProducerProperties producerProperties) throws Exception {

		log.debug("Create new producer message handler for destination <{}>", destination.getName());

		producerProperties.setUseNativeEncoding(true);

		return new WebSocketMessageHandler(stompClient, destination);
	}

	@Override
	protected MessageProducer createConsumerEndpoint(ConsumerDestination destination, String group,
			ConsumerProperties properties) throws Exception {

		log.debug("Create new consumer endpoint for destination <{}>", destination.getName());

		return new WebSocketMessageProducer(stompClient, destination);
	}
}
