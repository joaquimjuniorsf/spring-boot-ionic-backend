package com.joaquim.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joaquim.cursomc.domain.Produto;

@Repository
public interface ProdutoRepositoty extends JpaRepository<Produto, Integer>{

}
