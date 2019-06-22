package com.jsware.fizz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Controller
@ControllerAdvice
public class FizzErrorHandler {
	
	@ExceptionHandler({FizzException.class})
	public final ResponseEntity<ErrorDetail> handleException(FizzException ex, WebRequest req)
	{
		ErrorDetail error = new ErrorDetail(
				ex.getMessage(),
				req.getDescription(false),
				ex.getData());
		return new ResponseEntity<ErrorDetail>(error,HttpStatus.CONFLICT);
	}

}
