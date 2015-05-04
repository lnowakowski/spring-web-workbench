package org.ln.spring.web.controller;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FirstController {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/")
	public String handleIndex() {
		return "index";
	}
	
	@RequestMapping("/showMessage.html")
	public String handleShowMessage() {
		URI uri = fromMethodCall(on(SimpleRestController.class).handleTest(1, null, null)).build().toUri();
		
		log.info("URI: {}", uri);
		
		return "showMessage";
	}
}
