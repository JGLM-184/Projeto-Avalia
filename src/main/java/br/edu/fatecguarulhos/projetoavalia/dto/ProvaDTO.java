package br.edu.fatecguarulhos.projetoavalia.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Professor;
import jakarta.validation.constraints.NotBlank;

public class ProvaDTO {
	
	private int id;
	
    @NotBlank(message = "O título é obrigatório")
    private String titulo;
    
    private LocalDate dataCriacao;
    
    @NotBlank(message = "O nome do professor é obrigatório")
    private Professor professor;
	private boolean simulado;
	private boolean ativo;
    
    @NotBlank(message = "As questões são obrigatórias")
    private List<QuestaoDTO> questoes = new ArrayList<>();

    
    public ProvaDTO() {
    	
    }


	public ProvaDTO(int id, @NotBlank(message = "O título é obrigatório") String titulo, LocalDate dataCriacao,
			@NotBlank(message = "O nome do professor é obrigatório") Professor professor, boolean simulado,
			boolean ativo, List<QuestaoDTO> questoes) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.dataCriacao = dataCriacao;
		this.professor = professor;
		this.simulado = simulado;
		this.ativo = ativo;
		this.questoes = questoes;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public LocalDate getDataCriacao() {
		return dataCriacao;
	}


	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}


	public Professor getProfessor() {
		return professor;
	}


	public void setProfessor(Professor professor) {
		this.professor = professor;
	}


	public boolean isSimulado() {
		return simulado;
	}


	public void setSimulado(boolean simulado) {
		this.simulado = simulado;
	}


	public boolean isAtivo() {
		return ativo;
	}


	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}


	public List<QuestaoDTO> getQuestoes() {
		return questoes;
	}


	public void setQuestoes(List<QuestaoDTO> questoes) {
		this.questoes = questoes;
	}
    
    
}
