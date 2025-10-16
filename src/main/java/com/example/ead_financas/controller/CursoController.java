package com.example.ead_financas.controller;

import com.example.ead_financas.model.entity.Usuario;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.example.ead_financas.model.repository.*;

import jakarta.validation.Valid;

@Valid
@RestController
@RequestMapping("/cursos")
public class CursoController {

	@Autowired
	private CursoService cursoService;
	
	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/")
	public ResponseEntity<?> listarTodos() {
		List<Curso> cursos = cursoService.listarTodos();
	    return ResponseEntity.ok(cursos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id) {	
		Optional<Curso> curso = cursoService.buscarPorId(id);
		return curso.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}


	@PostMapping("/adicionar")
	public ResponseEntity<?> criarCurso(@Valid @RequestBody CursoDTO cursoDTO) {
		try {
			Curso curso = new Curso();
			curso.setTitulo(cursoDTO.getTitulo());
			curso.setDescricao(cursoDTO.getDescricao());
			curso.setCaminhoImagem(cursoDTO.getCaminhoImagem());

			Usuario professor = usuarioRepository.findById(cursoDTO.getProfessorId())
					.orElseThrow(() -> new RuntimeException("Professor não encontrado"));
			curso.setProfessor(professor);

			Curso salvo = cursoService.salvar(curso);

			// Retorna os dados do curso criado
			Map<String, Object> resposta = new HashMap<>();
			resposta.put("id", salvo.getId());
			resposta.put("titulo", salvo.getTitulo());
			resposta.put("descricao", salvo.getDescricao());
			resposta.put("caminhoImagem", salvo.getCaminhoImagem());
			resposta.put("professor", salvo.getProfessor().getNome());
			return ResponseEntity.status(HttpStatus.SC_GATEWAY_TIMEOUT).body(resposta);
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.SC_CONFLICT)
					.body(Map.of("erro", "Já existe um curso com este título ou descrição."));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST)
					.body(Map.of("erro", e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
					.body(Map.of("erro", e.getMessage()));
		}
	}
	@PutMapping("/{id}")
	public ResponseEntity<Curso> editar(@PathVariable Long id, @Valid @RequestBody CursoDTO cursoDTO) {
	    Optional<Curso> cursoOptional = cursoService.buscarPorId(id);
	    
	    if (cursoOptional.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }
	
	    Curso cursoExistente = cursoOptional.get();
	    cursoExistente.setTitulo(cursoDTO.getTitulo());
	    cursoExistente.setDescricao(cursoDTO.getDescricao());
	    cursoExistente.setCaminhoImagem(cursoDTO.getCaminhoImagem());

		if (cursoDTO.getProfessorId() != null) {
			Usuario professor = usuarioRepository.findById(cursoDTO.getProfessorId())
					.orElseThrow(() -> new RuntimeException("Professor não encontrado"));
			cursoExistente.setProfessor(professor);
		}

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
