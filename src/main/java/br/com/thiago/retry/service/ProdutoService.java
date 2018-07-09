package br.com.thiago.retry.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.thiago.retry.model.Produto;
import br.com.thiago.retry.service.repositories.ProdutoRepository;

@Service
public class ProdutoService {
	@Autowired
	private ProdutoRepository repository;

	public List<Produto>list(){
		return this.repository.findAll();
	}
	
	public Produto find(Integer id) {
		Optional<Produto> produto = this.repository.findById(id);
		return produto.orElse(null);
	}
}
