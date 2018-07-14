package br.com.thiago.retry.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;

	private List<ErrorFieldMessage> list = new ArrayList<>();

	public ValidationError(Integer status, String message, Long timeStamp) {
		super(status, message, timeStamp);
	}

	public List<ErrorFieldMessage> getErrors() {
		return list;
	}

	public void addError(String fieldName, String message) {
		list.add(new ErrorFieldMessage(fieldName, message));
	}

}
