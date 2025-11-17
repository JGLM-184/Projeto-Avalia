package br.edu.fatecguarulhos.projetoavalia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, Integer>{

	@Query("SELECT c FROM Curso c JOIN c.professores p WHERE p.id = :profId")
	List<Curso> findCursosByProfessor(@Param("profId") int professorId);

}
