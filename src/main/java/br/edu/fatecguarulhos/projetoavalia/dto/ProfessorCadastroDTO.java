package br.edu.fatecguarulhos.projetoavalia.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Set;

public class ProfessorCadastroDTO {

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
    private Set<Integer> idsDisciplinas;

    @NotEmpty(message = "É necessário informar pelo menos um curso.")
    private Set<Integer> idsCursos;
    
    private boolean ativo = true;
    
    //CONSTRUTORES
	public ProfessorCadastroDTO() {
		
	}
	
	public ProfessorCadastroDTO(@NotBlank(message = "O nome é obrigatório.") String nome,
			@NotBlank(message = "O e-mail é obrigatório.") @Email(message = "Formato de e-mail inválido.") String email,
			@NotBlank(message = "O RE é obrigatório.") String re,
			@NotBlank(message = "A senha é obrigatória.") @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.") String senha,
			boolean coordenador,
			@NotEmpty(message = "É necessário informar pelo menos uma disciplina.") Set<Integer> idsDisciplinas,
			@NotEmpty(message = "É necessário informar pelo menos um curso.") Set<Integer> idsCursos) {
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

	public Set<Integer> getIdsDisciplinas() {
		return idsDisciplinas;
	}

	public void setIdsDisciplinas(Set<Integer> idsDisciplinas) {
		this.idsDisciplinas = idsDisciplinas;
	}

	public Set<Integer> getIdsCursos() {
		return idsCursos;
	}

	public void setIdsCursos(Set<Integer> idsCursos) {
		this.idsCursos = idsCursos;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}    
}
