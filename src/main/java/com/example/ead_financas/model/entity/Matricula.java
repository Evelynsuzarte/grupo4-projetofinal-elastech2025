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
  public Long Id;

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
}
