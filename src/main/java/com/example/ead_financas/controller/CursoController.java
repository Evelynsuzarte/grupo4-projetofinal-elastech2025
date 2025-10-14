package com.example.ead_financas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ead_financas.dto.CursoDTO;
import com.example.ead_financas.model.entity.Curso;
import com.example.ead_financas.repository.CursoRepository;
import com.example.ead_financas.service.CursoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cursos")
public class CursoController {
	
	@Autowired
	private CursoService cursoService;
	
	@Autowired
	private CursoRepository repo;
	
	@GetMapping()
	public List<Curso> listarTodos() {
		return cursoService.listarTodos();
	}
	
	@GetMapping("/{id}")
	public Optional<Curso> buscarPorId(@PathVariable("id") Long id) {
		return cursoService.buscarPorId(id);
	}
	
	@PostMapping("/adicionar")
	public Curso salvar(@Valid @RequestBody CursoDTO cursoDTO) {
		Curso curso = new Curso();
		curso.setTitulo(cursoDTO.getTitulo());
		curso.setDescricao(cursoDTO.getDescricao());
		curso.setCaminhoImagem(cursoDTO.getCaminhoImagem());
		cursoService.salvar(curso);
		return curso;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Curso> editar(@PathVariable Long id, @Valid @RequestBody Curso cursoAtualizado) {
	    Optional<Curso> cursoOptional = cursoService.buscarPorId(id);
	    
	    if (cursoOptional.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }

	    Curso cursoExistente = cursoOptional.get();
	    cursoExistente.setTitulo(cursoAtualizado.getTitulo());
	    cursoExistente.setDescricao(cursoAtualizado.getDescricao());
	    cursoExistente.setCaminhoImagem(cursoAtualizado.getCaminhoImagem());
	    
	    Curso cursoEditado = cursoService.salvar(cursoExistente);
	    return ResponseEntity.ok(cursoEditado);
	}
	
	@GetMapping("/professor/{id}")
	public List<Curso> listarPorProfessor(Long idProfessor) {
	    return repo.findByProfessorId(idProfessor);
	}
	
	@GetMapping("/{id}/alunos")
	public List<Curso> listarPorAlunos(Long idAluno) {
		return repo.findByAlunoId(idAluno);
	}

}
