package br.edu.fatecguarulhos.projetoavalia.model.entity;

import java.time.LocalDate;

public class Prova {

	private int provaId;
	private String titulo;
	private LocalDate dataCriacao;
	private Questao questoes; //IMPLEMENTAR MULTIPLICIDADE
	private Professor professor;
	private boolean simulado;
	
}
