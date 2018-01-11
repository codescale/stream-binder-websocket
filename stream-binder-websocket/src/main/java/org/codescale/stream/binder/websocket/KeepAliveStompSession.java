/**
 * Copyright (c) 2018 Matthias Kappeller<br/>
 * All rights reserved.
 */
package org.codescale.stream.binder.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.socket.messaging.WebSocketStompClient;

/**
 * This class is used to open a WebSocket-Stomp-Session and notifies via
 * {@link ListenableFutureCallback} if the session is available or not. In case
 * the connection is interrupted, this {@link KeepAliveStompSession} tries to
 * re-connect and open another session, and notifies again.<br/>
 * <br/>
 * 
 * <ul>
 * <li>Call KeepAliveStompSession</li>
 * <li>Connect to WebSocket-Server</li>
 * <li>=> Callback#onSuccess</li>
 * <li>Connection is down</li>
 * <li>=> Callback#onFailure</li>
 * <li>KeepAliveStompSession re-connects</li>
 * <li>=> Callback#onSuccess</li>
 * </ul>
 * 
 * @author Matthias Kappeller
 * @since 0.0.1
 */
public class KeepAliveStompSession implements ListenableFutureCallback<StompSession> {

	private static final Logger log = LoggerFactory.getLogger(KeepAliveStompSession.class);

	/**
	 * Default timeout till next re-connection.
	 */
	private static final int RECONNECTION_TIMEOUT = 500;

	private final SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();

	private final ListenableFutureCallback<StompSession> callback;

	private final WebSocketStompClientWrapper stompClient;

	/**
	 * Create a new {@link KeepAliveStompSession} using the given
	 * {@link WebSocketStompClient}. If a session was successfully created the
	 * callback will be invoked
	 * ({@link ListenableFutureCallback#onSuccess(Object)} onSuccess). In case
	 * the connection fails the callback is notified
	 * ({@link ListenableFutureCallback#onFailure(Throwable) onFailure}).
	 * 
	 * @param stompClient
	 *            used to
	 *            {@link WebSocketStompClient#connect(String, org.springframework.messaging.simp.stomp.StompSessionHandler, Object...)
	 *            connect} to the webSocket-Server and open a session.
	 * @param callback
	 *            to notify about success or failure on connection.
	 */
	public KeepAliveStompSession(WebSocketStompClientWrapper stompClient,
			ListenableFutureCallback<StompSession> callback) {
		this.stompClient = stompClient;
		this.callback = callback;
		connect();
	}

	/**
	 * Tries to connect to a WebSocket server. The connection is asynchronous
	 * and therefore {@link #onSuccess(StompSession)} or
	 * {@link #onFailure(Throwable)} is called if a result is available.
	 */
	private void connect() {
		StompSessionHandler stompSessionHandler = new StompSessionHandler(this);
		try {
			ListenableFuture<StompSession> connect = stompClient.connect(stompSessionHandler);
			connect.addCallback(this);
		} catch (Exception e) {
			// In case something goes wrong during the connection, just retry :)
			log.debug("Failed to connect to websocket-server.", e);
			reconnectWithTimeout();
		}
	}

	/**
	 * Tries to connect after a given timeout.
	 */
	private void reconnectWithTimeout() {
		taskExecutor.execute(() -> connect(), RECONNECTION_TIMEOUT);
	}

	@Override
	public void onSuccess(StompSession stompSession) {
		// Forward the session
		this.callback.onSuccess(stompSession);
	}

	@Override
	public void onFailure(Throwable ex) {
		// Forward the connection failure
		this.callback.onFailure(ex);

		// Try to re-connect
		reconnectWithTimeout();
	}

}
