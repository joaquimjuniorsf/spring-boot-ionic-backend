package com.joaquim.cursomc.domain;

import java.time.LocalDate;

import javax.persistence.Entity;

import com.joaquim.cursomc.domain.enums.PagamentoStatus;

@Entity
public class PagamentoComBoleto extends Pagamento {
	
	private static final long serialVersionUID = 1L;
	
	private LocalDate dataPagamento;
	private LocalDate dataVencimeto;
	
	public PagamentoComBoleto() {
	}

	public PagamentoComBoleto(Integer id, PagamentoStatus pagametoStatus, Pedido pedido,LocalDate dataPagamento, LocalDate dataVencimeto) {
		super(id, pagametoStatus, pedido);
		
		this.dataPagamento = dataPagamento;
		this.dataVencimeto = dataVencimeto;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public LocalDate getDataVencimeto() {
		return dataVencimeto;
	}

	public void setDataVencimeto(LocalDate dataVencimeto) {
		this.dataVencimeto = dataVencimeto;
	}
	
	
}
