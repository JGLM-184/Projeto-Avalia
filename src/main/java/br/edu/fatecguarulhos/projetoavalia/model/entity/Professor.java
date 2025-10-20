package br.edu.fatecguarulhos.projetoavalia.model.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="professor")
public class Professor {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nome;
	private String email; 
	private String re; 
	private String senha; 
	
	private boolean coordenador;
	private boolean primeiroAcesso=true;
	private boolean ativo=true;
	
	// RELAÇÃO DE MUITOS PARA MUITOS (DISCIPLINA)
	@ManyToMany
    @JoinTable(
        name = "professor_disciplina",
        joinColumns = @JoinColumn(name = "professor_id"), 
        inverseJoinColumns = @JoinColumn(name = "disciplina_id")
    )
	private List<Disciplina> disciplinas = new ArrayList<>();
	
	
	// RELAÇÃO DE MUITOS PARA MUITOS (CURSO)
	@ManyToMany
	@JoinTable(
        name = "professor_curso",
        joinColumns = @JoinColumn(name = "professor_id"), 
        inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
	private List<Curso> cursos = new ArrayList<>();
	
	//CONSTRUTORES
	public Professor() {
		
	}

	public Professor(int id, String nome, String email, String re, String senha, List<Disciplina> disciplinas,
			List<Curso> cursos, boolean coordenador) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.re = re;
		this.senha = senha;
		this.disciplinas = disciplinas;
		this.cursos = cursos;
		this.coordenador = coordenador;
		this.primeiroAcesso = true;
		this.ativo = true;

	}

	//GETTERS E SETTER
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRe() {
		return re;
	}

	public void setRe(String re) {
		this.re = re;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public boolean isCoordenador() {
		return coordenador;
	}

	public void setCoordenador(boolean coordenador) {
		this.coordenador = coordenador;
	}

	public boolean isPrimeiroAcesso() {
		return primeiroAcesso;
	}

	public void setPrimeiroAcesso(boolean primeiroAcesso) {
		this.primeiroAcesso = primeiroAcesso;
	}
	
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}