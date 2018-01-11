/**
 * Copyright (c) 2018 Matthias Kappeller<br/>
 * All rights reserved.
 */
package org.codescale.stream.binder.websocket.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring-Auto-Configuration if no other {@link Binder} is available.
 * 
 * @author Matthias Kappeller
 * @since 0.0.1
 */
@Configuration
@ConditionalOnMissingBean(Binder.class)
@Import({ WebSocketMessageChannelBinderConfiguration.class })
public class WebSocketServiceAutoConfiguration {

}