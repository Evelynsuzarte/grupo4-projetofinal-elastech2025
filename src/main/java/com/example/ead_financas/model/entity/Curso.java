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


  // essa parte aqui ainda


  @ManyToOne
  @JoinColumn(name = "professor_id")
  private Usuario professor;

  @OneToMany(mappedBy = "curso")
  private List<Matricula> matriculas = new ArrayList<>();
    
  
  
  public Long getId() {
	  return id;
  }
  
  
  public String getTitulo() {
	  return titulo;
  }
  
  public void setTitulo(String titulo) {
	  this.titulo = titulo;
  }
}
