package com.joaquim.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joaquim.cursomc.domain.PedidoItem;

@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, Integer>{

}
