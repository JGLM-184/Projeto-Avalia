package br.edu.fatecguarulhos.projetoavalia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fatecguarulhos.projetoavalia.dto.CursoDTO;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Curso;
import br.edu.fatecguarulhos.projetoavalia.repository.CursoRepository;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    public Curso buscarPorId(int id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com id: " + id));
    }

    public Curso salvar(CursoDTO dto) {
        Curso curso = new Curso();
        curso.setNome(dto.getNome());
        return cursoRepository.save(curso);
    }

    public Optional<Curso> atualizar(int id, CursoDTO dto) {
        Optional<Curso> cursoOpt = cursoRepository.findById(id);
        if (cursoOpt.isPresent()) {
            Curso curso = cursoOpt.get();
            curso.setNome(dto.getNome());
            cursoRepository.save(curso);
        }
        return cursoOpt;
    }

    public void excluir(int id) {
        Optional<Curso> cursoOpt = cursoRepository.findById(id);
        cursoOpt.ifPresent(curso -> cursoRepository.deleteById(id));
    }
}
