package br.edu.fatecguarulhos.projetoavalia.model.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="resultadoAluno")
public class ResultadoAluno {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int resultadoAlunoId;
	private String aluno;
	/*private Prova simulado;
	private int totalAcertos;
	private Questao questoesErradas; //IMPLEMENTAR VÁRIAS QUESTÕES
	private LocalDateTime dataEnvio;*/
}