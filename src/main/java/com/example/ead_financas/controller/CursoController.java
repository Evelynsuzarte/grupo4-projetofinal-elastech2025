package com.example.ead_financas.controller;

import java.util.List;
import java.util.Optional;

import org.apache.http.HttpStatus;
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
import com.example.ead_financas.model.repository.CursoRepository;
import com.example.ead_financas.service.CursoService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/cursos")
public class CursoController {

	@Autowired
	private CursoService cursoService;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@GetMapping("/")
	public ResponseEntity<?> listarTodos() {
		List<Curso> cursos = cursoService.listarTodos();
	    return ResponseEntity.ok(cursos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id) {	
		Optional<Curso> curso = cursoService.buscarPorId(id);
		return ResponseEntity.ok(new CursoDTO());
	}
	
	
	@PostMapping("/adicionar")
	public ResponseEntity<?> criarCurso(@Valid @RequestBody CursoDTO cursoDTO) {
		try {		
			Curso curso = new Curso();		
			curso.setTitulo(cursoDTO.getTitulo());
			curso.setDescricao(cursoDTO.getDescricao());
			curso.setCaminhoImagem(cursoDTO.getCaminhoImagem());
			
			Curso salvo = cursoService.salvar(curso); 
			
			CursoDTO respostaDTO = new CursoDTO();
	        salvo.getId();
	        salvo.getTitulo();
	        salvo.getDescricao();
	        salvo.getCaminhoImagem();	        
			return ResponseEntity.status(HttpStatus.SC_CREATED).body(respostaDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
	    }
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
	
	@GetMapping("/professor/{idProfessor}")
	public ResponseEntity<?> listarPorProfessor(@PathVariable Long idProfessor) {
		List<Curso> cursos = cursoRepository.findByProfessor_Id(idProfessor);
		if(cursos == null || cursos.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cursos);
	}
	
	@GetMapping("/{id}/alunos")
	public ResponseEntity<?> listarPorAluno(@PathVariable Long idAluno) {
	    List<Curso> cursos = cursoRepository.findByMatriculas_Aluno_Id(idAluno);
	    if (cursos == null || cursos.isEmpty()) {
	        return ResponseEntity.noContent().build();
	    }
	    return ResponseEntity.ok(cursos);
	
	}
}
