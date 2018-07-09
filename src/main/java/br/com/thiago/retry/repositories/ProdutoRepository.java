package br.com.thiago.retry.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.thiago.retry.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

}
