package br.edu.fatecguarulhos.projetoavalia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Prova;
import br.edu.fatecguarulhos.projetoavalia.model.entity.ProvaQuestao;

public interface ProvaQuestaoRepository extends JpaRepository<ProvaQuestao, Integer> {

	List<ProvaQuestao> findByProva(Prova prova);
    void deleteByProva(Prova prova);
    
}
