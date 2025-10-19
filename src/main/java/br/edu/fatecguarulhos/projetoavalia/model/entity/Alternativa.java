package br.edu.fatecguarulhos.projetoavalia.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="alternativa")
public class Alternativa {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) //CHAVE PRIMÁRIA EM AUTOINCREMENTO
	private int id;
	private String texto;
	private boolean correto;
	
	//UMA ALTERNATIVA PERTENCE SOMENTE A UMA QUESTÃO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questao_id", nullable = false)
    private Questao questao;
	
	//CONSTRUTORES
	public Alternativa() {
		
	}

	public Alternativa(int id, String texto, boolean correto) {
		this.id = id;
		this.texto = texto;
		this.correto = correto;
	}

	//GETTERS E SETTERS
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
