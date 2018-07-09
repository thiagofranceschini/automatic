package br.com.thiago.retry.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@RequestMapping(method=RequestMethod.GET)
	public String listar() {
		return "Listagem em teste do constroller de categorias!";
	}
	
}
