/**
 * Copyright (c) 2018 Matthias Kappeller<br/>
 * All rights reserved.
 */
package org.codescale.event.notifier.demo.client.a.model;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Matthias Kappeller
 *
 */
public interface ScheduledNotification {

	String ALARM_SUBSCRIBER = "alarmSubscriber";
	String ALARM_PUBLISHER = "alarmPublisher";

	@Input(ALARM_SUBSCRIBER)
	SubscribableChannel alarmSubscriber();

	@Output(ALARM_PUBLISHER)
	MessageChannel alarmPublisher();
}
