package br.com.thiago.retry.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.thiago.retry.model.Pedido;
import br.com.thiago.retry.repositories.PedidoRepository;
import br.com.thiago.retry.service.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	public List<Pedido> list() {
		List<Pedido> list = this.repository.findAll();
		return list;
	}

	public Pedido findById(Integer id) {
		Optional<Pedido> pedido = this.repository.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado , id :" + id + ", tipo: " + Pedido.class.getName()));
	}
}
