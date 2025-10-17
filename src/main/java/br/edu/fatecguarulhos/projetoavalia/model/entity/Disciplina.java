package br.edu.fatecguarulhos.projetoavalia.model.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="disciplina")
public class Disciplina {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int disciplinaId;
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
}
