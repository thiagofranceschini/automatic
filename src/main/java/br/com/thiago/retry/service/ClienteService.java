package br.com.thiago.retry.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.thiago.retry.dto.ClienteDTO;
import br.com.thiago.retry.dto.ClienteNewDTO;
import br.com.thiago.retry.model.Cidade;
import br.com.thiago.retry.model.Cliente;
import br.com.thiago.retry.model.Endereco;
import br.com.thiago.retry.model.enums.TipoCliente;
import br.com.thiago.retry.repositories.ClienteRepository;
import br.com.thiago.retry.repositories.EnderecoRepository;
import br.com.thiago.retry.service.exceptions.DataIntegrityException;
import br.com.thiago.retry.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;

	public List<Cliente> list() {
		List<Cliente> list = this.clienteRepository.findAll();
		return list;
	}

	public Cliente find(Integer id) {
		Optional<Cliente> optional = this.clienteRepository.findById(id);
		return optional.orElseThrow(() -> new ObjectNotFoundException(
				"Cliente não encontrado, id:" + id + ", tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		clienteRepository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
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

	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cliente = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),
				TipoCliente.toEnum(objDto.getTipo()));
		
		Cidade cidade = new Cidade(objDto.getCidadeId(), null, null);
		Endereco endereco = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), cliente, cidade);
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2() != null) {
			cliente.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3() != null) {
			cliente.getTelefones().add(objDto.getTelefone3());
		}
		return cliente;
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
