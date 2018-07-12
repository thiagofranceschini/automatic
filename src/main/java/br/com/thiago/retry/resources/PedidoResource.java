package br.com.thiago.retry.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiago.retry.model.Pedido;
import br.com.thiago.retry.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService pedidoService;

	@RequestMapping(value = "/{pedidoId}", method = RequestMethod.GET)
	public ResponseEntity<?> findPedido(@PathVariable Integer pedidoId) {
		Pedido pedido = this.pedidoService.findById(pedidoId);
		return ResponseEntity.ok().body(pedido);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> listPedidos() {
		List<Pedido> list = this.pedidoService.list();
		return ResponseEntity.ok().body(list);
	}

}
