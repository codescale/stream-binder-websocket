/**
 * Copyright (c) 2018 Matthias Kappeller<br/>
 * All rights reserved.
 */
package org.codescale.stream.binder.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.ConnectionLostException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * A {@link org.springframework.messaging.simp.stomp.StompSessionHandler
 * StompSessionHandler} that handles session-exceptions. Basically it logs these
 * exceptions and in case of a {@link ConnectionLostException} it notifies the
 * given {@link ListenableFutureCallback callback}.
 * 
 * @author Matthias Kappeller
 * @since 0.0.1
 */
public class StompSessionHandler extends StompSessionHandlerAdapter {

	private static final Logger log = LoggerFactory.getLogger(StompSessionHandler.class);

	private final ListenableFutureCallback<StompSession> callback;

	/**
	 * Create a new {@link StompSessionHandler}. The given
	 * {@link ListenableFutureCallback} is called if a
	 * {@link ConnectionLostException} must be handled.
	 * 
	 * @param callback
	 *            its #onFailure will be called if the connection is lost.
	 */
	public StompSessionHandler(ListenableFutureCallback<StompSession> callback) {
		this.callback = callback;
	}

	/**
	 * Log the handled exceptions.
	 */
	@Override
	public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
			Throwable exception) {
		super.handleException(session, command, headers, payload, exception);

		log.debug("Exception on session <{}>.", session, exception);
	}

	/**
	 * Calls {@link ListenableFutureCallback#onFailure(Throwable)} if the
	 * exception is a {@link ConnectionLostException}. Otherwise the exception
	 * is logged.
	 */
	@Override
	public void handleTransportError(StompSession session, Throwable exception) {
		log.debug("Transport error on session <{}>.", session, exception);

		if (exception instanceof ConnectionLostException) {
			callback.onFailure(exception);
		}
	}
}