package br.com.thiago.retry.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.thiago.retry.model.Cliente;
import br.com.thiago.retry.service.validation.ClienteUpdate;
@ClienteUpdate
public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	@NotEmpty(message = "Preenchimento Obrigatório.")
	@Length(min = 5, max = 25, message = "Deve conter entre 5 e 25 caracteres")
	private String nome;
	@NotEmpty(message = "Preenchimento Obrigatório")
	@Email(message = "E-mail inválido.")
	private String email;

	public ClienteDTO() {
		super();
	}

	public ClienteDTO(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
