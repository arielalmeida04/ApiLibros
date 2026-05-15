package com.company.books.backend.service;

import org.springframework.http.ResponseEntity;

import com.company.books.backend.model.Libro;
import com.company.books.backend.response.LibroResponseRest;

public interface ILibroService {

	public ResponseEntity<LibroResponseRest> searchLibros();
	public ResponseEntity<LibroResponseRest> searchLibrosById(Long id);
	public ResponseEntity<LibroResponseRest> createLibros(Libro request);
	public ResponseEntity<LibroResponseRest> updateLibro (Libro libroUpdate, Long id);
	public ResponseEntity<LibroResponseRest> deleteLibro(Long id);
}
