package com.joaquim.cursomc.domain.enums;

public enum PagamentoStatus {
	PENDENTE(1,"Pendente"),
	QUITADO(2,"Quitado"),
	CANCELADO(3,"Cacelado");
	
	private int codigo;	
	private String descricao;
	
	private PagamentoStatus(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static PagamentoStatus toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}
		
		for (PagamentoStatus ct : PagamentoStatus.values()) {
			if (codigo.equals(ct.getCodigo())) {
				return ct;
			}
		}
		
		throw new IllegalArgumentException("código inválido" + codigo);
	}
}
