package org.codescale.websocket.server;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class WebSocketServer {

	public static void main(String[] args) {
		new SpringApplicationBuilder().bannerMode(Banner.Mode.CONSOLE).sources(WebSocketServer.class).run(args);
	}
}