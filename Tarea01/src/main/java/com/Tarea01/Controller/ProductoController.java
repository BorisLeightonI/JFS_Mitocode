package com.Tarea01.Controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

import com.Tarea01.Modelo.Producto;
import com.Tarea01.exception.ModeloNotFoundEXception;
import com.Tarea01.service.IProductoService;

/**
 * @author Boris
 *
 */
@RestController
@RequestMapping("/productos")
public class ProductoController {
	
	@Autowired
	private IProductoService service;

	@GetMapping
	public ResponseEntity<List<Producto>> listar(){
		List<Producto> lista = service.listar();
		return new ResponseEntity<List<Producto>>(lista, HttpStatus.OK); /*200*/
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Producto> listarPorID(@PathVariable("id") Integer id){
		Producto p = service.listarPorId(id);
		if(p.getIdProducto()==null) {
			throw new ModeloNotFoundEXception("ID NO ENCONTRADO: "+ id);
		}
		return new ResponseEntity<Producto>(p, HttpStatus.OK);
	}
	//hateoas
	@GetMapping("/hateoas/{id}")
	public EntityModel<Producto> listarPorIdHateoas(@PathVariable Integer id){
		Producto pac = service.listarPorId(id);
		EntityModel<Producto> recurso = new EntityModel<Producto>(pac);
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).listarPorID(id));
		recurso.add(linkTo.withRel("paciente-recurso"));
		return recurso;
	}
	
	@PostMapping
	public ResponseEntity<Producto> registrar(@Valid @RequestBody Producto persona){
		Producto p = service.registrar(persona);
		return new ResponseEntity<Producto>(p,HttpStatus.CREATED); /*201*/
	}

	@PutMapping /*LEVEL 2 API REST: Retornar uri en el header*/
	public ResponseEntity<Producto> modificar(@Valid @RequestBody Producto paciente){
	Producto p = service.registrar(paciente);
	//return new ResponseEntity<Producto>(p,HttpStatus.CREATED); /*201*/
	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getIdProducto()).toUri();
	return ResponseEntity.created(location).build();
}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id){
		Producto p = service.listarPorId(id);
		if(p.getIdProducto()==null) {
			throw new ModeloNotFoundEXception("ID NO ENCONTRADO"+ id);
		}
		service.eliminar(id);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		/*No se requiere retornar nada*/
	}
	
	
}
