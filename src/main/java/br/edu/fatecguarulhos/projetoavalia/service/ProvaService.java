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
            
            // Atualizar curso se fornecido
            if (provaDTO.getCurso() != null) {
                Curso curso = cursoRepository.findById(provaDTO.getCurso().getId())
                        .orElseThrow(() -> new RuntimeException("Curso não encontrado com id: " + provaDTO.getCurso().getId()));
                prova.setCurso(curso);
            }
            
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

    // MÉTODOS ADICIONAIS NECESSÁRIOS PARA A EDIÇÃO DE PROVA

    /**
     * Remove uma questão específica de uma prova
     */


    /**
     * Remove todas as questões de uma prova
     */
    public void removerTodasQuestoes(int provaId) {
        Prova prova = buscarPorId(provaId);
        provaQuestaoRepository.deleteByProva(prova);
    }

    /**
     * Remove uma disciplina específica de uma prova
     */
    public void removerDisciplina(int provaId, int disciplinaId) {
        Prova prova = buscarPorId(provaId);
        // Aqui você precisaria injetar o DisciplinaService ou DisciplinaRepository
        // Disciplina disciplina = disciplinaService.buscarPorId(disciplinaId);
        // provaDisciplinaRepository.deleteByProvaAndDisciplina(prova, disciplina);
    }

    /**
     * Atualiza uma prova com novas questões (substitui as antigas)
     */
    public Prova atualizarQuestoesDaProva(int provaId, List<Integer> questaoIds) {
        Prova prova = buscarPorId(provaId);
        
        // Remove questões antigas
        removerTodasQuestoes(provaId);
        
        // Adiciona novas questões
        for (Integer questaoId : questaoIds) {
            Questao questao = questaoService.buscarPorId(questaoId);
            ProvaQuestaoDTO provaQuestaoDTO = new ProvaQuestaoDTO();
            provaQuestaoDTO.setProva(prova);
            provaQuestaoDTO.setQuestao(questao);
            adicionarQuestao(provaQuestaoDTO);
        }
        
        return prova;
    }

    /**
     * Verifica se o usuário atual tem permissão para editar a prova
     */
    public boolean usuarioPodeEditarProva(int provaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;
        
        // Coordenador pode editar qualquer prova
        if (auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_COORDENADOR"))) {
            return true;
        }
        
        // Professor só pode editar suas próprias provas
        try {
            Prova prova = buscarPorId(provaId);
            String emailUsuario = auth.getName();
            return prova.getProfessor().getEmail().equals(emailUsuario);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Busca questões por lista de IDs
     */
    public List<Questao> buscarQuestoesPorIds(List<Integer> ids) {
        return questaoService.buscarQuestoesPorIds(ids);
    }

    /**
     * Lista todas as questões de uma prova (entidades Questao, não ProvaQuestao)
     */
    public List<Questao> listarQuestoesDaProva(int provaId) {
        List<ProvaQuestao> provaQuestoes = listarQuestoesPorProva(provaId);
        return provaQuestoes.stream()
                .map(ProvaQuestao::getQuestao)
                .toList();
    }
}