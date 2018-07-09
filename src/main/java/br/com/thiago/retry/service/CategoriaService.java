package br.com.thiago.retry.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.thiago.retry.model.Categoria;
import br.com.thiago.retry.repositories.CategoriaRepository;
import br.com.thiago.retry.service.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public List<Categoria> list() {
		List<Categoria> list = this.repository.findAll();
		return list;
	}

	public Categoria find(Integer id) {
		Optional<Categoria> categoria = this.repository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado, id:" + id + ", Tipo:" + Categoria.class.getName(), null));
	}

}
