package com.example.ead_financas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ead_financas.model.entity.*;
import com.example.ead_financas.model.enums.Perfil;
import com.example.ead_financas.model.repository.*;
import jakarta.transaction.Transactional;
import java.util.*;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository repo;
	
	@Transactional
	public Usuario criar(Usuario usuario) {
	    if (usuario.getPerfil() == Perfil.ALUNO) {
	        if (usuario.getMatriculas() == null) {
	            usuario.setMatriculas(new ArrayList<>());
	        }
	    } else if (usuario.getPerfil() == Perfil.PROFESSOR) {
	        if (usuario.getCursosCriados() == null) {
	            usuario.setCursosCriados(new ArrayList<>());
	        }
	    }

	    return repo.save(usuario);
	}
	
	public Optional<Usuario> buscarPorId(Long id){
		return repo.findById(id);
	}
	
    public Usuario editarUsuario(Long id, Usuario novosDados) {
    	
    	Optional<Usuario> usuarioOp = repo.findById(id);
    	
    	if (usuarioOp.isPresent()) {
    		Usuario usuarioExistente = usuarioOp.get();
    		
	        	if (novosDados.getNome() != null) {
	        		usuarioExistente.setNome(novosDados.getNome());
	        	}
	        	if (novosDados.getEmail() != null) {
	        		usuarioExistente.setEmail(novosDados.getEmail());
	        	}
	        	if (novosDados.getSenha() != null) {
	        		usuarioExistente.setSenha(novosDados.getSenha());
	        	}
	        	if (novosDados.getPerfil() == Perfil.ALUNO && novosDados.getMatriculas() != null) {
	        		usuarioExistente.setMatriculas(novosDados.getMatriculas());
	        	}
	        	if (novosDados.getPerfil() == Perfil.PROFESSOR && novosDados.getCursosCriados() != null) {
	        		usuarioExistente.setCursosCriados(novosDados.getCursosCriados());
	        	}
	        	
	        	return repo.save(usuarioExistente);
    	}
    	return null;
    }

    public Optional<Usuario> autenticar(String email, String senha) {
        return repo.findByEmailAndSenha(email, senha);
    }

	
	public List<Usuario> listarUsuarios(){
		return repo.findAll();
	}
	
	@Transactional
	public void deletarUsuario(Long id) {
		repo.deleteById(id);
	}
}
