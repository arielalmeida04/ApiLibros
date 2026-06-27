package com.company.books.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.ResponseEntity;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.model.dao.ICategoriaDAO;
import com.company.books.backend.response.CategoriaResponseRest;

public class CategoriaIMPLTest {

	@InjectMocks
	CategoriaServiceIMPL service;
	
	@Mock
	 ICategoriaDAO categoriaDAO;
	
	List<Categoria> listCategorias = new ArrayList<Categoria>();
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		this.realoadCategorias();
	}

	@Test
	public void searchCategoria() {
	    when(categoriaDAO.findAll()).thenReturn(listCategorias);

	    ResponseEntity<CategoriaResponseRest> response = service.searchCategoria();

	    assertEquals(3, response.getBody().getCr().getListCategoria().size());
	    verify(categoriaDAO, times(1)).findAll();
	}

	public void realoadCategorias() {
		Categoria categoria0 = new Categoria(Long.valueOf(1), "Librito de prueba 0", "Este es un libro de prueba unitaria 0");
		Categoria categoria1 = new Categoria(Long.valueOf(2), "Librito de prueba 1", "Este es un libro de prueba unitaria 1");
		Categoria categoria2 = new Categoria(Long.valueOf(3), "Librito de prueba 2", "Este es un libro de prueba unitaria 2");
		listCategorias.add(categoria0);
		listCategorias.add(categoria1);
		listCategorias.add(categoria2);
	}
}