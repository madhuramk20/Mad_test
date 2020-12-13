package my.com.api.user.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javassist.NotFoundException;
import my.com.api.exceptions.InvalidRequestException;
import my.com.api.user.dto.ErrorResponseDTO;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, getErrorMessageObjFromException(ex), new HttpHeaders(), HttpStatus.CONFLICT,
				request);
	}

	@ExceptionHandler(value = { InvalidRequestException.class })
	protected ResponseEntity<Object> handleInvalidRequestParam(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, getErrorMessageObjFromException(ex), new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = { NotFoundException.class })
	protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, getErrorMessageObjFromException(ex), new HttpHeaders(), HttpStatus.NOT_FOUND,
				request);
	}

	@ExceptionHandler(value = { RuntimeException.class })
	protected ResponseEntity<Object> handleServerError(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, getErrorMessageObjFromException(ex), new HttpHeaders(),
				HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	private ErrorResponseDTO getErrorMessageObjFromException(RuntimeException ex) {
		return ErrorResponseDTO.builder().errorMessage(ex.getMessage()).build();
	}
}
