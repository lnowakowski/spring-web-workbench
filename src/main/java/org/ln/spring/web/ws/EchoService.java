package org.ln.spring.web.ws;

import javax.annotation.PostConstruct;

import org.ln.spring.web.ws.model.SayEchoRequest;
import org.ln.spring.web.ws.model.SayEchoResponse;
import org.springframework.stereotype.Service;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@Service
public class EchoService {
	@PostConstruct
	public void init() {
		System.out.println("EchoService WS created");
	}
	
	@PayloadRoot(localPart = "sayEchoRequest", namespace = "http://ln.org/spring/ws/echo")
	@ResponsePayload
	public SayEchoResponse sayEcho(@RequestPayload SayEchoRequest request) {
		return new SayEchoResponse(String.format("echo: %s [%d]", request.getValue(), System.currentTimeMillis()));
	}
}
