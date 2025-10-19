package br.edu.fatecguarulhos.projetoavalia.dto;

import java.util.HashSet;
import java.util.Set;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Curso;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;
import jakarta.validation.constraints.NotBlank;

public class CursoDTO {

    private int id;

    @NotBlank(message = "O nome do curso é obrigatório")
    private String nome;

    private Set<Disciplina> disciplinas = new HashSet<>();

    public CursoDTO() { }

    public CursoDTO(int id, String nome, Set<Disciplina> disciplinas) {
        this.id = id;
        this.nome = nome;
        this.disciplinas = disciplinas;
    }

    public CursoDTO(Curso curso) {
        this.id = curso.getId();
        this.nome = curso.getNome();
        this.disciplinas = curso.getDisciplinas();
    }

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
