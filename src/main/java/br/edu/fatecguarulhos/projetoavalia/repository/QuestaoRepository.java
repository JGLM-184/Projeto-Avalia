package br.edu.fatecguarulhos.projetoavalia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Curso;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Professor;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Questao;

@Repository
public interface QuestaoRepository extends JpaRepository<Questao, Integer> {

	List<Questao> findByCurso(Curso curso);

	List<Questao> findByDisciplina(Disciplina disciplina);

	List<Questao> findByAutor(Professor professor);

}
