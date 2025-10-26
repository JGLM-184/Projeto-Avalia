package br.edu.fatecguarulhos.projetoavalia.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name="prova_questao",
	   uniqueConstraints = @UniqueConstraint(columnNames = {"prova_id", "questao_id"}))
public class ProvaQuestao {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
	
	@ManyToOne
	@JoinColumn(name = "questao_id")
	private Questao questao;
	
	@ManyToOne
	@JoinColumn(name = "prova_id")
	private Prova prova;

	public ProvaQuestao() {
		
	}
	
	public ProvaQuestao(int id, Questao questao, Prova prova) {
		super();
		this.id = id;
		this.questao = questao;
		this.prova = prova;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
