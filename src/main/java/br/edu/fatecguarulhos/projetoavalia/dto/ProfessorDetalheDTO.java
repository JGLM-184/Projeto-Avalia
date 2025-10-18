package br.edu.fatecguarulhos.projetoavalia.dto;

import java.util.Set;

public class ProfessorDetalheDTO {

	private Integer id;
    private String nome;
    private String email;
    private String re;
    private boolean coordenador;
    private boolean primeiroAcesso;
    private Set<String> disciplinas;
    private Set<String> cursos;

    //CONSTRUTORES
    public ProfessorDetalheDTO() {
    	
    }

	public ProfessorDetalheDTO(Integer id, String nome, String email, String re, boolean coordenador,
			boolean primeiroAcesso, Set<String> disciplinas, Set<String> cursos) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.re = re;
		this.coordenador = coordenador;
		this.primeiroAcesso = primeiroAcesso;
		this.disciplinas = disciplinas;
		this.cursos = cursos;
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

	public Set<String> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(Set<String> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public Set<String> getCursos() {
		return cursos;
	}

	public void setCursos(Set<String> cursos) {
		this.cursos = cursos;
	}
}
