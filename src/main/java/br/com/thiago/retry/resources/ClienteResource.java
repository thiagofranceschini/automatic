package br.com.thiago.retry.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.thiago.retry.dto.ClienteDTO;
import br.com.thiago.retry.dto.ClienteNewDTO;
import br.com.thiago.retry.model.Cliente;
import br.com.thiago.retry.service.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> list() {
		List<Cliente> list = this.clienteService.list();
		List<ClienteDTO> listDTO = list.stream().map(cliente -> new ClienteDTO(cliente))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findCliente(@PathVariable Integer id) {
		Cliente cliente = this.clienteService.find(id);
		return ResponseEntity.ok().body(cliente);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> insertCliente(@Valid @RequestBody ClienteNewDTO clienteNewDTO) {
		Cliente newCliente = this.clienteService.fromDTO(clienteNewDTO);
		newCliente = this.clienteService.insert(newCliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCliente.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateCliente(@Valid @RequestBody ClienteDTO clienteDTO,
			@PathVariable Integer id) {
		clienteDTO.setId(id);
		Cliente cliente = this.clienteService.fromDTO(clienteDTO);
		cliente = this.clienteService.update(cliente);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCliente(@PathVariable Integer id) {
		this.clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> FindPageable(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "4") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		Page<Cliente> findByPage = this.clienteService.findByPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> pageList = findByPage.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(pageList);

	}

}
