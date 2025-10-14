package com.example.ead_financas.model.entity;

import jakarta.persistence.*;
import java.util.*;


@Entity
@Table(name = "cursos")
public class Curso {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String titulo;

  @Lob
  @Column(columnDefinition = "TEXT")
  private String descricao;

  private  String caminhoImagem;

  @ManyToOne
  @JoinColumn(name = "professor_id")
  private Usuario professor;

  @OneToMany(mappedBy = "curso")
  private List<Matricula> matriculas = new ArrayList<>();
  
  public Curso() {}
  
  public Curso(Long id, String titulo, String descricao, String caminhoImagem, Usuario professor,
		List<Matricula> matriculas) {
	this.id = id;
	this.titulo = titulo;
	this.descricao = descricao;
	this.caminhoImagem = caminhoImagem;
	this.professor = professor;
	this.matriculas = matriculas;
}

  public Long getId() {
	  return id;
  }
  
  public String getTitulo() {
	  return titulo;
  }
  
  public void setTitulo(String titulo) {
	  this.titulo = titulo;
  }


  public String getDescricao() {
	return descricao;
  }


  public void setDescricao(String descricao) {
	this.descricao = descricao;
  }


  public String getCaminhoImagem() {
	return caminhoImagem;
  }


  public void setCaminhoImagem(String caminhoImagem) {
	this.caminhoImagem = caminhoImagem;
  }


  public Usuario getProfessor() {
	return professor;
  }


  public void setProfessor(Usuario professor) {
	this.professor = professor;
  }


  public List<Matricula> getMatriculas() {
	return matriculas;
  }


  public void setMatriculas(List<Matricula> matriculas) {
	this.matriculas = matriculas;
  }

  
  
}
