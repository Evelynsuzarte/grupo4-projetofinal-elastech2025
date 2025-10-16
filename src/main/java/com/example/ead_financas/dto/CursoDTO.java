package com.example.ead_financas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CursoDTO {

	@NotBlank(message = "Obrigatório o titulo")		
	@Size(min = 10, max = 100, message = "deve ter no mínimo 10 caracteres e no máximo 100 caracteres")
	@Pattern(regexp =  "^(?!.* {3,}).+$", message = "Não pode conter três ou mais espaços seguidos")
	private String titulo;
	
	@NotBlank(message = "Obrigatório descrição")		
	@Size(min = 100, max = 10000, message = "deve ter no mínimo 100 caracteres e no máximo 10000 caracteres")
	@Pattern(regexp =  "^(?!.* {3,}).+$", message = "Não pode conter três ou mais espaços seguidos")
	private String descricao;
	
	@NotBlank(message = "Obrigatório imagem")	
	@Pattern(regexp = "^(https?://).+", message = "URL deve começar com http:// ou https://")
	private String caminhoImagem;

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
	
	
}
