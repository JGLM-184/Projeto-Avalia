package br.edu.fatecguarulhos.projetoavalia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fatecguarulhos.projetoavalia.model.entity.TentativaSimulado;

public interface ResultadoAlunoRepository  extends JpaRepository<TentativaSimulado, Integer>{

}
