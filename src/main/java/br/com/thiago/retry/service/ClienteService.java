package br.com.thiago.retry.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.thiago.retry.dto.ClienteDTO;
import br.com.thiago.retry.model.Cliente;
import br.com.thiago.retry.repositories.ClienteRepository;
import br.com.thiago.retry.service.exceptions.DataIntegrityException;
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
		Cliente dbObj = find(cliente.getId());
		updateData(dbObj, cliente);
		return this.clienteRepository.save(dbObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			this.clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível deletar clientes que possuam ELEMENTOS ASSOCIADOS.");
		}

	}

	public Cliente fromDTO(ClienteDTO cliente) {
		return new Cliente(cliente.getId(), cliente.getNome(), cliente.getEmail(), null, null);

	}

	public Page<Cliente> findByPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return this.clienteRepository.findAll(pageRequest);
	}

	private void updateData(Cliente dbObject, Cliente obj) {
		dbObject.setEmail(obj.getEmail());
		dbObject.setNome(obj.getNome());
	}
}
