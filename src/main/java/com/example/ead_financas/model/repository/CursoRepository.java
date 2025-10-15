package com.example.ead_financas.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ead_financas.model.entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
	List<Curso> findByProfessor_Id(Long professorId);
	List<Curso> findByMatriculas_Aluno_Id(Long idAluno);
}

