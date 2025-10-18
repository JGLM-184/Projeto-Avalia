package br.edu.fatecguarulhos.projetoavalia.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.fatecguarulhos.projetoavalia.dto.ProfessorAtualizarDTO;
import br.edu.fatecguarulhos.projetoavalia.dto.ProfessorCadastroDTO;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Curso;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Professor;
import br.edu.fatecguarulhos.projetoavalia.repository.CursoRepository;
import br.edu.fatecguarulhos.projetoavalia.repository.DisciplinaRepository;
import br.edu.fatecguarulhos.projetoavalia.repository.ProfessorRepository;

@Service
public class ProfessorService {

    //----------------------------------- INSTÂNCIAS -----------------------------------//
    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    
    //----------------------------------- CONSULTAR -----------------------------------//

    //PARA LOGIN
    public Professor buscarPorEmail(String email) {
        return professorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
    
    //PARA LISTAR TODOS
    public List<Professor> listarTodos() {
        return professorRepository.findAll();
    }
    
    
        //----------------------------------- MÉTODOS DE CRUD -----------------------------------//
    
    //CADASTRO
    public Professor criarProfessor(ProfessorCadastroDTO dto) {
        if (professorRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Já existe um professor cadastrado com esse e-mail.");
        }
        
        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());

        Set<Disciplina> disciplinas = new HashSet<>();
        if (dto.getIdsDisciplinas() != null) {
            dto.getIdsDisciplinas().forEach(id -> {
                disciplinas.add(disciplinaRepository.findById(id).orElseThrow(() -> new RuntimeException("Disciplina com ID " + id + " não encontrada.")));
            });
        }

        Set<Curso> cursos = new HashSet<>();
        if (dto.getIdsCursos() != null) {
            dto.getIdsCursos().forEach(id -> {
                cursos.add(cursoRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Curso com ID " + id + " não encontrado.")));
            });
        }
        Professor professor = new Professor();
        professor.setNome(dto.getNome());
        professor.setEmail(dto.getEmail());
        professor.setRe(dto.getRe());
        professor.setSenha(senhaCriptografada);
        professor.setCoordenador(dto.isCoordenador());
        professor.setPrimeiroAcesso(true);
        professor.setDisciplinas(disciplinas);
        professor.setCursos(cursos);
        return professorRepository.save(professor);
    }
    
   //ALTERAR
    public Professor atualizarProfessor(int id, ProfessorAtualizarDTO dto) {
    	Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado."));

        //ATUALIZA DADOS BÁSICO
        professor.setNome(dto.getNome());
        professor.setEmail(dto.getEmail());
        professor.setRe(dto.getRe());
        professor.setCoordenador(dto.isCoordenador());

        //ATUALIZA DISCIPLINA
        if (dto.getIdsDisciplinas() != null) {
            Set<Disciplina> disciplinas = new HashSet<>();
            dto.getIdsDisciplinas().forEach(disciplinaId -> {
                Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                        .orElseThrow(() -> new RuntimeException("Disciplina com ID " + disciplinaId + " não encontrada."));
                disciplinas.add(disciplina);
            });
            professor.setDisciplinas(disciplinas);
        }

        //ATUALIZA CURSOS
        if (dto.getIdsCursos() != null) {
            Set<Curso> cursos = new HashSet<>();
            dto.getIdsCursos().forEach(cursoId -> {
                Curso curso = cursoRepository.findById(cursoId)
                        .orElseThrow(() -> new RuntimeException("Curso com ID " + cursoId + " não encontrado."));
                cursos.add(curso);
            });
            professor.setCursos(cursos);
        }

        return professorRepository.save(professor);
    }

 
    //EXCLUIR
    public void excluirProfessor(int id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado."));

        professorRepository.delete(professor);
    }

}
