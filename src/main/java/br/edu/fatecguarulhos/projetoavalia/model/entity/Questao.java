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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="questao")
public class Questao {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String enunciado;
	private String imagem;
	
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

	public Questao(int id, String enunciado, String imagem, Professor autor, Curso curso, Disciplina disciplina,
			boolean simulado) {
		this.id = id;
		this.enunciado = enunciado;
		this.imagem = imagem;
		this.autor = autor;
		this.curso = curso;
		this.disciplina = disciplina;
		this.simulado = simulado;
	}

	//GETTERS E SETTERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}
	
	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
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

	public Set<Alternativa> getAlternativas() {
		return alternativas;
	}

	public void setAlternativas(Set<Alternativa> alternativas) {
		this.alternativas = alternativas;
	}
	
	
}