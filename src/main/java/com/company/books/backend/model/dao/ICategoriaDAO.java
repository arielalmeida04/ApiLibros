package com.company.books.backend.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.company.books.backend.model.Categoria;

public interface ICategoriaDAO extends CrudRepository<Categoria,Long>{

}
