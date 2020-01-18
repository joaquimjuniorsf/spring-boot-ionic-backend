package com.joaquim.cursomc.domain.enums;

public enum ClienteTipo {
	PESSOA_FISICA(1,"Pessoa Física"),
	PESSOA_JURIDICA(2,"Pessoa Jurídica");
	
	private int codigo;
	private String descricao;
	private ClienteTipo(int codigo, String descricao) {
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
	
	public static ClienteTipo toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}
		
		for (ClienteTipo ct : ClienteTipo.values()) {
			if (codigo.equals(ct.getCodigo())) {
				return ct;
			}
		}
		
		throw new IllegalArgumentException("código inválido" + codigo);
	}
}
