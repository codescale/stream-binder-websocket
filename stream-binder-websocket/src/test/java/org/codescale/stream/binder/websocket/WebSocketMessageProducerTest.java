/**
 * Copyright (c) 2018 Matthias Kappeller<br/>
 * All rights reserved.
 */
package org.codescale.stream.binder.websocket;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.codescale.stream.binder.websocket.WebSocketMessageProducer;
import org.codescale.stream.binder.websocket.WebSocketStompClientWrapper;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.cloud.stream.binder.AbstractBinder;
import org.springframework.cloud.stream.binder.Binding;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.MessageValues;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @author Matthias Kappeller
 *
 */
public class WebSocketMessageProducerTest {

	private final class AbstractBinderExtension extends AbstractBinder<Object, ConsumerProperties, ProducerProperties> {
		public MessageValues verifyDeserialization(Message<?> message) {
			return deserializePayloadIfNecessary(message);
		}

		@Override
		protected Binding<Object> doBindConsumer(String name, String group, Object inputTarget,
				ConsumerProperties properties) {
			return null;
		}

		@Override
		protected Binding<Object> doBindProducer(String name, Object outboundBindTarget,
				ProducerProperties properties) {
			return null;
		}
	}

	/**
	 * Test method for
	 * {@link org.codescale.stream.binder.websocket.WebSocketMessageProducer#handleFrame(org.springframework.messaging.simp.stomp.StompHeaders, java.lang.Object)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testHandleFrame() {

		ConsumerDestination destination = () -> "foo";

		WebSocketStompClientWrapper stompClient = mock(WebSocketStompClientWrapper.class);
		when(stompClient.connect(any())).thenReturn(mock(ListenableFuture.class));

		// The message producer under test
		WebSocketMessageProducer producerToTest = new WebSocketMessageProducer(stompClient, destination);

		// The output channel that will receive the message
		MessageChannel outputChannel = mock(MessageChannel.class);
		producerToTest.setOutputChannel(outputChannel);
		// Simply accept every message
		when(outputChannel.send(any())).thenReturn(true);

		// Build the message
		String payload = "content";
		StompHeaders headers = new StompHeaders();
		// The mime type can contain some additional parameter. That's what we
		// actually wanna verify
		// The message processing must work for any structure of mime type.
		MimeType mimeType = MimeTypeUtils.parseMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE + "; charset=utf-8");
		headers.setContentType(mimeType);

		// Start the test call
		producerToTest.handleFrame(headers, payload);

		// Now we expect the receive a valid message
		// We use the AbstractBinderExtension class to call the deserialization
		// method from AbstractBinder
		// Let this class deserialize the message and verify that the result
		// matches.
		AbstractBinderExtension abstractBinder = new AbstractBinderExtension();
		verify(outputChannel).send(argThat(new ArgumentMatcher<Message<?>>() {
			@Override
			public boolean matches(Message<?> message) {
				MessageValues messageValues = abstractBinder.verifyDeserialization(message);
				Object jsonPayload = messageValues.getPayload();
				// The message actually contains now a JSON-String ... that's
				// why we have to add the pre/post-quote-mark.
				return ("\"" + payload + "\"").equals(jsonPayload);
			}
		}));
	}

}
