package br.com.thiago.retry.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.thiago.retry.dto.ClienteNewDTO;
import br.com.thiago.retry.model.Cliente;
import br.com.thiago.retry.model.enums.TipoCliente;
import br.com.thiago.retry.repositories.ClienteRepository;
import br.com.thiago.retry.resources.exception.ErrorFieldMessage;
import br.com.thiago.retry.service.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

		List<ErrorFieldMessage> list = new ArrayList<>();
		// inclua os testes aqui, inserindo erros na lista

		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && (!BR.isValidCPF(objDto.getCpfOuCnpj()))) {
			list.add(new ErrorFieldMessage("cpfOuCnpj", "CPF inválido."));
		}
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && (!BR.isValidCNPJ(objDto.getCpfOuCnpj()))) {
			list.add(new ErrorFieldMessage("cpfOuCnpj", "CNPJ inválido."));
		}

		Cliente cliente = clienteRepository.findByEmail(objDto.getEmail());
		if (cliente != null) {
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