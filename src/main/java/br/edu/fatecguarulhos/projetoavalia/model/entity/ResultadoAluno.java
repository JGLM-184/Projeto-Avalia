package br.edu.fatecguarulhos.projetoavalia.model.entity;

import java.time.LocalDateTime;

public class ResultadoAluno {

	private int resultadoAlunoId;
	private String aluno;
	private Prova simulado;
	private int totalAcertos;
	private Questao questoesErradas; //IMPLEMENTAR VÁRIAS QUESTÕES
	private LocalDateTime dataEnvio;
}