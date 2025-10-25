package br.edu.fatecguarulhos.projetoavalia.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Curso;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;

public class ProfessorDetalheDTO {

	private int id;
    private String nome;
    private String email;
    private String re;
    private boolean coordenador;
    private boolean primeiroAcesso;
    private boolean ativo;
    private List<Disciplina> disciplinas = new ArrayList<>();
    private List<Curso> cursos = new ArrayList<>();

    //CONSTRUTORES
    public ProfessorDetalheDTO() {
    	
    }

	public ProfessorDetalheDTO(Integer id, String nome, String email, String re, boolean coordenador,
			boolean primeiroAcesso, boolean ativo, List<Disciplina> disciplinas, List<Curso> cursos) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.re = re;
		this.coordenador = coordenador;
		this.primeiroAcesso = primeiroAcesso;
		this.disciplinas = disciplinas;
		this.cursos = cursos;
		this.ativo = ativo;
	}

	//GETTERS E SETTERS
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}
