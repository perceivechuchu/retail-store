package ca.company.store.retail.api.exception.handler;

/**   
* @author  Perceive Chuchu
*/ 

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ca.company.store.retail.api.response.ApiResponse;
import ca.company.store.retail.commons.exceptions.RecordNotFoundException;
import lombok.extern.java.Log;

@Log
@ControllerAdvice
public class BusinessExceptionHandlerAdvice {
		
	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	@ResponseBody
	public ApiResponse<String> handleHttpMethodNotSupportedException(final HttpRequestMethodNotSupportedException exe) {
		log.info("Custom Formatter Exception: " + exe.getMessage());
		final ApiResponse<String> exceptionResponse = constructExceptionResponse(exe, HttpStatus.NOT_FOUND);
		log.info("Returning Formatter Error Response : " + exceptionResponse);
		return exceptionResponse;
	}
	
	@ExceptionHandler({ RecordNotFoundException.class })
	@ResponseBody
	public ApiResponse<String> handleRestResourceNotFoundException(final Exception exe) {
		log.info("Custom Formatter Exception: " + exe.getMessage());
		final ApiResponse<String> exceptionResponse = constructExceptionResponse(exe, HttpStatus.NOT_FOUND);
		log.info("Returning Formatter Error Response : " + exceptionResponse);
		return exceptionResponse;
	}

	private ApiResponse<String> constructExceptionResponse(final Exception exe, HttpStatus responseStatus){
		final ApiResponse<String> exceptionResponse = new ApiResponse<>();
		exceptionResponse.setMessage(exe.getMessage());
		exceptionResponse.setStatusCode(responseStatus.toString());
		return exceptionResponse;
	}
	
	@ExceptionHandler
	@ResponseBody
	public ApiResponse<String> handleGenericException(final Exception x) {
		log.info("Generic Exception thrown when processing request : " + x.getMessage());
		final ApiResponse<String> exceptionResponse = new ApiResponse<>();
		exceptionResponse.setMessage(x.getMessage());
		exceptionResponse.setStatusCode(resolveAnnotatedResponseStatus(x));
		log.info("Returning an exception response : " + exceptionResponse);
		return exceptionResponse;
	}
	
	private String resolveAnnotatedResponseStatus(final Exception x) {
		final ResponseStatus annotation = AnnotatedElementUtils.findMergedAnnotation(x.getClass(), ResponseStatus.class);
		return (annotation != null ? annotation.value() : HttpStatus.INTERNAL_SERVER_ERROR).toString();
	}
}
