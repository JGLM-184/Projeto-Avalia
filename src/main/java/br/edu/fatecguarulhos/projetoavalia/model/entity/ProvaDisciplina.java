package br.edu.fatecguarulhos.projetoavalia.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="prova_disciplina")
public class ProvaDisciplina {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
	private int qntQuestoes;
	
	@ManyToOne
	@JoinColumn(name = "disciplina_id")
	private Questao questao;
	
	@ManyToOne
	@JoinColumn(name = "prova_id")
	private Prova prova;

	public ProvaDisciplina() {
		
	}
	
	public ProvaDisciplina(int id, int qntQuestoes, Questao questao, Prova prova) {
		this.id = id;
		this.qntQuestoes = qntQuestoes;
		this.questao = questao;
		this.prova = prova;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQntQuestoes() {
		return qntQuestoes;
	}

	public void setQntQuestoes(int qntQuestoes) {
		this.qntQuestoes = qntQuestoes;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public Prova getProva() {
		return prova;
	}

	public void setProva(Prova prova) {
		this.prova = prova;
	}
	
	
	
	
}
