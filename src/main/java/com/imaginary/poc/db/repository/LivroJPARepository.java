package com.imaginary.poc.db.repository;


import org.springframework.data.repository.CrudRepository;

import com.imaginary.poc.modelo.Livro;

public interface LivroJPARepository extends   CrudRepository<Livro,Integer> {

}
