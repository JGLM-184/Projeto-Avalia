package br.edu.fatecguarulhos.projetoavalia.dto;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Curso;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;
import jakarta.validation.constraints.NotBlank;

public class DisciplinaDTO {

    private int id;

    @NotBlank(message = "O nome da disciplina é obrigatório")
    private String nome;
    
    @NotBlank(message = "O nome do curso é obrigatório")
    private Curso curso;  

	public DisciplinaDTO() { }

    public DisciplinaDTO(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public DisciplinaDTO(Disciplina disciplina) {
        this.id = disciplina.getId();
        this.nome = disciplina.getNome();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
}
