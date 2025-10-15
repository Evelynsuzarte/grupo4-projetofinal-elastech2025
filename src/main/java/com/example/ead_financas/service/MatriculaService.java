package com.example.ead_financas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ead_financas.model.entity.Curso;
import com.example.ead_financas.model.entity.Matricula;
import com.example.ead_financas.model.entity.Usuario;
import com.example.ead_financas.model.repository.MatriculaRepository;

import jakarta.transaction.Transactional;

@Service
public class MatriculaService {

	@Autowired
	private MatriculaRepository repository;

	@Transactional
	public Matricula adicionar(Matricula matricula) {
		return repository.save(matricula);
	}
	
	public List<Matricula> findByAluno(Usuario aluno) {
    return repository.findByAluno(aluno);
    }

	public List<Matricula> findByCurso(Curso curso) {
		return repository.findByCurso(curso);
	}

	public List<Matricula> listarTodos() {
		return repository.findAll();
	}

	
//	Provalmente é pra retornar o ID da matrícula que gera um novo a cada inscrição
//	TIRAR DÚVIDA COM PROFESSOR
//	public Matricula atualizarMatricula(Long id, Matricula matriculaAtualizada) {
//		Matricula matricula = repository.findById(id).orElseThrow(() -> new RuntimeException("Matricula inexistente."));
//
//		matricula.setId(matriculaAtualizada.getId());
//		matricula.setCurso(matriculaAtualizada.getCurso());
//
//		return repository.save(matricula);
//	}

	
	
	public Matricula atualizarID(Long id, Matricula matriculaAtualizada) {
		Matricula matricula = repository.findById(id).orElseThrow(() -> new RuntimeException("Matricula inexistente."));
	
		matricula.setId(matriculaAtualizada.getId());
		matricula.setCurso(matriculaAtualizada.getCurso());
	
		return repository.save(matricula);
	}
	
	public void deletar(Long id) {
		repository.deleteById(id);
	}
}
