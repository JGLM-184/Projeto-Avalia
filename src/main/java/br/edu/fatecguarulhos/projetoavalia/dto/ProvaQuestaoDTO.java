package br.edu.fatecguarulhos.projetoavalia.dto;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Prova;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Questao;

public class ProvaQuestaoDTO {
	
	private int id;
    private Questao questao;
    private Prova prova;
	
    public ProvaQuestaoDTO() {
    	
    }
    
    public ProvaQuestaoDTO(int id, Questao questao, Prova prova) {
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
