package com.joaquim.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joaquim.cursomc.domain.Endereco;

@Repository
public interface EnderecoRepositoty extends JpaRepository<Endereco, Integer>{

}
