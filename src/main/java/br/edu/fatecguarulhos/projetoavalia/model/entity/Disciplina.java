package br.edu.fatecguarulhos.projetoavalia.model.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="disciplina")
public class Disciplina {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nome;
	
	//UMA DISCIPLINA PERTENCE SOMENTE A UM CURSO
    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
    
    @ManyToMany(mappedBy = "disciplinas")
    private List<Professor> professores = new ArrayList<>();

    //CONSTRUTORES
    public Disciplina() {
    	
    }
    
	public Disciplina(int id, String nome, Curso curso) {
		this.id = id;
		this.nome = nome;
		this.curso = curso;
	}

	//GETTERS E SETTERS
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
