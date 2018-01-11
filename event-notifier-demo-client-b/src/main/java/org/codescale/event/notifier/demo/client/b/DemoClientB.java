/**
 * Copyright (c) 2018 Matthias Kappeller<br/>
 * All rights reserved.
 */
package org.codescale.event.notifier.demo.client.b;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Matthias Kappeller
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class DemoClientB {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		SpringApplication app = new SpringApplication(DemoClientB.class);
		app.setWebEnvironment(false);
		app.run(args);

		while (true) {
			Thread.sleep(1000);
		}
	}

}
