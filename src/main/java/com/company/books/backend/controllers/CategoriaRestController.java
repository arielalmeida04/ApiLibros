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

import com.company.books.backend.model.Categoria;
import com.company.books.backend.response.CategoriaResponseRest;
import com.company.books.backend.service.ICategoriaService;

@RestController
@RequestMapping("/v1")
public class CategoriaRestController {

	@Autowired
	private ICategoriaService service;
	
	@GetMapping("/categorias")
	public ResponseEntity<CategoriaResponseRest> consultaCat() 
	{
		  ResponseEntity<CategoriaResponseRest> response  = service.searchCategoria();
		  return response;
	}

	@GetMapping("/categorias/{id}")
	public ResponseEntity<CategoriaResponseRest> consultaCatId(@PathVariable Long id)
	{
		ResponseEntity<CategoriaResponseRest> response = service.searchCategoriaId(id);
		return response;
		
		
	}
	@PostMapping("/categorias")
	public ResponseEntity<CategoriaResponseRest> createCategoria(@RequestBody Categoria request)
	{
		ResponseEntity<CategoriaResponseRest> response = service.create(request);
		return response;
		
	}
	
	@PutMapping("/categorias/{id}")
	public ResponseEntity<CategoriaResponseRest> updateCategoria(@RequestBody Categoria request,@PathVariable Long id)
	{
		ResponseEntity<CategoriaResponseRest> response = service.update(request, id);
		return response;
		
	}
	@DeleteMapping("/categorias/{id}")
	public ResponseEntity<CategoriaResponseRest> deleteCategopria(@PathVariable Long id)
	{
		ResponseEntity<CategoriaResponseRest> response = service.delete(id);
		return response;
		
	}
}
