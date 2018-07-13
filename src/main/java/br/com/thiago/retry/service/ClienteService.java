package br.com.thiago.retry.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.thiago.retry.model.Cliente;
import br.com.thiago.retry.repositories.ClienteRepository;
import br.com.thiago.retry.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;

	public List<Cliente> list() {
		List<Cliente> list = this.clienteRepository.findAll();
		return list;
	}

	public Cliente find(Integer id) {
		Optional<Cliente> optional = this.clienteRepository.findById(id);
		return optional.orElseThrow(() -> new ObjectNotFoundException(
				"Cliente não encontrado, id:" + id + ", tipo: " + Cliente.class.getName()));
	}

	public Cliente insert(Cliente cliente) {
		return this.clienteRepository.save(cliente);
	}

	public Cliente update(Cliente cliente) {
		find(cliente.getId());
		return this.clienteRepository.save(cliente);
	}
}
