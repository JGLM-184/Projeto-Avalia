package br.edu.fatecguarulhos.projetoavalia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Alternativa;

@Repository
public interface AlternativaRepository extends JpaRepository<Alternativa, Integer> {

}
