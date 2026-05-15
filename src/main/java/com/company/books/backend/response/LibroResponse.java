package com.company.books.backend.response;

import java.util.List;

import com.company.books.backend.model.Libro;

public class LibroResponse {

        private List<Libro> listLibro;

    public List<Libro> getListLibro() {
        return listLibro;
    }

    public void setListLibro(List<Libro> listLibro) {
        this.listLibro = listLibro;
    }

}
