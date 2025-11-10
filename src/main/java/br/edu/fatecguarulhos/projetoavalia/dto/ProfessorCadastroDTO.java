package br.edu.fatecguarulhos.projetoavalia.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ProfessorCadastroDTO {

	private int id;
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;

    @NotBlank(message = "O RE é obrigatório.")
    private String re;

    @NotBlank(message = "A senha provisória é obrigatória.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    private String senha;

    private boolean coordenador;

    @NotEmpty(message = "É necessário informar pelo menos uma disciplina.")
    private List<Integer> idsDisciplinas;

    @NotEmpty(message = "É necessário informar pelo menos um curso.")
    private List<Integer> idsCursos;
    
    private boolean ativo = true;
    
    //CONSTRUTORES
	public ProfessorCadastroDTO() {
		
	}
	
	public ProfessorCadastroDTO(@NotBlank(message = "O nome é obrigatório.") String nome,
			@NotBlank(message = "O e-mail é obrigatório.") @Email(message = "Formato de e-mail inválido.") String email,
			@NotBlank(message = "O RE é obrigatório.") String re,
			@NotBlank(message = "A senha é obrigatória.") @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.") String senha,
			boolean coordenador,
			@NotEmpty(message = "É necessário informar pelo menos uma disciplina.") List<Integer> idsDisciplinas,
			@NotEmpty(message = "É necessário informar pelo menos um curso.") List<Integer> idsCursos) {
		this.nome = nome;
		this.email = email;
		this.re = re;
		this.senha = senha;
		this.coordenador = coordenador;
		this.idsDisciplinas = idsDisciplinas;
		this.idsCursos = idsCursos;
		this.ativo = true;
	}
	
	//GETTERS E SETTERS
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
