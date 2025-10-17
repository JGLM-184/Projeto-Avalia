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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="questao")
public class Questao {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idQuestao;
	private String enunciado;
	
	// RELACIONAMENTOS MUITOS PARA UM
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false) 
	private Professor autor;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false) 
	private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplina_id", nullable = false) 
    private Disciplina disciplina;	
    
    // RELACIONAMENTO UM PARA MUITOS (ALTERNATIVAS)
    @OneToMany(
        mappedBy = "questao", 
    //SE A QUESTÃO FOR APAGADA, AS ALTERNATIVAS TAMBÉM SÃO
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY 
    )
    private Set<Alternativa> alternativas = new HashSet<>();
 	
	private boolean simulado;	
	
	//CONSTRUTORES
	public Questao() {
		
	}

	public Questao(int idQuestao, String enunciado, Professor autor, Curso curso, Disciplina disciplina,
			boolean simulado) {
		this.idQuestao = idQuestao;
		this.enunciado = enunciado;
		this.autor = autor;
		this.curso = curso;
		this.disciplina = disciplina;
		this.simulado = simulado;
	}

	//GETTERS E SETTERS
	public int getIdQuestao() {
		return idQuestao;
	}

	public void setIdQuestao(int idQuestao) {
		this.idQuestao = idQuestao;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public Professor getAutor() {
		return autor;
	}

	public void setAutor(Professor autor) {
		this.autor = autor;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public boolean isSimulado() {
		return simulado;
	}

	public void setSimulado(boolean simulado) {
		this.simulado = simulado;
	}
}