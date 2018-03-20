package com.imaginary.poc.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;
@Entity
@Document(indexName = "livros", type = "livro")
public class Livro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "DT_PUBLICACAO")
	private Timestamp dtPublicacao;

	private String editora;

	//Field(pattern="ingest-attachment", type=FieldType.Attachment) //
	@Column(name = "PATH_DIGITAL")
	private String pathDigital;

	private String titulo;

	private BigDecimal valor;
	
	

	@Transient
	@Field(type = FieldType.text, index=true, store = false)
	private String conteudo; 



	@ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "livro_rel_autor",
            joinColumns = { @JoinColumn(name = "LIVRO_ID") },
            inverseJoinColumns = { @JoinColumn(name = "AUTOR_ID") })
	private List<Autor> autors=new ArrayList<Autor>();
    
    
    

	public Livro() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getDtPublicacao() {
		return this.dtPublicacao;
	}

	public void setDtPublicacao(Timestamp dtPublicacao) {
		this.dtPublicacao = dtPublicacao;
	}

	public String getEditora() {
		return this.editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public String getPathDigital() {
		return this.pathDigital;
	}

	public void setPathDigital(String pathDigital) {
		this.pathDigital = pathDigital;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public List<Autor> getAutors() {
		return this.autors;
	}

	public void setAutors(List<Autor> autors) {
		this.autors = autors;
	}
	
    public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	

	@Override
	public String toString() {
		return "Livro [id=" + id + ", dtPublicacao=" + dtPublicacao + ", editora=" + editora + ", pathDigital="
				+ pathDigital + ", titulo=" + titulo + ", valor=" + valor + ", autors=" + autors + "]";
	}

	public static Timestamp convertStringToTimestamp(String str_date) {
		try {
			DateFormat formatter;
			formatter = new SimpleDateFormat("dd/MM/yyyy");
			// you can change format of date
			Date date = formatter.parse(str_date);
			java.sql.Timestamp timeStampDate =  new Timestamp(date.getTime());
			return timeStampDate;
		} catch (ParseException e) {
			throw new RuntimeException(e);

		}
	}

}