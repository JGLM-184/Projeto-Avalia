package br.edu.fatecguarulhos.projetoavalia.model.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="disciplina")
public class Disciplina {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nome;
    
    // UMA DISCIPLINA POSSUI MUITAS QUESTÕES
    @OneToMany(
        mappedBy = "disciplina", 
        
    //SE A DISCIPLINA FOR APAGADA, AS QUESTÕES TAMBÉM SÃO
        cascade = CascadeType.REMOVE,
        orphanRemoval = true,
        fetch = FetchType.LAZY 
    )
    private Set<Questao> questoes = new HashSet<>();

    //CONSTRUTORES
    public Disciplina() {
    	
    }
    
	public Disciplina(int id, String nome, Set<Questao> questoes) {
		super();
		this.id = id;
		this.nome = nome;
		this.questoes = questoes;
	}

	//GETTERS E SETTERS
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
