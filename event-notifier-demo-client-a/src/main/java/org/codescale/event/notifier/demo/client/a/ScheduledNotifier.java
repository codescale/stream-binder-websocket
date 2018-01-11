/**
 * Copyright (c) 2018 Matthias Kappeller<br/>
 * All rights reserved.
 */
package org.codescale.event.notifier.demo.client.a;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

import org.codescale.event.notifier.demo.client.a.model.AlarmValue;
import org.codescale.event.notifier.demo.client.a.model.AlarmValueBuilder;
import org.codescale.event.notifier.demo.client.a.model.ScheduledNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author Matthias Kappeller
 *
 */
@Service
@EnableBinding(ScheduledNotification.class)
public class ScheduledNotifier {

	private static final long DP_CHANGE_RATE_MILLIS = 1000;

	private final ScheduledNotification notifier;
	private final Random random;

	/**
	 * 
	 */
	@Autowired
	public ScheduledNotifier(ScheduledNotification notifier) {
		this.notifier = notifier;
		this.random = new Random();
	}

	@Scheduled(fixedRate = DP_CHANGE_RATE_MILLIS)
	public void scheduledNotification() {
		notify("scheduled.notification");
	}

	public void notify(String name) {
		AlarmValue alarmValue = new AlarmValueBuilder().withName(name).withSourceTimestamp(Date.from(Instant.now()))
				.withValue(random.nextDouble()).build();

		notifier.alarmPublisher().send(MessageBuilder.withPayload(alarmValue).build());
	}
}
