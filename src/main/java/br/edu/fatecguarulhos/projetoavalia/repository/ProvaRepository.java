package br.edu.fatecguarulhos.projetoavalia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Prova;

public interface ProvaRepository extends JpaRepository<Prova, Integer>{

	List<Prova> findByCursoId(int cursoId);

	Optional<Prova> findByTitulo(String titulo);
}
