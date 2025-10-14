package com.example.ead_financas.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "matriculas")
public class Matricula {

  @Id
  @GeneratedValue
  public Long id;

  @ManyToOne
  @JoinColumn(name = "aluno_id")
  private Usuario aluno;

  @ManyToOne
  @JoinColumn(name = "curso_id")
  private Curso curso;

  private LocalDateTime dataMatricula;

  @PrePersist
  public void prePersist() {
    if (dataMatricula == null) dataMatricula = LocalDateTime.now();
  }



  public Matricula() {
  }

  public Matricula(Long id, Usuario aluno, Curso curso, LocalDateTime dataMatricula) {
    this.id = id;
    this.aluno = aluno;
    this.curso = curso;
    this.dataMatricula = dataMatricula;
  }



  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Usuario getAluno() {
    return aluno;
  }

  public void setAluno(Usuario aluno) {
    this.aluno = aluno;
  }

  public Curso getCurso() {
    return curso;
  }

  public void setCurso(Curso curso) {
    this.curso = curso;
  }

  public LocalDateTime getDataMatricula() {
    return dataMatricula;
  }

  public void setDataMatricula(LocalDateTime dataMatricula) {
    this.dataMatricula = dataMatricula;
  }
}
