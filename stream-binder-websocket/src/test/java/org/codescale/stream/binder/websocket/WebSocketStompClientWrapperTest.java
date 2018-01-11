/**
 * Copyright (c) 2018 Matthias Kappeller<br/>
 * All rights reserved.
 */
package org.codescale.stream.binder.websocket;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.codescale.stream.binder.websocket.WebSocketStompClientWrapper;
import org.junit.Test;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureTask;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;

/**
 * @author Matthias Kappeller
 *
 */
public class WebSocketStompClientWrapperTest {

	/**
	 * Test method for
	 * {@link org.codescale.stream.binder.websocket.WebSocketStompClientWrapper#connect(org.springframework.messaging.simp.stomp.StompSessionHandler)}.
	 */
	@Test
	public void testConnectStompSessionHandler() {

		// Define the WebSocketServer-URL
		String url = "myUrl";
		URI uri = URI.create(url);

		WebSocketClient webSocketClient = mock(WebSocketClient.class);

		// Create a session if the webSocketClient shall do a handshake for the
		// expected URI
		ListenableFuture<WebSocketSession> future = new ListenableFutureTask<>(() -> mock(WebSocketSession.class));
		when(webSocketClient.doHandshake(any(), any(), eq(uri))).thenReturn(future);

		StompSessionHandler handler = new StompSessionHandlerAdapter() {
		};
		WebSocketStompClientWrapper wrapperToTest = new WebSocketStompClientWrapper(webSocketClient, uri);

		// Do connect
		ListenableFuture<StompSession> sessionRequest = wrapperToTest.connect(handler);

		// And there is not much we can verify
		// We expect that if we have an object (not null) then everything's fine
		// :)
		assertNotNull(sessionRequest);
	}

}
