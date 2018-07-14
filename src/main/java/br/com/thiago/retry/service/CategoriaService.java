package br.com.thiago.retry.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.thiago.retry.model.Categoria;
import br.com.thiago.retry.repositories.CategoriaRepository;
import br.com.thiago.retry.service.exceptions.DataIntegrityException;
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

	public Categoria insert(Categoria categoria) {
		Categoria newCategoria = this.repository.save(categoria);
		return newCategoria;
	}

	// malandragem: chame o método só para retornar o erro, não precisa setar nada,
	// salve o que chegou na requisição.
	// dessa forma você nem salva o retorno do método porque não será usado no
	// código.
	public Categoria update(Categoria categoria) {
		find(categoria.getId());
		return this.repository.save(categoria);
	}

	public void delete(Integer id) {
		find(id);
		try {
			this.repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível deletar categorias que possuam produtos associados.");
		}

	}

}
