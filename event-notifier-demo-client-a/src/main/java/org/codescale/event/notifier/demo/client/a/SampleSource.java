package org.codescale.event.notifier.demo.client.a;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

@EnableBinding(SampleSource.MultiOutputSource.class)
public class SampleSource {

	@Bean
	@InboundChannelAdapter(value = MultiOutputSource.OUTPUT1, poller = @Poller(fixedDelay = "1000", maxMessagesPerPoll = "1"))
	public synchronized MessageSource<String> messageSource1() {
		return new MessageSource<String>() {
			public Message<String> receive() {
				String message = "FromSource1";
				Map<String, Object> headers = new HashMap<>(2);
				return new GenericMessage<String>(message, headers);
			}
		};
	}

	@Bean
	@InboundChannelAdapter(value = MultiOutputSource.OUTPUT2, poller = @Poller(fixedDelay = "100", maxMessagesPerPoll = "1"))
	public synchronized MessageSource<String> timerMessageSource() {
		return new MessageSource<String>() {
			public Message<String> receive() {
				String message = "FromSource2";
				Map<String, Object> headers = new HashMap<>(2);
				return new GenericMessage<String>(message, headers);
			}
		};
	}

	public interface MultiOutputSource {
		String OUTPUT1 = "output1";

		String OUTPUT2 = "output2";

		@Output(OUTPUT1)
		MessageChannel output1();

		@Output(OUTPUT2)
		MessageChannel output2();
	}
}