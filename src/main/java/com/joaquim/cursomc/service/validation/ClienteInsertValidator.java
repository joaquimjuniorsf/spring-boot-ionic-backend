package com.joaquim.cursomc.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.joaquim.cursomc.domain.Cliente;
import com.joaquim.cursomc.domain.enums.ClienteTipo;
import com.joaquim.cursomc.dto.ClienteNewDTO;
import com.joaquim.cursomc.repositories.ClienteRepository;
import com.joaquim.cursomc.resources.exceptions.FieldMessage;
import com.joaquim.cursomc.service.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getClienteTipo().equals(ClienteTipo.PESSOA_FISICA.getCodigo()) && !BR.isValidCPF(objDto.getCpfcnpj())) {
			list.add(new FieldMessage("cpfcnpj","CPF Inválido"));
		}
		
		if (objDto.getClienteTipo().equals(ClienteTipo.PESSOA_JURIDICA.getCodigo()) && !BR.isValidCNPJ(objDto.getCpfcnpj())) {
			list.add(new FieldMessage("cpfcnpj","CNPJ Inválido"));
		}
		
		Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
		
		if (aux != null) {
			list.add(new FieldMessage("email","Email já existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
