package br.edu.fatecguarulhos.projetoavalia.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="prova")
public class Prova {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String titulo;
	/*private LocalDate dataCriacao;
	private Questao questoes; //IMPLEMENTAR MULTIPLICIDADE
	private Professor professor;
	private boolean simulado;*/
	
}
