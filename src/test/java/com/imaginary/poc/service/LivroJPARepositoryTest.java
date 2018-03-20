package com.imaginary.poc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsMapper;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.xml.sax.SAXException;

import com.imaginary.poc.config.ApplicationConfiguration;
import com.imaginary.poc.db.repository.AutorJPARepository;
import com.imaginary.poc.db.repository.LivroJPARepository;
import com.imaginary.poc.modelo.Autor;
import com.imaginary.poc.modelo.Livro;
import com.imaginary.poc.parser.TikaUtil;
import com.imaginary.poc.search.repository.LivroSearchRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { ApplicationConfiguration.class }, loader = AnnotationConfigContextLoader.class)
public class LivroJPARepositoryTest {

	@Autowired
	public LivroJPARepository livroJPARepository;

	@Autowired
	public AutorJPARepository autorJPARepository;

	@Autowired
	public LivroSearchRepository livroSearchRepository;

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Before
	public void before() {
		
		elasticsearchTemplate.deleteIndex(Livro.class);
		elasticsearchTemplate.createIndex(Livro.class);
		elasticsearchTemplate.putMapping(Livro.class);
		elasticsearchTemplate.refresh(Livro.class);

	}

	@Test
	public void test() {

		List<Livro> livrosList = new ArrayList<Livro>();
		Livro livro = new Livro();
		livro.setDtPublicacao(Livro.convertStringToTimestamp("20/02/2010"));
		livro.setEditora("Teste editora alterardo");
		livro.setTitulo("Java Designer Patterns");
		livro.setPathDigital("C:\\Temp\\livros\\DesignPatternscomJava.pdf");
		
		Autor autor = new Autor();
		autor.setNome("teste");
		livro.getAutors().add(autor);

		livroJPARepository.save(livro);
		livrosList.add(livro);
		
		livro = new Livro();
		livro.setDtPublicacao(Livro.convertStringToTimestamp("20/02/2010"));
		livro.setEditora("Teste editora alterardo");
		livro.setTitulo("MundoJ Segurança com Java - Casa do Codigo");
		livro.setPathDigital("C:\\Temp\\livros\\MundoJSegurancaJava.pdf");
		autor = new Autor();
		autor.setNome("Henrique Lobo");
		livro.getAutors().add(autor);		
		livroJPARepository.save(livro);
		livrosList.add(livro);
		
		
		
		livro = new Livro();
		livro.setDtPublicacao(Livro.convertStringToTimestamp("20/02/2010"));
		livro.setEditora("Teste editora alterardo");
		livro.setTitulo("Test Driven Development - Teste e Design no Mundo Real com PHP - Casa do Codigo");
		livro.setPathDigital("C:\\Temp\\livros\\Test Driven Development - Teste e Design no Mundo Real com PHP - Casa do Codigo.pdf");
		autor = new Autor();
		autor.setNome("Andre Cardoso");
		livro.getAutors().add(autor);	
		autor = new Autor();
		autor.setNome("Mauricio Aniche");
		livro.getAutors().add(autor);		
		livroJPARepository.save(livro);
		livrosList.add(livro);
		
		
		livro = new Livro();
		livro.setDtPublicacao(Livro.convertStringToTimestamp("20/02/2010"));
		livro.setEditora("Casa do Codigo");
		livro.setTitulo("Vire o jogo com spring framework");
		livro.setPathDigital("C:\\Temp\\livros\\Virecomspringframework.pdf");
		autor = new Autor();
		autor.setNome("Henrique Lobo");
		livro.getAutors().add(autor);
		
		livroJPARepository.save(livro);
		livrosList.add(livro);
		
		
		
		

		// Optional<Livro> livroNovo = livroJPARepository.findById(Integer.valueOf(28));
		// livroNovo.get().getAutors();
		// System.out.println(livroNovo.get());

		

		String conteudo;
		try {
			for (Livro livroSearch : livrosList) {
				
				System.out.println("Titulo" + livroSearch.getTitulo());
				
				conteudo = new String(); 
				TikaUtil tikaUtil = new TikaUtil();
				
				 
				conteudo = new String(tikaUtil.parser(livroSearch.getPathDigital()));
				if (conteudo==null) throw new IllegalStateException("Condeudo nulo"+ livroSearch.getPathDigital());
				//System.out.println(conteudo);			
				livroSearch.setConteudo(conteudo);
				livroSearchRepository.save(livroSearch);
				
			}
			
		} catch (IOException | SAXException | TikaException e) {
			 throw new IllegalAccessError();
		}

		
		
		List<Livro> livrosListSearch = new ArrayList<Livro>();
		Page<Livro>  page = livroSearchRepository.findByConteudo("teste",  PageRequest.of(0, 2));
		System.out.println(page.getNumberOfElements());
		System.out.println(page.getTotalPages());
		
		System.out.println(page.getTotalPages());
		Page<Livro>  page2 = livroSearchRepository.findByConteudo("teste", PageRequest.of(1, 2));
		System.out.println(page2.getNumberOfElements());
		System.out.println(page2.getTotalPages());
		
		
		
		
		

		
		/*livroSearchRepository.search(query, pageable)
		
		for (Livro livro2 : livrosListSearch) {
			
			
			System.out.println("Livro:" + livro2.getTitulo());
			
		}

		// QueryBuilder query = QueryBuilder new QueryBuilderFactory().
		// livroSearchRepository.search(query);

		// assertThat("200").isEqualTo(200);*/

	}

	/*
	 * private static XContentBuilder buildDocument(Livro document) throws Exception
	 * { XContentBuilder builder = jsonBuilder(); builder.startObject();
	 * builder.field("id", document.getId());
	 * 
	 * builder.field("fileName", document.getPathDigital());
	 * 
	 * builder.endArray(); builder.field("content", document.getContent());
	 * builder.field("file", document.getFile()); builder.endObject(); return
	 * builder; }
	 */

}
