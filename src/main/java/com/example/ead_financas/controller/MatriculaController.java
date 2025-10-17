package com.example.ead_financas.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<Map<String, Object>>> buscarPorAluno(@PathVariable Long alunoId) {
        Usuario aluno = usuarioRepo.findById(alunoId)
            .orElseThrow(() -> new RuntimeException("Aluno não encontrado."));
        List<Matricula> matriculas = matriculaService.findByAluno(aluno);

        List<Map<String, Object>> resposta = new java.util.ArrayList<>();

        for (Matricula m : matriculas) {
            Map<String, Object> dados = new java.util.HashMap<>();
            dados.put("numeroMatricula", m.getnumeroMatricula());
            dados.put("dataMatricula", m.getDataMatricula());
            dados.put("tituloCurso", m.getCurso() != null ? m.getCurso().getTitulo() : null);
            dados.put("nome", 
                (m.getCurso() != null && m.getCurso().getProfessor() != null) ? 
                m.getCurso().getProfessor().getNome() : null
            );
            resposta.add(dados);
        }

        return ResponseEntity.ok(resposta);
    }

    

    @GetMapping("/")
    public ResponseEntity<List<Map<String, Object>>> listarTodos() {
        List<Matricula> matriculas = matriculaService.listarTodos();

        List<Map<String, Object>> resposta = new ArrayList<>();

        for (Matricula m : matriculas) {
            Map<String, Object> dados = new HashMap<>();
            dados.put("idMatricula", m.getId());
            dados.put("nome", m.getAluno() != null ? m.getAluno().getNome() : null);
            dados.put("tituloCurso", m.getCurso() != null ? m.getCurso().getTitulo() : null);
            dados.put("dataMatricula", m.getDataMatricula());
            resposta.add(dados);
        }

        return ResponseEntity.ok(resposta);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Matricula> matriculaOpt = matriculaRepo.findById(id);

        if (matriculaOpt.isPresent()) {
            Matricula matricula = matriculaOpt.get();
            Map<String, Object> resposta = new java.util.HashMap<>();
            resposta.put("numeroMatricula", matricula.getnumeroMatricula());
            resposta.put("nome", matricula.getAluno() != null ? matricula.getAluno().getNome() : null);
            resposta.put("tituloCurso", matricula.getCurso() != null ? matricula.getCurso().getTitulo() : null);
            resposta.put("dataMatricula", matricula.getDataMatricula());
            // Adicione outros campos se necessário
            return ResponseEntity.ok(resposta);
        }
        // Substituição da linha orElse: retorna 404 manualmente
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Matrícula não encontrada.");
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