package br.com.thiago.retry.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.thiago.retry.model.Produto;

public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	private BigDecimal preco;

	public ProdutoDTO() {
		super();
	}

	public ProdutoDTO(Produto produto) {
		this.id = produto.getId();
		this.nome = produto.getNome();
		this.preco = produto.getPreco();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

}
