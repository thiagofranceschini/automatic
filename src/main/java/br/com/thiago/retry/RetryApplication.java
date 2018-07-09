package br.com.thiago.retry;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.thiago.retry.model.Categoria;
import br.com.thiago.retry.model.Produto;
import br.com.thiago.retry.service.repositories.CategoriaRepository;
import br.com.thiago.retry.service.repositories.ProdutoRepository;

@SpringBootApplication
public class RetryApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;

	public static void main(String[] args) {
		SpringApplication.run(RetryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto("Computador", new BigDecimal(2000.00));
		Produto p2 = new Produto("Impressora", new BigDecimal(800.00));
		Produto p3 = new Produto("mouse", new BigDecimal(80.00));

		cat1.getProdutos().add(p1);
		cat1.getProdutos().add(p2);
		cat1.getProdutos().add(p3);
		cat2.getProdutos().add(p2);

		p1.getCategorias().add(cat1);
		p2.getCategorias().add(cat1);
		p2.getCategorias().add(cat2);
		p3.getCategorias().add(cat1);

		this.categoriaRepository.save(cat1);
		this.categoriaRepository.save(cat2);
		this.produtoRepository.save(p1);
		this.produtoRepository.save(p2);
		this.produtoRepository.save(p3);

	}
}
