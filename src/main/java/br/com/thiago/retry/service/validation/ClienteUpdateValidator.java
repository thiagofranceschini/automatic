package br.com.thiago.retry.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.thiago.retry.dto.ClienteDTO;
import br.com.thiago.retry.model.Cliente;
import br.com.thiago.retry.repositories.ClienteRepository;
import br.com.thiago.retry.resources.exception.ErrorFieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private HttpServletRequest request;

	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));

		List<ErrorFieldMessage> list = new ArrayList<>();
		// inclua os testes aqui, inserindo erros na lista:

		Cliente cliente = clienteRepository.findByEmail(objDto.getEmail());
		if (cliente != null && !cliente.getId().equals(uriId)) {
			list.add(new ErrorFieldMessage("email", "e-mail já existente"));
		}

		for (ErrorFieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}