package com.imaginary.poc.search.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.imaginary.poc.modelo.Livro;

public interface LivroSearchRepository  extends   ElasticsearchRepository<Livro,Integer> {

	List<Livro> findByConteudo(String string);
	
	Page<Livro> findByConteudo(String string ,Pageable pageable);
	

}
