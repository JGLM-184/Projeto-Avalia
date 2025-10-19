package br.edu.fatecguarulhos.projetoavalia.dto;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Alternativa;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Questao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AlternativaDTO {

    private int id;

    @NotBlank(message = "O texto da alternativa é obrigatório")
    private String texto;

    private boolean correto;

    @NotNull(message = "A questão é obrigatória")
    private Questao questao;

    // Construtor vazio
    public AlternativaDTO() { }

    // Construtor com todos os campos
    public AlternativaDTO(int id, String texto, boolean correto, Questao questao) {
        this.id = id;
        this.texto = texto;
        this.correto = correto;
        this.questao = questao;
    }

    // Construtor que recebe uma entidade Alternativa
    public AlternativaDTO(Alternativa alternativa) {
        this.id = alternativa.getId();
        this.texto = alternativa.getTexto();
        this.correto = alternativa.isCorreto();
        this.questao = alternativa.getQuestao();
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isCorreto() {
        return correto;
    }

    public void setCorreto(boolean correto) {
        this.correto = correto;
    }

    public Questao getQuestao() {
        return questao;
    }

    public void setQuestao(Questao questao) {
        this.questao = questao;
    }
}
