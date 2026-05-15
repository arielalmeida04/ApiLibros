package com.company.books.backend.service;

import org.springframework.http.ResponseEntity;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.response.CategoriaResponseRest;

public interface ICategoriaService {

	public ResponseEntity<CategoriaResponseRest> searchCategoria();
	public ResponseEntity<CategoriaResponseRest> searchCategoriaId(Long id);
	public ResponseEntity<CategoriaResponseRest> create(Categoria requestCreacion);
	public ResponseEntity<CategoriaResponseRest> update(Categoria requestModificacion, Long id);
	public ResponseEntity<CategoriaResponseRest> delete(Long id);
	
}
