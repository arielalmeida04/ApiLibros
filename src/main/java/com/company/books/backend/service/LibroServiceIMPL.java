package com.company.books.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.books.backend.model.Libro;
import com.company.books.backend.model.dao.ILibroDAO;
import com.company.books.backend.response.LibroResponseRest;

@Service
public class LibroServiceIMPL implements ILibroService {
    private static final Logger log = LoggerFactory.getLogger(LibroServiceIMPL.class);
    
    private final ILibroDAO libroDAO;
    
      public LibroServiceIMPL(ILibroDAO libroDAO) {
      this.libroDAO = libroDAO;
      }
     
    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<LibroResponseRest> searchLibros() {
        log.info("Inicio de Busqueda de los libros");
        LibroResponseRest response = new LibroResponseRest();

        try {
            List<Libro> listLibros = (List<Libro>) libroDAO.findAll();
            response.getLr().setListLibro(listLibros);

            response.setHashMap("Respuesta ok", "00", "Respuesta exitosa");
         // 
        } catch (Exception e) {
            response.setHashMap("Respuesta negativa", "500", "Respuesta sin éxito");
            log.error("Error en obtener las listas de Libros", e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK); // Tiene que devolver 200
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<LibroResponseRest> searchLibrosById(Long id) {
        log.info("Inicio de Busqueda de los libros por id");
        LibroResponseRest response = new LibroResponseRest();  
    try {
            List<Libro> listLibros = new ArrayList<>();
            Optional<Libro> librito = libroDAO.findById(id);

            if (librito.isPresent()) {
                listLibros.add(librito.get());
                response.getLr().setListLibro(listLibros);
                response.setHashMap("Respuesta ok", "00", "Respuesta exitosa");
            } else {
                response.setHashMap("Respuesta negativa", "404", "Respuesta sin exito");
                
            }
    } catch (Exception e) {
        response.setHashMap("Respuesta negativa", "500", "Respuesta sin éxito");
        log.error("Error en buscar un libro", e);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> createLibros(Libro request) {
		log.info("Se inicio operacion de creacion de libro");
		LibroResponseRest response = new LibroResponseRest();
		
		  try {
	            List<Libro> listLibros = new ArrayList<>();
	            Libro librito = libroDAO.save(request);
	            if (librito != null) {
	            	listLibros.add(librito);
	                response.getLr().setListLibro(listLibros);
	                response.setHashMap("Respuesta ok", "00", "Respuesta exitosa");
	                log.info("Se creo el libro");
	            } else {
	                response.setHashMap("Respuesta negativa", "404", "Respuesta sin exito");
	                
	            }
		  } catch (Exception e) {
			    response.setHashMap("Respuesta negativa", "500", "Respuesta sin éxito");
			    log.error("Error en crear un libro", e);
			    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		  return new ResponseEntity<>(response, HttpStatus.OK);
		}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> updateLibro(Libro libroUpdate, Long id) {
		  log.info("Inicio de modificacion del libro por id");
	        LibroResponseRest response = new LibroResponseRest();  
	    try {
	            List<Libro> listLibros = new ArrayList<>();
	            Optional<Libro> librito = libroDAO.findById(id);

	            if (librito.isPresent()) {
	            	
	            	Libro libroExistente = librito.get();
	            	libroExistente.setName(libroUpdate.getName());
	            	libroExistente.setDescripcion(libroUpdate.getDescripcion());
	            	libroExistente.setCategoria(libroUpdate.getCategoria());

	            	Libro libritoGuardado = libroDAO.save(libroExistente);
	            if (libritoGuardado != null) {
	            	listLibros.add(libritoGuardado);
	                response.getLr().setListLibro(listLibros);
	            	
				}
	                response.setHashMap("Respuesta ok", "00", "Respuesta exitosa");
	            } else {
	                response.setHashMap("Respuesta negativa", "404", "Respuesta sin exito");
	                
	            }
	    } catch (Exception e) {
	        response.setHashMap("Respuesta negativa", "500", "Respuesta sin éxito");
	        log.error("Error en actualizar un libro", e);
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> deleteLibro(Long id) {
		 log.info("Inicio de Busqueda para eliminar libros por id");
	        LibroResponseRest response = new LibroResponseRest();  
	    try {
	            libroDAO.deleteById(id);
	            response.setHashMap("Respuesta ok", "00", "Respuesta exitosa");
	            
	    } catch (Exception e) {
	        response.setHashMap("Respuesta negativa", "500", "Respuesta sin éxito");
	        log.error("Error en al eliminar un libro", e);
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
}

