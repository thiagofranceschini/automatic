package br.com.thiago.retry;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.thiago.retry.model.Categoria;
import br.com.thiago.retry.model.Cidade;
import br.com.thiago.retry.model.Cliente;
import br.com.thiago.retry.model.Endereco;
import br.com.thiago.retry.model.Estado;
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
import br.com.thiago.retry.repositories.PagamentoRepository;
import br.com.thiago.retry.repositories.PedidoRepository;
import br.com.thiago.retry.repositories.ProdutoRepository;

@SpringBootApplication
public class RetryApplication implements CommandLineRunner {

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


		this.categoriaRepository.save(cat1);
		this.categoriaRepository.save(cat2);

		this.produtoRepository.save(p1);
		this.produtoRepository.save(p2);
		this.produtoRepository.save(p3);

		this.estadoRepository.save(est1);
		this.estadoRepository.save(est2);
		this.cidadeRepository.save(c1);
		this.cidadeRepository.save(c2);
		this.cidadeRepository.save(c3);

		this.clienteRepository.save(cli1);
		this.enderecoRepository.save(e1);
		this.enderecoRepository.save(e2);

		this.pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		this.pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto1));
	}
}
