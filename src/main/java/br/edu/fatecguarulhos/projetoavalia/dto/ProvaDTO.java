package br.edu.fatecguarulhos.projetoavalia.dto;

import java.time.LocalDate;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Curso;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Professor;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Prova;
import jakarta.validation.constraints.NotBlank;

public class ProvaDTO {
	
	private int id;
	
    @NotBlank(message = "O título é obrigatório")
    private String titulo;
    
    private LocalDate dataCriacao;
    
    @NotBlank(message = "O nome do professor é obrigatório")
    private Professor professor;
    
    @NotBlank(message = "O curso é obrigatório")
    private Curso curso;
    
	private boolean simulado;
	private String codigoSimulado = "";
	private boolean ativo;
    
    public ProvaDTO() {
    	
    }

	public ProvaDTO(int id, String titulo, LocalDate dataCriacao, Professor professor, 
			Curso curso, boolean simulado, boolean ativo) {
		this.id = id;
		this.titulo = titulo;
		this.dataCriacao = dataCriacao;
		this.professor = professor;
		this.curso = curso;
		this.simulado = simulado;
		this.ativo = ativo;
	}
	
	public ProvaDTO(Prova prova) {
		this.id = prova.getId();
		this.titulo = prova.getTitulo();
		this.dataCriacao = prova.getDataCriacao();
		this.professor = prova.getProfessor();
		this.curso = prova.getCurso();
		this.simulado = prova.isSimulado();
		this.codigoSimulado = prova.getCodigoSimulado();
		this.ativo = prova.isAtivo();
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

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public boolean isSimulado() {
		return simulado;
	}

	public void setSimulado(boolean simulado) {
		this.simulado = simulado;
	}
	
	public String getCodigoSimulado() {
		return codigoSimulado;
	}

	public void setCodigoSimulado(String codigoSimulado) {
		this.codigoSimulado = codigoSimulado;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
        
}
