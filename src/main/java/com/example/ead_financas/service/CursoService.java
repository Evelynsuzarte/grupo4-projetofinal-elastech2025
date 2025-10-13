package com.example.ead_financas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ead_financas.model.entity.Curso;
import com.example.ead_financas.repository.CursoRepository;

import jakarta.transaction.Transactional;

@Service
public class CursoService {
	@Autowired
	private CursoRepository repo;
	
	@Transactional
	public Curso criar(Curso curso) {
		return repo.save(curso);
	}
	
	public Curso editar(Long id, String novoTitulo) {
		Curso curso = repo.findById(id).orElse(null);
		if(curso != null) {
			curso.setTitulo(novoTitulo);
			return repo.save(curso);		
		}
		return null;		
	}
	public Optional<Curso> buscarPorId(Long id){
		return repo.findById(id);
	}
	
	public List<Curso> listarTodos(){
		return repo.findAll();
	}
	
	
	
	
	
}
