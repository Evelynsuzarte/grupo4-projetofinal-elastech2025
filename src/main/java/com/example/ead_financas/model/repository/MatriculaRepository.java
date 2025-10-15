package com.example.ead_financas.model.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ead_financas.model.entity.*;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

	List<Matricula> findByAluno(Usuario aluno);

	List<Matricula> findByCurso(Curso curso);

	Optional<Matricula> findByAlunoAndCurso(Usuario aluno, Curso curso);

}
