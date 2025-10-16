package com.example.ead_financas.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.*;




@Entity
@Table(name = "cursos")
public class Curso {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String titulo;


  @Lob
  @Column(columnDefinition = "TEXT", unique = true)
  private String descricao;


  private  String caminhoImagem;

  @ManyToOne
  @JoinColumn(name = "professor_id")
  @JsonBackReference(value = "professor-curso")
  private Usuario professor;

  @OneToMany(mappedBy = "curso")
  @JsonManagedReference(value = "curso-matricula")
  private List<Matricula> matriculas = new ArrayList<>();

  public Curso() {}

  public Curso(Long id, String titulo, String descricao, String caminhoImagem, Usuario professor) {
    this.id = id;
    this.titulo = titulo;
    this.descricao = descricao;
    this.caminhoImagem = caminhoImagem;
    this.professor = professor;
  }



  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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