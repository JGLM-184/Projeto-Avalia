package br.edu.fatecguarulhos.projetoavalia.dto;

import java.util.ArrayList;
import java.util.List;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Curso;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Professor;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Questao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class QuestaoDTO {

    private int id;

    @NotBlank(message = "O enunciado é obrigatório")
    private String enunciado;

    private String imagem;

    @NotNull(message = "O autor é obrigatório")
    private Professor autor;

    @NotNull(message = "O curso é obrigatório")
    private Curso curso;

    @NotNull(message = "A disciplina é obrigatória")
    private Disciplina disciplina;

    private boolean simulado;

    private List<AlternativaDTO> alternativas = new ArrayList<>();

    public QuestaoDTO() { }

    public QuestaoDTO(int id, String enunciado, String imagem, Professor autor,
                      Curso curso, Disciplina disciplina, boolean simulado) {
        this.id = id;
        this.enunciado = enunciado;
        this.imagem = imagem;
        this.autor = autor;
        this.curso = curso;
        this.disciplina = disciplina;
        this.simulado = simulado;
    }

    public QuestaoDTO(Questao questao) {
        this.id = questao.getId();
        this.enunciado = questao.getEnunciado();
        this.imagem = questao.getImagem();
        this.autor = questao.getAutor();
        this.curso = questao.getCurso();
        this.disciplina = questao.getDisciplina();
        this.simulado = questao.isSimulado();
        // Preenchendo alternativas já existentes
        if (questao.getAlternativas() != null) {
            for (var alt : questao.getAlternativas()) {
                this.alternativas.add(new AlternativaDTO(alt));
            }
        }
    }

    // Getters e Setters
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

	public List<AlternativaDTO> getAlternativas() {
		return alternativas;
	}

	public void setAlternativas(List<AlternativaDTO> alternativas) {
		this.alternativas = alternativas;
	}
	
    public List<AlternativaDTO> addAlternativa(int qnt) {
        for (int i = 1; i <= qnt; i++) {
            alternativas.add(new AlternativaDTO());
        }

        return alternativas;
    }
}
