package br.com.thiago.retry.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.thiago.retry.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

}
