package com.imaginary.poc.db.repository;

import org.springframework.data.repository.CrudRepository;

import com.imaginary.poc.modelo.Autor;

public interface AutorJPARepository  extends   CrudRepository<Autor,Integer> {
	

}
