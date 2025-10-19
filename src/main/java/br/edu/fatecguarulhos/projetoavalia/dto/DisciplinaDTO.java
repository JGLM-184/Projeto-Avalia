package br.edu.fatecguarulhos.projetoavalia.dto;

import java.util.HashSet;
import java.util.Set;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Questao;
import jakarta.validation.constraints.NotBlank;

public class DisciplinaDTO {

    private int id;

    @NotBlank(message = "O nome da disciplina é obrigatório")
    private String nome;

    private Set<Questao> questoes = new HashSet<>();

    public DisciplinaDTO() { }

    public DisciplinaDTO(int id, String nome, Set<Questao> questoes) {
        this.id = id;
        this.nome = nome;
        this.questoes = questoes;
    }

    public DisciplinaDTO(Disciplina disciplina) {
        this.id = disciplina.getId();
        this.nome = disciplina.getNome();
        this.questoes = disciplina.getQuestoes();
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

    public Set<Questao> getQuestoes() {
        return questoes;
    }

    public void setQuestoes(Set<Questao> questoes) {
        this.questoes = questoes;
    }
}
