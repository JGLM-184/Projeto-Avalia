package br.edu.fatecguarulhos.projetoavalia.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tentativaSimulado")
public class TentativaSimulado {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String aluno;
	private String semestre;
	
	@ManyToOne
	@JoinColumn(name = "prova_id")
	Prova simulado;
	
	private int totalAcertos;
	
	private LocalDateTime dataEnvio;	
	
	//CONSTRUTORES
	public TentativaSimulado(){
		
	}

	public TentativaSimulado(int id, String aluno, String semestre, Prova simulado, int totalAcertos,
			LocalDateTime dataEnvio) {
		this.id = id;
		this.aluno = aluno;
		this.semestre = semestre;
		this.simulado = simulado;
		this.totalAcertos = totalAcertos;
		this.dataEnvio = dataEnvio;
	}

	//GETTERS E SETTERS
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getAluno() {
		return aluno;
	}


	public void setAluno(String aluno) {
		this.aluno = aluno;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public Prova getSimulado() {
		return simulado;
	}


	public void setSimulado(Prova simulado) {
		this.simulado = simulado;
	}


	public int getTotalAcertos() {
		return totalAcertos;
	}


	public void setTotalAcertos(int totalAcertos) {
		this.totalAcertos = totalAcertos;
	}

	public LocalDateTime getDataEnvio() {
		return dataEnvio;
	}


	public void setDataEnvio(LocalDateTime dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
}