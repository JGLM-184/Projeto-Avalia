package br.edu.fatecguarulhos.projetoavalia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Prova;
import br.edu.fatecguarulhos.projetoavalia.model.entity.ProvaQuestao;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Questao;

public interface ProvaQuestaoRepository extends JpaRepository<ProvaQuestao, Integer> {

	List<ProvaQuestao> findByProva(Prova prova);
    void deleteByProva(Prova prova);
    
    Optional<ProvaQuestao> findByProvaAndQuestao(Prova prova, Questao questao);
	List<ProvaQuestao> findByProvaId(int provaId);
    
}
