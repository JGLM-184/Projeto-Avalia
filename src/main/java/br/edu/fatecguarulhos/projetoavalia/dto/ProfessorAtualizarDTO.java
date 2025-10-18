package br.edu.fatecguarulhos.projetoavalia.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public class ProfessorAtualizarDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;

    @NotBlank(message = "O RE é obrigatório.")
    private String re;

    private boolean coordenador;

    @NotEmpty(message = "É necessário informar pelo menos uma disciplina.")
    private Set<Integer> idsDisciplinas;

    @NotEmpty(message = "É necessário informar pelo menos um curso.")
    private Set<Integer> idsCursos;
    
    
    //CONSTRUTORES
    public ProfessorAtualizarDTO() {
    	
    }

	public ProfessorAtualizarDTO(@NotBlank(message = "O nome é obrigatório.") String nome,
			@NotBlank(message = "O e-mail é obrigatório.") @Email(message = "Formato de e-mail inválido.") String email,
			@NotBlank(message = "O RE é obrigatório.") String re, boolean coordenador,
			@NotEmpty(message = "É necessário informar pelo menos uma disciplina.") Set<Integer> idsDisciplinas,
			@NotEmpty(message = "É necessário informar pelo menos um curso.") Set<Integer> idsCursos) {
		this.nome = nome;
		this.email = email;
		this.re = re;
		this.coordenador = coordenador;
		this.idsDisciplinas = idsDisciplinas;
		this.idsCursos = idsCursos;
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
}
