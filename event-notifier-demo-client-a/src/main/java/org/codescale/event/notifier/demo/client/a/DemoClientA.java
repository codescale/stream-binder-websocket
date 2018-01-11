/**
 * Copyright (c) 2018 Matthias Kappeller<br/>
 * All rights reserved.
 **/
package org.codescale.event.notifier.demo.client.a;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Matthias Kappeller
 *
 */
@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
public class DemoClientA {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		SpringApplication app = new SpringApplication(DemoClientA.class);
		app.setWebEnvironment(false);
		ConfigurableApplicationContext ctx = app.run(args);

		// Get Event-Notification Service
		ScheduledNotifier notifier = ctx.getBean(ScheduledNotifier.class);

		// Send Notification
		notifier.notify("my.alarm.dp");

		while (true) {
			Thread.sleep(1000);
		}
	}

}
