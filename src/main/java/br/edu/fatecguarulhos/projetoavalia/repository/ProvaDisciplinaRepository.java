package br.edu.fatecguarulhos.projetoavalia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Prova;
import br.edu.fatecguarulhos.projetoavalia.model.entity.ProvaDisciplina;

public interface ProvaDisciplinaRepository extends JpaRepository<ProvaDisciplina, Integer> {

	List<ProvaDisciplina> findByProva(Prova prova);
    void deleteByProva(Prova prova);
	boolean existsByProvaAndDisciplina(Prova prova, Disciplina disciplina);
	Optional<ProvaDisciplina> findByProvaAndDisciplina(Prova prova, Disciplina disciplina);
    
}
