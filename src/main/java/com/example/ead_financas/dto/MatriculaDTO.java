package com.example.ead_financas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MatriculaDTO {

    private Long id;

    @NotBlank(message = "O número da matrícula é obrigatório.")
    @Size(min = 6, max = 12, message = "O número da matrícula deve ter entre 6 e 12 caracteres.")
    private String numeroMatricula;

    @NotNull(message = "O ID do aluno é obrigatório.")
    private Long alunoId;

    @NotNull(message = "O ID do curso é obrigatório.")
    private Long cursoId;

    public Long getId() {
        return id;
    }

    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(String numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
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
}
