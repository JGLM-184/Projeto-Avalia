package br.edu.fatecguarulhos.projetoavalia.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Prova;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Questao;
import jakarta.validation.constraints.NotBlank;

public class TentativaSimuladoDTO {

	private int id;
	
    @NotBlank(message = "O nome do aluno é obrigatório")
	private String aluno;
    
	Prova simulado;
	private int totalAcertos;
	private List<Questao> questoesErradas = new ArrayList<>();
	private LocalDateTime dataEnvio;
	private boolean ativo;
	
	public TentativaSimuladoDTO() {
		
	}

	public TentativaSimuladoDTO(int id, @NotBlank(message = "O nome do aluno é obrigatório") String aluno,
			Prova simulado, int totalAcertos, List<Questao> questoesErradas, LocalDateTime dataEnvio, boolean ativo) {
		this.id = id;
		this.aluno = aluno;
		this.simulado = simulado;
		this.totalAcertos = totalAcertos;
		this.questoesErradas = questoesErradas;
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
	
	
}
