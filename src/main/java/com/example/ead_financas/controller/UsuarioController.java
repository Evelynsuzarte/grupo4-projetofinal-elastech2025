package com.example.ead_financas.controller;

import com.example.ead_financas.service.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.ead_financas.dto.UsuarioDTO;
import com.example.ead_financas.model.entity.Usuario;
import com.example.ead_financas.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/")
	public List<Usuario> listar_usuarios() {
		return usuarioService.listarUsuarios();
	}
	
	@GetMapping("/{id}")
	public Optional<Usuario> buscar_id(@PathVariable("id") Long id) {
		return usuarioService.buscarPorId(id);
	}

	
	@PostMapping("/adicionar")
	public Usuario criar_usuario(@Valid @RequestBody UsuarioDTO novo_usuarioDTO) {
		Usuario usuario = new Usuario();
		
		usuario.setNome(novo_usuarioDTO.getNome());
		usuario.setSenha(novo_usuarioDTO.getSenha());
		usuario.setPerfil(novo_usuarioDTO.getPerfil());
		
		usuarioService.criar(usuario);
		
		return usuario;
	}
	
	@PutMapping("/editar/{id}")
	public Usuario editarUsuario(@PathVariable("id") Long id, @RequestBody Usuario novosDados) {
	    Usuario usuarioAtualizado = usuarioService.editarUsuario(id, novosDados);
	    return usuarioAtualizado;
	}

	
	@DeleteMapping("/apagar/{id}")
	public void deletar(@PathVariable("id") Long id) {
		usuarioService.deletarUsuario(id);
	}

}
