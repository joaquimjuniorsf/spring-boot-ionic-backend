package com.joaquim.cursomc.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.joaquim.cursomc.domain.Categoria;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Categoria> listar() {
		
		Categoria cat1 = new Categoria(1, "Informática");
		Categoria cat2 = new Categoria(2, "Escritório");
		Categoria cat3 = new Categoria(3, "Cozinha");
		
		List<Categoria>	lista = Arrays.asList(cat1,cat2,cat3);
		
//		List<Categoria>	lista = new ArrayList<>();
//		lista.add(cat1);
//		lista.add(cat2);
//		lista.add(cat3);
		
		return lista;
	}

}
