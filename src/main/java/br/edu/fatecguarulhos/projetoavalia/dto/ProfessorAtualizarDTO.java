package br.edu.fatecguarulhos.projetoavalia.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class ProfessorAtualizarDTO {

	private int id;
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;

    @NotBlank(message = "O RE é obrigatório.")
    private String re;
    
    @NotEmpty(message = "É necessário informar pelo menos uma disciplina.")
    private List<Integer> idsDisciplinas = new ArrayList<>();

    @NotEmpty(message = "É necessário informar pelo menos um curso.")
    private List<Integer> idsCursos = new ArrayList<>();
    
    private boolean coordenador;
    private boolean ativo;
    
    
    //CONSTRUTORES
    public ProfessorAtualizarDTO() {
    	
    }

	public ProfessorAtualizarDTO(@NotBlank(message = "O nome é obrigatório.") String nome,
			@NotBlank(message = "O e-mail é obrigatório.") @Email(message = "Formato de e-mail inválido.") String email,
			@NotBlank(message = "O RE é obrigatório.") String re, boolean coordenador,
			@NotEmpty(message = "É necessário informar pelo menos uma disciplina.") List<Integer> idsDisciplinas,
			@NotEmpty(message = "É necessário informar pelo menos um curso.") List<Integer> idsCursos, boolean ativo) {
		super();
		this.nome = nome;
		this.email = email;
		this.re = re;
		this.coordenador = coordenador;
		this.idsDisciplinas = idsDisciplinas;
		this.idsCursos = idsCursos;
		this.ativo = ativo;
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

	public List<Integer> getIdsDisciplinas() {
		return idsDisciplinas;
	}

	public void setIdsDisciplinas(List<Integer> idsDisciplinas) {
		this.idsDisciplinas = idsDisciplinas;
	}

	public List<Integer> getIdsCursos() {
		return idsCursos;
	}

	public void setIdsCursos(List<Integer> idsCursos) {
		this.idsCursos = idsCursos;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}	
	
	
}
