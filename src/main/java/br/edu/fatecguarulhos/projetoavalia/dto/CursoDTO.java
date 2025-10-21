package br.edu.fatecguarulhos.projetoavalia.dto;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Curso;
import jakarta.validation.constraints.NotBlank;

public class CursoDTO {

    private int id;

    @NotBlank(message = "O nome do curso é obrigatório")
    private String nome;

    public CursoDTO() { }

    public CursoDTO(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public CursoDTO(Curso curso) {
        this.id = curso.getId();
        this.nome = curso.getNome();
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
}
