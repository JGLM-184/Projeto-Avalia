package br.edu.fatecguarulhos.projetoavalia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer>{

	List<Disciplina> findByCursoId(int cursoId);
	
	@Query("SELECT d FROM Disciplina d WHERE d.curso.id IN :cursosIds ORDER BY d.nome")
    List<Disciplina> findByCursoIdIn(List<Integer> cursosIds);

}
