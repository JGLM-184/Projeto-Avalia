package br.edu.fatecguarulhos.projetoavalia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	
    boolean existsByAutor(Professor autor);
	
	/* adicionado por amanda */
	
	@Query("SELECT q FROM Questao q WHERE q.curso IN :cursos OR q.disciplina IN :disciplinas")
	List<Questao> findByCursosOrDisciplinas(@Param("cursos") List<Curso> cursos,
	                                        @Param("disciplinas") List<Disciplina> disciplinas);

	@Query("""
		    SELECT q FROM Questao q
		    WHERE LOWER(q.enunciado) LIKE LOWER(CONCAT('%', :termo, '%'))
		       OR LOWER(q.curso.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
		       OR LOWER(q.disciplina.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
		""")
	
	 List<Questao> pesquisarPorTermo(@Param("termo") String termo);
	
	 List<Questao> findByEnunciadoContainingIgnoreCase(String termo);

	 List<Questao> findByDisciplinaNomeContainingIgnoreCase(String termo);

	 List<Questao> findByCursoNomeContainingIgnoreCase(String termo);
	 
	/* adicionado por amanda */
}
