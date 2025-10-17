package com.example.ead_financas.dto;

import jakarta.validation.constraints.NotNull;

public class MatriculaDTO {

    private Long id;

    @NotNull(message = "O ID do aluno é obrigatório.")
    private Long alunoId;

    @NotNull(message = "O ID do curso é obrigatório.")
    private Long cursoId;

    private String numeroMatricula;

    private String nomeProfessor;

    private String tituloCurso;

    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setnumeroMatricula(String numeroMatricula){
        this.numeroMatricula = numeroMatricula;

    }


    public Long getId() {
        return id;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public String getTituloCurso() {
        return tituloCurso;
    }

    public void setTituloCurso(String tituloCurso) {
        this.tituloCurso = tituloCurso;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    public void setId(Long id) {
        this.id = id;
    }
}