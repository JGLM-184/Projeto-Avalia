package br.edu.fatecguarulhos.projetoavalia.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.edu.fatecguarulhos.projetoavalia.dto.ProvaDTO;
import br.edu.fatecguarulhos.projetoavalia.dto.ProvaDisciplinaDTO;
import br.edu.fatecguarulhos.projetoavalia.dto.ProvaQuestaoDTO;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Curso;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Professor;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Prova;
import br.edu.fatecguarulhos.projetoavalia.model.entity.ProvaDisciplina;
import br.edu.fatecguarulhos.projetoavalia.model.entity.ProvaQuestao;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Questao;
import br.edu.fatecguarulhos.projetoavalia.repository.CursoRepository;
import br.edu.fatecguarulhos.projetoavalia.repository.ProfessorRepository;
import br.edu.fatecguarulhos.projetoavalia.repository.ProvaDisciplinaRepository;
import br.edu.fatecguarulhos.projetoavalia.repository.ProvaQuestaoRepository;
import br.edu.fatecguarulhos.projetoavalia.repository.ProvaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProvaService {

    @Autowired
    private ProvaRepository provaRepository;

    @Autowired
    private ProvaDisciplinaRepository provaDisciplinaRepository;

    @Autowired
    private ProvaQuestaoRepository provaQuestaoRepository;
    
    @Autowired
    private ProfessorRepository professorRepository;
    
    @Autowired
    private CursoRepository cursoRepository;
    
    @Autowired
    private QuestaoService questaoService;

    //-------------------- CONSULTAS --------------------
    
    public List<Prova> listarTodas() {
        return provaRepository.findAll();
    }

    public Prova buscarPorId(int id) {
        return provaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prova não encontrada com id: " + id));
    }
    
	public Prova buscarPorTitulo(String titulo) {
		return provaRepository.findByTitulo(titulo)
				.orElseThrow(() -> new RuntimeException("Prova não encontrada com título: " + titulo));
	}
    
    public List<ProvaQuestao> listarQuestoesPorProva(int provaId) {
        Prova prova = buscarPorId(provaId);
        return provaQuestaoRepository.findByProva(prova);
    }

    public List<ProvaDisciplina> listarDisciplinasPorProva(int provaId) {
        Prova prova = buscarPorId(provaId);
        return provaDisciplinaRepository.findByProva(prova);
    }
    
    //---------------------- CRUD ----------------------
    
    public Prova criar(ProvaDTO provaDTO) {
        
        // Obtém o usuário logado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado.");
        }

        // Verifica se o professor existe
        String email = auth.getName(); 
        Professor professor = professorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado para o usuário autenticado: " + email));

        // Verifica se o curso existe
        if (provaDTO.getCurso() == null || provaDTO.getCurso().getId() == 0) {
            throw new RuntimeException("Curso não informado.");
        }

        Curso curso = cursoRepository.findById(provaDTO.getCurso().getId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com id: " + provaDTO.getCurso().getId()));
        
        // Criação da prova
        Prova prova = new Prova();
        prova.setTitulo(provaDTO.getTitulo());
        prova.setDataCriacao(LocalDate.now());
        prova.setProfessor(professor);
        prova.setCurso(curso);
        prova.setSimulado(provaDTO.isSimulado());
        prova.setAtivo(provaDTO.isAtivo());
        
        return provaRepository.save(prova);
    }

    public Optional<Prova> atualizar(int id, ProvaDTO provaDTO) {
        Optional<Prova> provaOpt = provaRepository.findById(id);
        if (provaOpt.isPresent()) {
            Prova prova = provaOpt.get();

            prova.setTitulo(provaDTO.getTitulo());
            prova.setDataCriacao(provaDTO.getDataCriacao() != null ? provaDTO.getDataCriacao() : LocalDate.now());
            prova.setSimulado(provaDTO.isSimulado());
            
            provaRepository.save(prova);
        }
        
        return provaOpt;
    }

    public void excluir(int id) {
        Prova prova = buscarPorId(id);

        // Remove associações antes da exclusão
        provaQuestaoRepository.deleteByProva(prova);
        provaDisciplinaRepository.deleteByProva(prova);

        provaRepository.delete(prova);
    }

    public ProvaQuestao adicionarQuestao(ProvaQuestaoDTO provaQuestaoDTO) {
        ProvaQuestao provaQuestao = new ProvaQuestao();
        
        provaQuestao.setProva(provaQuestaoDTO.getProva());
        provaQuestao.setQuestao(provaQuestaoDTO.getQuestao());
        
        return provaQuestaoRepository.save(provaQuestao);
    }

    public ProvaDisciplina adicionarDisciplina(ProvaDisciplinaDTO provaDisciplinaDTO) {
        ProvaDisciplina provaDisciplina = new ProvaDisciplina();
        
        provaDisciplina.setProva(provaDisciplinaDTO.getProva());
        provaDisciplina.setDisciplina(provaDisciplinaDTO.getDisciplina());
        provaDisciplina.setQntQuestoes(provaDisciplinaDTO.getQntQuestoes());
        
        if(provaDisciplinaRepository.existsByProvaAndDisciplina(provaDisciplina.getProva(), provaDisciplina.getDisciplina())) {
            throw new RuntimeException("Disciplina já adicionada a essa prova!");
        }
        else {
        	return provaDisciplinaRepository.save(provaDisciplina);        	
        }
        
    }

    public List<Questao> listarQuestoesDisponiveisPorProva(int provaId) {
        List<ProvaDisciplina> disciplinasProva = listarDisciplinasPorProva(provaId);
        
        if (disciplinasProva.isEmpty()) {
            return List.of();
        }

        // Filtra questões cujas disciplinas estão na prova
        return questaoService.listarTodas().stream()
                .filter(questao -> disciplinasProva.stream()
                        .anyMatch(provaDisciplina -> questao.getDisciplina().getId() == provaDisciplina.getDisciplina().getId()))
                .toList();
    }

}
