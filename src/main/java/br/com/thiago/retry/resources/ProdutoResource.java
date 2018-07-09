package br.com.thiago.retry.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiago.retry.model.Produto;
import br.com.thiago.retry.service.ProdutoService;

@RestController
@RequestMapping(value = "/prdutos")
public class ProdutoResource {
	@Autowired
	private ProdutoService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> list() {
		List<Produto> list = this.service.list();
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Produto produto = this.service.find(id);
		return ResponseEntity.ok().body(produto);
	}

}
