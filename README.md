# WebSocket Stream-Binder for Spring-Cloud-Stream
## Build
In the root folder run:
> `mvn package`

## WebSocket Server
The project `websocket-server` contains a websocket-server that implements a simple websocket-broker based on [STOMP](https://stomp.github.io/).
### Run Server
You can find the Spring-Boot Fat-JAR in `websocket-server/target`.
> `java -jar websocket-server-<version>.jar`
