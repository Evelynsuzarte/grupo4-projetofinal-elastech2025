package com.example.ead_financas.dto;

import jakarta.validation.constraints.*;


public class LoginDTO {
	
	@NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O formato do e-mail é inválido. Formato esperado: nome@dominio.com")
    private String email;

	@NotBlank(message = "É obrigatório digitar uma senha.")		
	@Size(min = 6, message = "Mínimo de 6 caracteres")
	@Pattern(regexp =  "^(?!.*\\\\s).{8,30}$", message = "Não pode conter espaços em branco.")
    private String senha;

    // Getters e Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

