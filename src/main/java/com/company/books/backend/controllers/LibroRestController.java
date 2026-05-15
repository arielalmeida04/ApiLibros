package com.company.books.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.books.backend.model.Libro;
import com.company.books.backend.response.LibroResponseRest;
import com.company.books.backend.service.ILibroService;


@RestController
@RequestMapping("/v1")
public class LibroRestController {

    @Autowired
    private ILibroService libritoService;

    @GetMapping("/libros")
    public ResponseEntity<LibroResponseRest> getListaLibros(){
        ResponseEntity<LibroResponseRest> response = libritoService.searchLibros();
        return response;
    }
    
    @GetMapping("/libros/{id}")
    public ResponseEntity<LibroResponseRest> getLibroById(@PathVariable Long id){
        ResponseEntity<LibroResponseRest> response = libritoService.searchLibrosById(id);
        return response;
    }
    @PostMapping("/libros")
    public ResponseEntity<LibroResponseRest> createLibro(@RequestBody Libro request)
    {
		ResponseEntity<LibroResponseRest> response = libritoService.createLibros(request);
    	return response;
    	
    }
    @PutMapping("/libros/{id}")
    public ResponseEntity<LibroResponseRest> updateLibro(@RequestBody Libro request, @PathVariable Long id)
    {
    	ResponseEntity<LibroResponseRest> response = libritoService.updateLibro(request, id);
		return response;
    	
    }

    @DeleteMapping("/libros/{id}")
    public ResponseEntity<LibroResponseRest> delteLibro(@PathVariable Long id)
    {
    	ResponseEntity<LibroResponseRest> response = libritoService.deleteLibro(id);
    	return response;
    }
}
