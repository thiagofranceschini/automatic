package br.com.thiago.retry.dto;

import java.io.Serializable;

import br.com.thiago.retry.model.Categoria;

public class CategoriaDTO implements Serializable {

	/**
	 * DataTrasferObject -> responsável por trafegar dados de forma eficiênte. ex:
	 * posso enviar para o front as categorias sem os objetos associados à mesma.
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;

	public CategoriaDTO() {
		super();
	}

	public CategoriaDTO(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public CategoriaDTO(Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
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

}
