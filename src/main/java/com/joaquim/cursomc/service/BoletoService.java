package com.joaquim.cursomc.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.joaquim.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {
	public void preencherPagamentoComBoleto(PagamentoComBoleto p, LocalDate instanteDoPedido) {
		p.setDataVencimeto(instanteDoPedido.plusDays(7));
	}
}
