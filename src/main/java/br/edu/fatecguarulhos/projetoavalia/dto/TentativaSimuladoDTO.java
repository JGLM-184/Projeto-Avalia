package br.edu.fatecguarulhos.projetoavalia.dto;

import java.time.LocalDateTime;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Prova;
import jakarta.validation.constraints.NotBlank;

public class TentativaSimuladoDTO {

	private int id;
	
    @NotBlank(message = "O nome do aluno é obrigatório")
	private String aluno;
    
    @NotBlank(message = "O semestre é obrigatório")
	private int semestre;
    
	Prova simulado;
	private int totalAcertos;
	private LocalDateTime dataEnvio;
	private boolean ativo;
	
	public TentativaSimuladoDTO() {
		
	}

	public TentativaSimuladoDTO(int id, @NotBlank(message = "O nome do aluno é obrigatório") String aluno,
			@NotBlank(message = "O semestre é obrigatório") int semestre, Prova simulado, int totalAcertos,
			LocalDateTime dataEnvio, boolean ativo) {
		this.id = id;
		this.aluno = aluno;
		this.semestre = semestre;
		this.simulado = simulado;
		this.totalAcertos = totalAcertos;
		this.dataEnvio = dataEnvio;
		this.ativo = ativo;
	}



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
	
	public int getSemestre() {
		return semestre;
	}

	public void setSemestre(int semestre) {
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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	
}
