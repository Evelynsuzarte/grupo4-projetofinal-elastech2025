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


  public Usuario() {
  }

  public Usuario(long id, String nome, String senha, Perfil perfil) {
    this.id = id;
    this.nome = nome;
    this.senha = senha;
    this.perfil = perfil;
  }



  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public Perfil getPerfil() {
    return perfil;
  }

  public void setPerfil(Perfil perfil) {
    this.perfil = perfil;
  }

  public List<Curso> getCursosCriados() {
    return cursosCriados;
  }

  public void setCursosCriados(List<Curso> cursosCriados) {
    this.cursosCriados = cursosCriados;
  }

  public List<Matricula> getMatriculas() {
    return matriculas;
  }

  public void setMatriculas(List<Matricula> matriculas) {
    this.matriculas = matriculas;
  }


  @OneToMany(mappedBy = "professor")
  private List<Curso> cursosCriados = new ArrayList<>();


  @OneToMany(mappedBy = "aluno")
  private List<Matricula> matriculas = new ArrayList<>();
}
