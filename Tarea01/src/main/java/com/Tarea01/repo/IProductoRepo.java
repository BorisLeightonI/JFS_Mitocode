package com.Tarea01.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tarea01.Modelo.Producto;

//@Repository
public interface IProductoRepo extends JpaRepository<Producto, Integer>{

}
