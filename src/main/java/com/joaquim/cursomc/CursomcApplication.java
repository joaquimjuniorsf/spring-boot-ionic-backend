package com.joaquim.cursomc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.joaquim.cursomc.domain.Categoria;
import com.joaquim.cursomc.domain.Cidade;
import com.joaquim.cursomc.domain.Cliente;
import com.joaquim.cursomc.domain.Endereco;
import com.joaquim.cursomc.domain.Estado;
import com.joaquim.cursomc.domain.Pagamento;
import com.joaquim.cursomc.domain.PagamentoComBoleto;
import com.joaquim.cursomc.domain.PagamentoComCartao;
import com.joaquim.cursomc.domain.Pedido;
import com.joaquim.cursomc.domain.PedidoItem;
import com.joaquim.cursomc.domain.Produto;
import com.joaquim.cursomc.domain.enums.ClienteTipo;
import com.joaquim.cursomc.domain.enums.PagamentoStatus;
import com.joaquim.cursomc.repositories.CategoriaRepository;
import com.joaquim.cursomc.repositories.CidadeRepository;
import com.joaquim.cursomc.repositories.ClienteRepository;
import com.joaquim.cursomc.repositories.EnderecoRepository;
import com.joaquim.cursomc.repositories.EstadoRepository;
import com.joaquim.cursomc.repositories.PagamentoRepository;
import com.joaquim.cursomc.repositories.PedidoItemRepository;
import com.joaquim.cursomc.repositories.PedidoRepository;
import com.joaquim.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepositoty;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepositoty;
	
	@Autowired
	private ClienteRepository clienteRepositoty;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private PedidoItemRepository pedidoItemRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama Mesa e Banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 2000.00);
		Produto p3 = new Produto(null, "Mouse", 2000.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().add(p2);

		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().add(cat1);

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2,cat3,cat4,cat5,cat6));
		produtoRepositoty.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "12345678901", ClienteTipo.PESSOA_FISICA);
		cli1.getTelefones().addAll(Arrays.asList("(38) 1234-5679", "(11) 12345678"));

		Endereco end1 = new Endereco(null, "Rua das Flores", "24", "Apto 76", "Centro", "05541-000", cli1, c1);
		Endereco end2 = new Endereco(null, "Rua das Pedras", "69", "Apto 171", "Centro", "05541-000", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));

		clienteRepositoty.saveAll(Arrays.asList(cli1));
		enderecoRepositoty.saveAll(Arrays.asList(end1, end2));

		Pedido ped1 = new Pedido(null, LocalDate.now(), cli1, end1);
		Pedido ped2 = new Pedido(null, LocalDate.now(), cli1, end2);

		Pagamento pagto1 = new PagamentoComCartao(null, PagamentoStatus.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, PagamentoStatus.PENDENTE, ped2, null, LocalDate.now());
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));

		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

		PedidoItem pi1 = new PedidoItem(ped1, p1, new BigDecimal(0.00), 1, new BigDecimal(2000.00));
		PedidoItem pi2 = new PedidoItem(ped1, p3, new BigDecimal(0.00), 2, new BigDecimal(80.00));
		PedidoItem pi3 = new PedidoItem(ped1, p3, new BigDecimal(0.00), 2, new BigDecimal(80.00));

		ped1.getItens().addAll(Arrays.asList(pi1, pi2));
		ped2.getItens().addAll(Arrays.asList(pi3));

		p1.getItens().addAll(Arrays.asList(pi1));
		p2.getItens().addAll(Arrays.asList(pi3));
		p3.getItens().addAll(Arrays.asList(pi1));

		pedidoItemRepository.saveAll(Arrays.asList(pi1, pi2, pi3));
	}

}
