package org.ln.spring.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ControllerAdvice(assignableTypes = FormController.class)
public class FormAdvice {
	@ExceptionHandler(NoSuchFieldException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Requested item was not found")
	public void itemNotFound() {
	}
}
