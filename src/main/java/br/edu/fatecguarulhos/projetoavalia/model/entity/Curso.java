package br.edu.fatecguarulhos.projetoavalia.model.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn; // <--- ESTE IMPORT ESTAVA FALTANDO!
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="curso")
public class Curso {
    
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nome;
    
	// RELAÇÃO DE MUITOS PARA MUITOS (UNIDIRECIONAL) - DISCIPLINA
	@ManyToMany
    @JoinTable(
        name = "curso_disciplina",
        joinColumns = @JoinColumn(name = "curso_id"), 
        inverseJoinColumns = @JoinColumn(name = "disciplina_id")
    )
	private Set<Disciplina> disciplinas = new HashSet<>();

	
	//CONSTRUTORES
	public Curso() {
		
	}

	public Curso(int id, String nome, Set<Disciplina> disciplinas) {
		this.id = id;
		this.nome = nome;
		this.disciplinas = disciplinas;
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

	public Set<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(Set<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
}