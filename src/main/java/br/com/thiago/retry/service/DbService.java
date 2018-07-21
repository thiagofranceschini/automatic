package br.com.thiago.retry.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.thiago.retry.model.Categoria;
import br.com.thiago.retry.model.Cidade;
import br.com.thiago.retry.model.Cliente;
import br.com.thiago.retry.model.Endereco;
import br.com.thiago.retry.model.Estado;
import br.com.thiago.retry.model.ItemPedido;
import br.com.thiago.retry.model.Pagamento;
import br.com.thiago.retry.model.PagamentoComBoleto;
import br.com.thiago.retry.model.PagamentoComCartao;
import br.com.thiago.retry.model.Pedido;
import br.com.thiago.retry.model.Produto;
import br.com.thiago.retry.model.enums.EstadoPagamento;
import br.com.thiago.retry.model.enums.TipoCliente;
import br.com.thiago.retry.repositories.CategoriaRepository;
import br.com.thiago.retry.repositories.CidadeRepository;
import br.com.thiago.retry.repositories.ClienteRepository;
import br.com.thiago.retry.repositories.EnderecoRepository;
import br.com.thiago.retry.repositories.EstadoRepository;
import br.com.thiago.retry.repositories.ItemPedidoRepository;
import br.com.thiago.retry.repositories.PagamentoRepository;
import br.com.thiago.retry.repositories.PedidoRepository;
import br.com.thiago.retry.repositories.ProdutoRepository;

@Service
public class DbService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public void instantiateDataBase() throws ParseException {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Puppets");
		Categoria cat4 = new Categoria(null, "Utilidades");
		Categoria cat5 = new Categoria(null, "Vestuário");
		Categoria cat6 = new Categoria(null, "Infatil");
		Categoria cat7 = new Categoria(null, "Cama, mesa e banho");

		Produto p1 = new Produto("Computador", new BigDecimal(2000.00));
		Produto p2 = new Produto("Impressora", new BigDecimal(800.00));
		Produto p3 = new Produto("mouse", new BigDecimal(80.00));
		Produto p4 = new Produto("mesa de escritório", new BigDecimal(300.00));
		Produto p5 = new Produto("Toalha", new BigDecimal(50.00));
		Produto p6 = new Produto("Colcha", new BigDecimal(200.00));
		Produto p7 = new Produto("Tv Lcd", new BigDecimal(1200.00));
		Produto p8 = new Produto("Roçadeira", new BigDecimal(800.00));
		Produto p9 = new Produto("Abajour", new BigDecimal(100.00));
		Produto p10 = new Produto("Pendente", new BigDecimal(180.00));
		Produto p11 = new Produto("Shampoo", new BigDecimal(90.00));

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));

		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().add(c1);
		est2.getCidades().add(c2);
		est2.getCidades().add(c3);

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().add("27363326");
		cli1.getTelefones().add("93838393");
		Endereco e1 = new Endereco(null, "Rua flores", "300", "Apto303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38771212", cli1, c2);
		cli1.getEnderecos().add(e1);
		cli1.getEnderecos().add(e2);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, dateFormat.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, dateFormat.parse("10/10/2017 19:35"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2,
				dateFormat.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, new BigDecimal(0.00), 1, new BigDecimal(2000.00));
		ItemPedido ip2 = new ItemPedido(ped1, p3, new BigDecimal(0.00), 2, new BigDecimal(80.00));
		ItemPedido ip3 = new ItemPedido(ped2, p2, new BigDecimal(100.00), 1, new BigDecimal(800.00));

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		this.categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		this.produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		this.estadoRepository.saveAll(Arrays.asList(est1, est2));
		this.cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		this.clienteRepository.save(cli1);
		this.enderecoRepository.saveAll(Arrays.asList(e1, e2));
		this.pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		this.pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto1));
		this.itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
}
