package br.edu.fatecguarulhos.projetoavalia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Prova;
import br.edu.fatecguarulhos.projetoavalia.model.entity.TentativaSimulado;

public interface TentativaSimuladoRepository extends JpaRepository<TentativaSimulado, Integer> {

	List<TentativaSimulado> findBySimuladoOrderByDataEnvioDesc(Prova simulado);

	void deleteBySimulado(Prova simulado);
	
}
