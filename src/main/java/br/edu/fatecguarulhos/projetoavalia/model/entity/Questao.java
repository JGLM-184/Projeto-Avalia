package br.edu.fatecguarulhos.projetoavalia.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="questao")
public class Questao {

	//Atributos
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idQuestao;
	
	//Construtores
	public Questao() {
		
	}
	
	//Getters e Setters
	
	
}
