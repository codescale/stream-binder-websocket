/**
 * Copyright (c) 2018 Matthias Kappeller<br/>
 * All rights reserved.
 */
package org.codescale.stream.binder.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.cloud.stream.provisioning.ProvisioningException;
import org.springframework.cloud.stream.provisioning.ProvisioningProvider;

/**
 * Used to create the destination for a message-producer and -consumer.
 * 
 * @author Matthias Kappeller
 */
public class WebSocketDestinationProvider implements ProvisioningProvider<ConsumerProperties, ProducerProperties> {

	private static final Logger log = LoggerFactory.getLogger(WebSocketDestinationProvider.class);

	/**
	 * Prefix used in the destination to broadcast messages on a
	 * STOMP-WebSocket.
	 */
	private static final String DESTINATION_BROADCAST_PREFIX = "/topic/";

	@Override
	public ProducerDestination provisionProducerDestination(String name, ProducerProperties properties)
			throws ProvisioningException {

		log.debug("Create new producer destination with name <{}>", name);

		return new ProducerDestination() {

			@Override
			public String getNameForPartition(int partition) {
				return name;
			}

			@Override
			public String getName() {
				return DESTINATION_BROADCAST_PREFIX + name;
			}
		};
	}

	@Override
	public ConsumerDestination provisionConsumerDestination(String name, String group, ConsumerProperties properties)
			throws ProvisioningException {

		log.debug("Create new consumer destination with name <{}> and group <{}>.", name, group);

		return new ConsumerDestination() {

			@Override
			public String getName() {
				return DESTINATION_BROADCAST_PREFIX + name;
			}
		};
	}

}
