package br.edu.fatecguarulhos.projetoavalia.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tentativaSimulado")
public class TentativaSimulado {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String aluno;
	
	/*
	
	@ManyToOne
	@JoinColumn(name = "prova_id")
	Prova simulado;
	
	private int totalAcertos;
	
	@ManyToMany
    @JoinTable(
        name = "tentativaSimulado_questao",
        joinColumns = @JoinColumn(name = "id"), 
        inverseJoinColumns = @JoinColumn(name = "questao_id")
    )
	private List<Questao> questoesErradas = new ArrayList<>();
	
	private LocalDateTime dataEnvio;
	private boolean ativo;
	
	
	//CONSTRUTORES
	public TentativaSimulado(){
		
	}


	public TentativaSimulado(int id, String aluno, Prova simulado, int totalAcertos, List<Questao> questoesErradas,
			LocalDateTime dataEnvio, boolean ativo) {
		this.id = id;
		this.aluno = aluno;
		this.simulado = simulado;
		this.totalAcertos = totalAcertos;
		this.questoesErradas = questoesErradas;
		this.dataEnvio = dataEnvio;
		this.ativo = ativo;
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


	public List<Questao> getQuestoesErradas() {
		return questoesErradas;
	}


	public void setQuestoesErradas(List<Questao> questoesErradas) {
		this.questoesErradas = questoesErradas;
	}


	public LocalDateTime getDataEnvio() {
		return dataEnvio;
	}


	public void setDataEnvio(LocalDateTime dataEnvio) {
		this.dataEnvio = dataEnvio;
	}


	public boolean isAtivo() {
		return ativo;
	}


	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	*/
}