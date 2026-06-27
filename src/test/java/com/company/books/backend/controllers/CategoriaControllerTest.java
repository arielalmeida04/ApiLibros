package com.company.books.backend.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.response.CategoriaResponseRest;
import com.company.books.backend.service.ICategoriaService;

public class CategoriaControllerTest {

	@InjectMocks
	CategoriaRestController categoriaController;
	
	@Mock
	ICategoriaService  service;
	
	@BeforeEach
	 void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createTest() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		Categoria categoria = new Categoria(Long.valueOf(1), "Terror", "Libros asociados al temor, miedo, terror psicologico");
		
			when(service.create(any(Categoria.class))).thenReturn(new ResponseEntity<CategoriaResponseRest>(HttpStatus.OK));
			ResponseEntity<CategoriaResponseRest> respuesta = categoriaController.createCategoria(categoria);
			
			assertThat(respuesta.getStatusCodeValue()).isEqualTo(200);
		}
	}

