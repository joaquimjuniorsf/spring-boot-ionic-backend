package com.joaquim.cursomc.domain;

import java.time.LocalDate;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.joaquim.cursomc.domain.enums.PagamentoStatus;

@Entity
@JsonTypeName("pagamentoComBoleto")
public class PagamentoComBoleto extends Pagamento {
	
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataPagamento;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataVencimeto;
	
	public PagamentoComBoleto() {
	}

	public PagamentoComBoleto(Integer id, PagamentoStatus pagametoStatus, Pedido pedido,LocalDate dataVencimento, LocalDate dataPagamento) {
		super(id, pagametoStatus, pedido);
		
		this.dataPagamento = dataPagamento;
		this.dataVencimeto = dataVencimento;
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
