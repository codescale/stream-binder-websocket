/**
 * Copyright (c) 2018 Matthias Kappeller<br/>
 * All rights reserved.
 */
package org.codescale.event.notifier.demo.client.b;

import java.util.HashMap;
import java.util.Map;

import org.codescale.event.notifier.demo.client.a.model.ScheduledNotification;
import org.codescale.event.notifier.demo.client.a.model.AlarmValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

/**
 * @author Matthias Kappeller
 *
 */
@EnableBinding({ ScheduledNotification.class })
public class DemoSink {
	private static final Logger log = LoggerFactory.getLogger(DemoSink.class);

	private final Map<String, Double> dpSum = new HashMap<>();

	@StreamListener(target = ScheduledNotification.ALARM_SUBSCRIBER)
	public void logAlarmDpNotifications(AlarmValue alarmValue) {
		log.info("Alarm: " + alarmValue);
	}
}
