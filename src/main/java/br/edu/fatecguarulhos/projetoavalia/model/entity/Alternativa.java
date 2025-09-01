package br.edu.fatecguarulhos.projetoavalia.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="alternativa")
public class Alternativa {

	//Atributos
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAlternativa;
	
	//Construtores
	public Alternativa() {
		
	}
	
	//Getters e Setters
	
	
}
