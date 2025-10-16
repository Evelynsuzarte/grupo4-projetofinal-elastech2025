package com.example.ead_financas.model.repository;

import com.example.ead_financas.model.entity.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {
	Optional<Usuario> findByEmailAndSenha(String email, String senha);

}
