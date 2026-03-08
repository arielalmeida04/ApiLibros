package com.company.books.backend.response;

import java.util.List;

import com.company.books.backend.model.Categoria;

public class CategoriaResponse {

	private List<Categoria> listCategoria;

	public List<Categoria> getListCategoria() {
		return listCategoria;
	}

	public void setListCategoria(List<Categoria> listCategoria) {
		this.listCategoria = listCategoria;
	}
}
