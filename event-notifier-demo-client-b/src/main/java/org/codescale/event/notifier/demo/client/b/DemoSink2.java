/**
 * Copyright (c) 2018 Matthias Kappeller<br/>
 * All rights reserved.
 */
package org.codescale.event.notifier.demo.client.b;

import org.codescale.event.notifier.demo.client.b.DemoSink2.MultiSinkSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;

/**
 * @author Matthias Kappeller
 *
 */
@EnableBinding(MultiSinkSource.class)
public class DemoSink2 {
	private static final Logger log = LoggerFactory.getLogger(DemoSink2.class);

	/**
	 * 
	 */
	@StreamListener(MultiSinkSource.INPUT1)
	public void demoSink1(Object payload) {
		log.info("Received-1: " + payload);
	}

	@StreamListener(MultiSinkSource.INPUT2)
	public void demoSink2(Object payload) {
		log.info("Received-2: " + payload);
	}

	public interface MultiSinkSource {
		String INPUT1 = "input1";

		String INPUT2 = "input2";

		@Input(INPUT1)
		MessageChannel input1();

		@Input(INPUT2)
		MessageChannel input2();
	}
}
