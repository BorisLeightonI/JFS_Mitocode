package com.Tarea01.Controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Tarea01.Modelo.Persona;
import com.Tarea01.exception.ModeloNotFoundEXception;
import com.Tarea01.service.IPersonaService;

/**
 * @author Boris
 *
 */
@RestController
@RequestMapping("/personas")
public class PersonaController {
	
	@Autowired
	private IPersonaService service;

	@GetMapping
	public ResponseEntity<List<Persona>> listar(){
		List<Persona> lista = service.listar();
		return new ResponseEntity<List<Persona>>(lista, HttpStatus.OK); /*200*/
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Persona> listarPorID(@PathVariable("id") Integer id){
		Persona p = service.listarPorId(id);
		if(p.getIdPersona()==null) {
			throw new ModeloNotFoundEXception("ID NO ENCONTRADO: "+ id);
		}
		return new ResponseEntity<Persona>(p, HttpStatus.OK);
	}
	//hateoas
	@GetMapping("/hateoas/{id}")
	public EntityModel<Persona> listarPorIdHateoas(@PathVariable Integer id){
		Persona pac = service.listarPorId(id);
		EntityModel<Persona> recurso = new EntityModel<Persona>(pac);
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorID(id));
		recurso.add(linkTo.withRel("paciente-recurso"));
		return recurso;
	}
	
	@PostMapping
	public ResponseEntity<Persona> registrar(@Valid @RequestBody Persona persona){
		Persona p = service.registrar(persona);
		return new ResponseEntity<Persona>(p,HttpStatus.CREATED); /*201*/
	}

	@PutMapping /*LEVEL 2 API REST: Retornar uri en el header*/
	public ResponseEntity<Persona> modificar(@Valid @RequestBody Persona paciente){
	Persona p = service.registrar(paciente);
	//return new ResponseEntity<Persona>(p,HttpStatus.CREATED); /*201*/
	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getIdPersona()).toUri();
	return ResponseEntity.created(location).build();
}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id){
		Persona p = service.listarPorId(id);
		if(p.getIdPersona()==null) {
			throw new ModeloNotFoundEXception("ID NO ENCONTRADO"+ id);
		}
		service.eliminar(id);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		/*No se requiere retornar nada*/
	}
	
	
}
