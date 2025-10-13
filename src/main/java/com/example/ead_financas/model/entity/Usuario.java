package com.example.ead_financas.model.entity;

import com.example.ead_financas.model.enums.Perfil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(unique = true)
  private String nome;

  private String senha;
  @Enumerated(EnumType.STRING)
  private Perfil perfil;


  @OneToMany(mappedBy = "professor")
  private List<Curso> cursosCriados = new ArrayList<>();


  @OneToMany(mappedBy = "aluno")
  private List<Matricula> matriculas = new ArrayList<>();


}
