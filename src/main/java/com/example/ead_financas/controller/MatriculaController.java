package com.example.ead_financas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ead_financas.dto.MatriculaDTO;
import com.example.ead_financas.model.entity.Curso;
import com.example.ead_financas.model.entity.Matricula;
import com.example.ead_financas.model.entity.Usuario;
import com.example.ead_financas.model.repository.*;
import com.example.ead_financas.service.MatriculaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("matriculas")
@Valid
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @Autowired
    private MatriculaRepository matriculaRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private CursoRepository cursoRepo;

    @PostMapping("/adicionar")
    public ResponseEntity<?> criar_matriculaEntity(@Valid @RequestBody MatriculaDTO dto) {
        try {
            Usuario aluno = usuarioRepo.findById(dto.getAlunoId())
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado."));
            Curso curso = cursoRepo.findById(dto.getCursoId())
                    .orElseThrow(() -> new RuntimeException("Curso não encontrado."));

            Matricula matricula = new Matricula();
            matricula.setId(dto.getId());
            matricula.setAluno(aluno);
            matricula.setCurso(curso);

            Matricula salva = matriculaService.adicionar(matricula);
            return ResponseEntity.status(HttpStatus.CREATED).body(salva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar matrícula.");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Matricula>> listarTodos() {
        List<Matricula> matriculas = matriculaService.listarTodos();
        return ResponseEntity.ok(matriculas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return matriculaRepo.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Matrícula não encontrada."));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarMatricula(@PathVariable Long id, @Valid @RequestBody MatriculaDTO dto) {
        try {
            Usuario aluno = usuarioRepo.findById(dto.getAlunoId())
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado."));
            Curso curso = cursoRepo.findById(dto.getCursoId())
                    .orElseThrow(() -> new RuntimeException("Curso não encontrado."));

            Matricula matriculaAtualizada = new Matricula();
            matriculaAtualizada.setId(dto.getId());
            matriculaAtualizada.setAluno(aluno);
            matriculaAtualizada.setCurso(curso);

            Matricula atualizada = matriculaService.atualizarID(id, matriculaAtualizada);
            return ResponseEntity.ok(atualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar matrícula.");
        }
    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            matriculaService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar matrícula.");
        }
    }
}