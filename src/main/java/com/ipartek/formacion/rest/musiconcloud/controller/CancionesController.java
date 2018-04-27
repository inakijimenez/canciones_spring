package com.ipartek.formacion.rest.musiconcloud.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.formacion.rest.musiconcloud.domain.Cancion;
import com.ipartek.formacion.rest.musiconcloud.domain.ResponseMessage;
import com.ipartek.formacion.rest.musiconcloud.model.CancionesRepository;

@RestController
@CrossOrigin
public class CancionesController {

	private ResponseEntity<Object> result = null;

	@Autowired
	CancionesRepository cancionesRepository;
	
	@RequestMapping(value = {"/cancion/", "/cancion"}, method = RequestMethod.GET)
	public ResponseEntity<Object> buscarNombre(@RequestParam(required = false) String nombre) {

		result = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		List<Cancion> lista = new ArrayList<Cancion>();
		try {
			if (null == nombre || "".equals(nombre)) {
				lista = (List<Cancion>) cancionesRepository.findAll();
				result = new ResponseEntity<Object>(lista, HttpStatus.OK);
			} else {
				lista = (List<Cancion>) cancionesRepository.findByNombreContaining(nombre);
				if (lista.size() == 0) {
					result = new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
				} else {
					result = new ResponseEntity<Object>(lista, HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	@RequestMapping(value = "/cancion/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> detalle(@PathVariable int id) {

		result = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			Optional<Cancion> cancion = cancionesRepository.findById(id);
			if (!cancion.isPresent()) {
				result = new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
			} else {
				result = new ResponseEntity<Object>(cancion, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	@RequestMapping(value = "/cancion/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> eliminar(@PathVariable int id) {

		try {
			cancionesRepository.deleteById(id);
			result = new ResponseEntity<Object>(HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			result = new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	@RequestMapping(value = { "/cancion/", "/cancion" }, method = RequestMethod.POST)
	public ResponseEntity<Object> insertar(@RequestBody Cancion cancion) {

		try {
			result = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
			cancionesRepository.save(cancion);
			result = new ResponseEntity<Object>(cancion, HttpStatus.CREATED);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			result = new ResponseEntity<Object>(new ResponseMessage("Existe el nombre de la Cancion"),
					HttpStatus.CONFLICT);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	@RequestMapping(value = { "/cancion/{id}" }, method = RequestMethod.PUT)
	public ResponseEntity<Object> update(@RequestBody Cancion cancion, @PathVariable int id) {

		try {
			result = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
			cancion.setId(id);
			cancionesRepository.save(cancion);
			result = new ResponseEntity<Object>(cancion, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

}
