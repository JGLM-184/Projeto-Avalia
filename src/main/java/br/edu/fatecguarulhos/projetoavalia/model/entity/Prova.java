package br.edu.fatecguarulhos.projetoavalia.model.entity;

import java.time.LocalDate;
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
@Table(name="prova")
public class Prova {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String titulo;
	private LocalDate dataCriacao;
	
	/*
	//UMA PROVA TEM VÁRIAS QUESTÕES
	@ManyToMany
    @JoinTable(
        name = "prova_questao",
        joinColumns = @JoinColumn(name = "id"), 
        inverseJoinColumns = @JoinColumn(name = "questao_id")
    )
	private Set<Disciplina> disciplinas = new HashSet<>();
	private List<Questao> questoes = new ArrayList<>();
		
	 
     //AQUI EU FIQUEI EM DÚVIDA SE SERIA UM PROFESSOR SÓ
     //O JADIR DISSE QUE PODERIA FAZER UMA PROVA MISTA, ENTÃO SERIA MAIS DE UM PROFESSOR NÉ?
 
	
	@ManyToOne
	@JoinColumn(name = "professor_id")
	private Professor professor;
	private boolean simulado;
	private boolean ativo;
	
	
	//CONSTRUTORES
	public Prova() {
		
	}

	public Prova(int id, String titulo, LocalDate dataCriacao, Set<Disciplina> disciplinas, List<Questao> questoes,
			Professor professor, boolean simulado, boolean ativo) {
		this.id = id;
		this.titulo = titulo;
		this.dataCriacao = dataCriacao;
		this.disciplinas = disciplinas;
		this.questoes = questoes;
		this.professor = professor;
		this.simulado = simulado;
		this.ativo = ativo;
	}

	
	//GETTERS E SETTERS
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

	public Set<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(Set<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public List<Questao> getQuestoes() {
		return questoes;
	}

	public void setQuestoes(List<Questao> questoes) {
		this.questoes = questoes;
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
	*/
}
