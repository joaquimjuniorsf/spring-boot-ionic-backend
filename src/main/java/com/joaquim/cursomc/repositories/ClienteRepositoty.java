package com.joaquim.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joaquim.cursomc.domain.Cliente;

@Repository
public interface ClienteRepositoty extends JpaRepository<Cliente, Integer>{

}
