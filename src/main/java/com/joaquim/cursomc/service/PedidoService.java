package com.joaquim.cursomc.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.joaquim.cursomc.domain.Cliente;
import com.joaquim.cursomc.domain.PagamentoComBoleto;
import com.joaquim.cursomc.domain.Pedido;
import com.joaquim.cursomc.domain.enums.PagamentoStatus;
import com.joaquim.cursomc.repositories.ClienteRepository;
import com.joaquim.cursomc.repositories.PagamentoRepository;
import com.joaquim.cursomc.repositories.PedidoItemRepository;
import com.joaquim.cursomc.repositories.PedidoRepository;
import com.joaquim.cursomc.repositories.ProdutoRepository;
import com.joaquim.cursomc.security.UserSS;
import com.joaquim.cursomc.service.exceptions.AuthorizationException;
import com.joaquim.cursomc.service.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository  pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private PedidoItemRepository pedidoItemRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = 	pedidoRepository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	public Pedido insert(Pedido p) {
		p.setId(null);
		p.setInstante(LocalDate.now());
		p.getPagamento().setPagametoStatus(PagamentoStatus.PENDENTE);
		p.getPagamento().setPedido(p);
		if (p.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto) p.getPagamento();
			boletoService.preencherPagamentoComBoleto(pgto, p.getInstante());
		}
		
		pedidoRepository.save(p);
		pagamentoRepository.save(p.getPagamento());
		
		p.getItens().forEach(i -> {
			i.setDesconto(BigDecimal.ZERO);
			i.setPreco(produtoRepository.findById(i.getProduto().getId()).get().getPreco());
			i.setPedido(p);
		});
		
		pedidoItemRepository.saveAll(p.getItens());
		
		emailService.sendOrderConfirmationEmail(p);
		
		return p;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		UserSS 	user = UserService.authenticated();
		
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Cliente cliente = clienteRepository.findById(user.getId()).orElse(null);
		
		return pedidoRepository.findByCliente(cliente, pageRequest);
	}

}
