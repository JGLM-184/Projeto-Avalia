package br.edu.fatecguarulhos.projetoavalia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fatecguarulhos.projetoavalia.dto.DisciplinaDTO;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Questao;
import br.edu.fatecguarulhos.projetoavalia.repository.DisciplinaRepository;
import br.edu.fatecguarulhos.projetoavalia.repository.QuestaoRepository;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private QuestaoRepository questaoRepository;

    // Lista todas as disciplinas
    public List<Disciplina> listarTodas() {
        return disciplinaRepository.findAll();
    }

    public Disciplina buscarPorId(int id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada com id: " + id));
    }

    public List<Disciplina> listarDisciplinasPorProfessor(int professorId) {
        return disciplinaRepository.listarDisciplinasPorProfessor(professorId);
    }

    public List<Disciplina> buscarPorCursoId(int cursoId) {
        return disciplinaRepository.findByCursoId(cursoId);
    }

    public List<Disciplina> buscarPorCursosIds(List<Integer> cursosIds) {
        return disciplinaRepository.findByCursoIdIn(cursosIds);
    }

    public List<Disciplina> buscarPorCursoQuestaoId(int questaoId) {
        Questao questao = questaoRepository.findById(questaoId)
                .orElseThrow(() -> new RuntimeException("Questão não encontrada com id: " + questaoId));
        return disciplinaRepository.findByCursoId(questao.getCurso().getId());
    }

    public Disciplina salvar(DisciplinaDTO dto) {
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(dto.getNome());
        disciplina.setCurso(dto.getCurso());
        return disciplinaRepository.save(disciplina);
    }

    public Optional<Disciplina> atualizar(int id, DisciplinaDTO dto) {
        Optional<Disciplina> disciplinaOpt = disciplinaRepository.findById(id);
        if (disciplinaOpt.isPresent()) {
            Disciplina disciplina = disciplinaOpt.get();
            disciplina.setNome(dto.getNome());
            disciplina.setCurso(dto.getCurso());
            disciplinaRepository.save(disciplina);
        }
        return disciplinaOpt;
    }

    // Exclusão: bloqueia apenas se houver questões associadas
    public void excluir(int id) {
        Optional<Disciplina> disciplinaOpt = disciplinaRepository.findById(id);
        if (disciplinaOpt.isEmpty()) {
            throw new RuntimeException("Disciplina não encontrada com id: " + id);
        }

        Disciplina disciplina = disciplinaOpt.get();

        // Usa existsByDisciplina para checar se há questões
        boolean temQuestoes = questaoRepository.existsByDisciplina(disciplina);

        if (temQuestoes) {
            throw new IllegalStateException(
                    "Não é possível excluir esta disciplina pois já existem questões cadastradas para ela.");
        }

        // Apenas aqui exclui, sem interferir na listagem
        disciplinaRepository.delete(disciplina);
    }
}
