package br.edu.fatecguarulhos.projetoavalia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fatecguarulhos.projetoavalia.dto.DisciplinaDTO;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;
import br.edu.fatecguarulhos.projetoavalia.repository.DisciplinaRepository;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public List<Disciplina> listarTodas() {
        return disciplinaRepository.findAll();
    }

    public Disciplina buscarPorId(int id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada com id: " + id));
    }

    public Disciplina salvar(DisciplinaDTO dto) {
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(dto.getNome());
        disciplina.setQuestoes(dto.getQuestoes());
        return disciplinaRepository.save(disciplina);
    }

    public Optional<Disciplina> atualizar(int id, DisciplinaDTO dto) {
        Optional<Disciplina> disciplinaOpt = disciplinaRepository.findById(id);
        if (disciplinaOpt.isPresent()) {
            Disciplina disciplina = disciplinaOpt.get();
            disciplina.setNome(dto.getNome());
            disciplina.setQuestoes(dto.getQuestoes());
            disciplinaRepository.save(disciplina);
        }
        return disciplinaOpt;
    }

    public void excluir(int id) {
        Optional<Disciplina> disciplinaOpt = disciplinaRepository.findById(id);
        disciplinaOpt.ifPresent(disciplina -> disciplinaRepository.deleteById(id));
    }
}
