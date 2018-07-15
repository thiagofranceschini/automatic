package br.com.thiago.retry.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.thiago.retry.model.Categoria;
import br.com.thiago.retry.model.Produto;
import br.com.thiago.retry.repositories.CategoriaRepository;
import br.com.thiago.retry.repositories.ProdutoRepository;
import br.com.thiago.retry.service.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;
	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<Produto> list() {
		List<Produto> list = this.repository.findAll();
		return list;
	}

	public Produto findById(Integer id) {
		Optional<Produto> obj = this.repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado , id :" + id + ", tipo: " + Produto.class.getName()));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		
		return repository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}
