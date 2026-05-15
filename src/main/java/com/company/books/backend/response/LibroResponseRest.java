package com.company.books.backend.response;

public class LibroResponseRest extends ResponseRest{

    private LibroResponse lr = new LibroResponse();

    public LibroResponse getLr(){
        return lr;
    }

    public void setLr(LibroResponse lr){
        this.lr = lr;
    }

}
