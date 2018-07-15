package br.com.thiago.retry.resources.exception;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.thiago.retry.service.exceptions.DataIntegrityException;
import br.com.thiago.retry.service.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException exception,
			HttpServletRequest request) {

		StandardError standardError = new StandardError(HttpStatus.NOT_FOUND.value(), exception.getMessage(),
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrityException(DataIntegrityException exception,
			HttpServletRequest request) {

		StandardError standardError = new StandardError(HttpStatus.BAD_REQUEST.value(), exception.getMessage(),
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> methodArgumentNotValidException(MethodArgumentNotValidException e,
			HttpServletRequest requests) {

		ValidationError validationError = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação.",
				System.currentTimeMillis());
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			validationError.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
	}

	// ValidationException validação do número inteiro que causa null pointer no
	// prórpio método de validação
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<StandardError> validationException(ValidationException e, HttpServletRequest requests) {

		ValidationError validationError = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação.",
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
	}
}
