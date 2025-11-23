package br.edu.fatecguarulhos.projetoavalia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
	
	Optional<Professor> findByEmail(String email);	
	Optional<Professor> findByRe(String re);

}
