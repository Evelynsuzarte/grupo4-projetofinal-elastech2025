package com.example.ead_financas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> listar_usuarios() {
	    try {
	        List<Usuario> usuarios = usuarioService.listarUsuarios();
	        if (usuarios.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT)
	                                 .body("Nenhum usuário encontrado.");
	        }
	        return ResponseEntity.ok(usuarios);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Erro ao buscar usuários: " + e.getMessage());
	    }
	}

	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar_id(@PathVariable("id") Long id) {
	    try {
	        Optional<Usuario> usuarioOp = usuarioService.buscarPorId(id);
	        if (usuarioOp.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                                 .body("Usuário com ID " + id + " não encontrado.");
	        }

	        Usuario usuarioBuscado = usuarioOp.get();
	        return ResponseEntity.ok(usuarioBuscado);

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Erro ao buscar usuário: " + e.getMessage());
	    }
	}

	

	@PostMapping("/adicionar")
	public ResponseEntity<?> criar_usuario(@Valid @RequestBody UsuarioDTO novo_usuarioDTO) {
	    try {
	        Usuario usuario = new Usuario();
	        usuario.setNome(novo_usuarioDTO.getNome());
	        usuario.setSenha(novo_usuarioDTO.getSenha());
	        usuario.setEmail(novo_usuarioDTO.getEmail());
	        usuario.setPerfil(novo_usuarioDTO.getPerfil());

	        Usuario salvo = usuarioService.criar(usuario);

	        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                             .body("Erro ao criar usuário: " + e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Erro inesperado ao criar usuário.");
	    }
	}

	
	
	@PutMapping("/editar/{id}")
	public ResponseEntity<?> editarUsuario(@PathVariable("id") Long id, @RequestBody Usuario novosDados) {
	    try {
	        Usuario usuarioAtualizado = usuarioService.editarUsuario(id, novosDados);
	        if (usuarioAtualizado == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                                 .body("Usuário com ID " + id + " não encontrado.");
	        }
	        return ResponseEntity.ok(usuarioAtualizado);
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                             .body("Erro ao atualizar usuário: " + e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Erro inesperado ao atualizar usuário.");
	    }
	}


	
	@DeleteMapping("/apagar/{id}")
	public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
	    try {
	        Optional<Usuario> usuarioOp = usuarioService.buscarPorId(id);
	        if (usuarioOp.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                                 .body("Usuário com ID " + id + " não encontrado.");
	        }
	        usuarioService.deletarUsuario(id);
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 (sem corpo)

	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                             .body("Erro ao excluir usuário: " + e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Erro inesperado ao excluir usuário.");
	    }
	}


}
