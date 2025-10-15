package com.example.ead_financas.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.ead_financas.model.entity.Curso;
import com.example.ead_financas.model.entity.Matricula;
import com.example.ead_financas.model.enums.Perfil;

import jakarta.validation.constraints.*;

public class UsuarioDTO {
	
	@NotBlank(message = "É obrigatório preencher o nome do usuário.")		
	@Size(min = 6, max = 100, message = "deve ter no mínimo 5 caracteres e no máximo 100 caracteres")
	@Pattern(regexp =  "^(?!.* {3,}).+$", message = "Não pode conter três ou mais espaços seguidos")
	private String nome;
	
	@NotBlank(message = "É obrigatório digitar uma senha.")		
	@Size(min = 6, message = "Mínimo de 6 caracteres")
	@Pattern(regexp =  "^(?!.*\\\\s).{8,30}$", message = "Não pode conter espaços em branco.")
	private String senha;
	
	@NotBlank(message = "É obrigatório preencher o email.")	
	@Email(message = "O formato do e-mail é inválido. Formato esperado: nome@dominio.com")
	private String email;
	
	@NotBlank(message = "Obrigatório designar perfil.")	
	@Pattern(regexp = "^(ALUNO|PROFESSOR)$",
	        message = "O perfil deve ser 'ALUNO' ou 'PROFESSOR'.")
	private Perfil perfil;
	
	
	private List<Curso> cursosCriados = new ArrayList<>();
	
	private List<Matricula> matriculas = new ArrayList<>();

	
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

	public String getEmail() {
		return email;
	}
	

	public void setEmail(String email) {
		this.email = email;
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
	
	
	
}
