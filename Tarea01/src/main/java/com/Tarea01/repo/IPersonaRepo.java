package com.Tarea01.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tarea01.Modelo.Persona;

//@Repository
public interface IPersonaRepo extends JpaRepository<Persona, Integer>{

}
