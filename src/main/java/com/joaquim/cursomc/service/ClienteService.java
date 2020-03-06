package com.joaquim.cursomc.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.joaquim.cursomc.domain.Cidade;
import com.joaquim.cursomc.domain.Cliente;
import com.joaquim.cursomc.domain.Endereco;
import com.joaquim.cursomc.domain.enums.ClienteTipo;
import com.joaquim.cursomc.dto.ClienteDTO;
import com.joaquim.cursomc.dto.ClienteNewDTO;
import com.joaquim.cursomc.repositories.CidadeRepository;
import com.joaquim.cursomc.repositories.ClienteRepository;
import com.joaquim.cursomc.repositories.EnderecoRepository;
import com.joaquim.cursomc.service.exceptions.DataIntegrityException;
import com.joaquim.cursomc.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = 	repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		
		enderecoRepository.saveAll(obj.getEnderecos());
		
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateDate(newObj,obj);
		
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException ex) {
			throw new DataIntegrityException("Não é possível excluir um Cliente porque há entidades relacionadas");
		}
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO obj) {
		return new Cliente(obj.getId(),obj.getNome(),obj.getEmail(),null,null,null);
	}
	
	public Cliente fromDTO(ClienteNewDTO obj) {
		Cliente cli = new Cliente(null,obj.getNome(),obj.getEmail(),obj.getCpfcnpj(),ClienteTipo.toEnum(obj.getClienteTipo()),pe.encode(obj.getSenha()));
		
		Cidade cid = cidadeRepository.findById(obj.getCidadeId()).orElse(null);
		
		Endereco end = new Endereco(null, obj.getLogradouro(), obj.getNumero(), obj.getComplemento(), obj.getBairro(), obj.getCep(), cli,  cid);
		
		cli.getEnderecos().addAll(Arrays.asList(end));
		
		cli.getTelefones().add(obj.getTelefone1());
		
		if (obj.getTelefone2() != null) {
			cli.getTelefones().add(obj.getTelefone2());
		}
		
		if (obj.getTelefone3() != null) {
			cli.getTelefones().add(obj.getTelefone3());
		}
		
		return cli;
	}
	
	private void updateDate(Cliente newObj,Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
