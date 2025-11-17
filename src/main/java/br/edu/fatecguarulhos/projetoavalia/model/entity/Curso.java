package br.edu.fatecguarulhos.projetoavalia.model.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="curso")
public class Curso {
    
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nome;
	
	@ManyToMany(mappedBy = "cursos")
	private List<Professor> professores;
	
	//CONSTRUTORES
	public Curso() {
		
	}

	public Curso(int id, String nome) {
		this.id = id;
		this.nome = nome;
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
}