package br.edu.fatecguarulhos.projetoavalia.dto;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Prova;

public class ProvaDisciplinaDTO {
	
	private int id;
	private int qntQuestoes;
    private Disciplina disciplina;
    private Prova prova;
	
    public ProvaDisciplinaDTO() {
    	
    }

	public ProvaDisciplinaDTO(int id, int qntQuestoes, Disciplina disciplina, Prova prova) {
		this.id = id;
		this.qntQuestoes = qntQuestoes;
		this.disciplina = disciplina;
		this.prova = prova;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Prova getProva() {
		return prova;
	}

	public void setProva(Prova prova) {
		this.prova = prova;
	}

	public int getQntQuestoes() {
		return qntQuestoes;
	}

	public void setQntQuestoes(int qntQuestoes) {
		this.qntQuestoes = qntQuestoes;
	}
     
}
