package br.com.thiago.retry.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiago.retry.model.Cliente;
import br.com.thiago.retry.repositories.ClienteRepository;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResources {

	@Autowired
	private ClienteRepository clienteRepository;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> list() {
		List<Cliente> list = this.clienteRepository.findAll();
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Optional<Cliente> optional = this.clienteRepository.findById(id);
		return ResponseEntity.ok().body(optional);
	}
}
