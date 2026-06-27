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

import com.company.books.backend.model.Categoria;
import com.company.books.backend.model.dao.ICategoriaDAO;
import com.company.books.backend.response.CategoriaResponseRest;


@Service
public class CategoriaServiceIMPL implements ICategoriaService {
	
	private static final Logger log = LoggerFactory.getLogger(CategoriaServiceIMPL.class);
	
	
	private final ICategoriaDAO categoriaDAO;

    public CategoriaServiceIMPL(ICategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }
	
	@Override
	@Transactional(readOnly = true)
	
	public ResponseEntity<CategoriaResponseRest> searchCategoria() {
		log.info("inicio metodo de buscar searchCategoria");
		
		CategoriaResponseRest responde = new CategoriaResponseRest();
		try {
			
			List<Categoria> listCategoria = (List<Categoria>)categoriaDAO.findAll();
			
			responde.getCr().setListCategoria(listCategoria);
			
			responde.setHashMap("Respuesta ok", "00", "Respusta exitosa");
		} catch (Exception e) {
			
			responde.setHashMap("Resputa negativa", "404", "Repuesta sin exito");
			log.error("Error en obtener las listas de categoria", e.getMessage());
			e.getStackTrace();
			return new ResponseEntity<CategoriaResponseRest>(responde,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CategoriaResponseRest>(responde, HttpStatus.OK); //Tiene que devolver 200
	}

	@Override
	@Transactional(readOnly=true)
	public ResponseEntity<CategoriaResponseRest> searchCategoriaId(Long id) {
		//Creamos el regristro para el usuario como al programador
		log.info("Se inicio la busqueda de la categoria por id");
		CategoriaResponseRest response = new CategoriaResponseRest();
		
		try {
			
			List<Categoria> listaCategoria = new ArrayList<>();
			Optional<Categoria> categoria  = categoriaDAO.findById(id);
			
			if (categoria.isPresent()) {
				listaCategoria.add(categoria.get());
				response.getCr().setListCategoria(listaCategoria);
				
			}else {
			
				log.error("Hubo un error en buscar la categoria por id");
			response.setHashMap("respuesta negada", "404", "No se encontro la categoria");
			return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.NOT_FOUND);
				
			}
		} catch (Exception e) {
			//Informacion del proceso
			response.setHashMap("Resputa negativa", "404", "Repuesta sin exito");
			log.error("Error en obtener las listas de categoria", e.getMessage());
			e.getStackTrace();
			return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.setHashMap("Respuesta ok", "00", "Respusta exitosa");
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);//Siempre devuelve 200
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> create(Categoria request) {
		log.info("Inicio del metodo de creacion de categoria");
		CategoriaResponseRest response = new CategoriaResponseRest();
		
		List<Categoria> listaCategoria = new ArrayList<>();
		try {
			
			Categoria categoriaGuardada =  categoriaDAO.save(request);
			if (categoriaGuardada != null) {
				listaCategoria.add(categoriaGuardada);
				response.getCr().setListCategoria(listaCategoria);
			}
			else {
				
				log.error("Hubo un error en crear la categoria");
			response.setHashMap("respuesta negada", "404", "No se creo correctamente");
			return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			response.setHashMap("Resputa negativa", "404", "Repuesta sin exito");
			log.error("Error al crear las catorias ", e.getMessage());
			e.getStackTrace();
			return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.NO_CONTENT);
		}
		response.setHashMap("Respuesta ok", "00", "Respusta exitosa");
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);//Siempre devuelve 200
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> update(Categoria requestModificacion, Long id) {
		
		log.info("Se inicio la busqueda de la categoria por id para modificar");
		
		
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> listaCategoria = new ArrayList<>();
		
		try {
			
			Optional<Categoria> categoriaUpdate = categoriaDAO.findById(id);
			
			if (categoriaUpdate.isPresent()) {
				categoriaUpdate.get().setName(requestModificacion.getName());
				categoriaUpdate.get().setDescripcion(requestModificacion.getDescripcion());
				
				
				Categoria categoriaSaved = categoriaDAO.save(categoriaUpdate.get());
				if (categoriaSaved != null) {
					listaCategoria.add(categoriaSaved);
					response.getCr().setListCategoria(listaCategoria);
				}
			}
		else {
				
				log.error("Hubo un error en actualizar la categoria");
			response.setHashMap("respuesta negada", "404", "No se actualizo correctamente");
			return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			response.setHashMap("Resputa negativa", "404", "Repuesta sin exito");
			log.error("Error al actualizar las categorias ", e.getMessage());
			e.getStackTrace();
			return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.NO_CONTENT);
		}
		response.setHashMap("Respuesta ok", "00", "Respusta exitosa");
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);//Siempre devuelve 200
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> delete(Long id) {
		log.info("Se inicio la operacion para eliminar la categoria ");
		CategoriaResponseRest response = new CategoriaResponseRest();
		try {
			
			//Forma de eliminar un dato por id
			categoriaDAO.deleteById(id);
			
			
		} catch (Exception e) {
			response.setHashMap("Resputa negativa", "404", "Repuesta sin exito");
			log.error("Error al eliminar las categorias ", e.getMessage());
			e.getStackTrace();
			return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.NO_CONTENT);
		}
		response.setHashMap("Respuesta ok", "00", "Respusta exitosa");
		return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.OK);
	}

}
