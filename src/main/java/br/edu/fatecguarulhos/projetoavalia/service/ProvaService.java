package br.edu.fatecguarulhos.projetoavalia.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.edu.fatecguarulhos.projetoavalia.dto.ProvaDTO;
import br.edu.fatecguarulhos.projetoavalia.dto.ProvaDisciplinaDTO;
import br.edu.fatecguarulhos.projetoavalia.dto.ProvaQuestaoDTO;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Curso;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;
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
import br.edu.fatecguarulhos.projetoavalia.repository.QuestaoRepository;
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
    private QuestaoRepository questaoRepository;
    
    @Autowired
    private QuestaoService questaoService;
    
    @Autowired
    private DisciplinaService disciplinaService;

    //-------------------- CONSULTAS --------------------
    
    public List<Prova> listarTodas() {
        return provaRepository.findAll();
    }
    
    public List<Prova> listarProvasVisiveis() {

        Professor usuario = getUsuarioLogado();

        // Caso seja professor comum → somente suas próprias provas
        if (!usuario.isCoordenador()) {
            return provaRepository.findByProfessorId(usuario.getId());
        }
        
        List<Curso> cursosCoordenador = usuario.getCursos();

        // Coordenador sem cursos → vê apenas suas próprias provas
        if (cursosCoordenador.isEmpty()) {
            return provaRepository.findByProfessorId(usuario.getId());
        }

        List<Prova> listaProvas = new ArrayList<>();

        // Para cada curso do coordenador, adicionar as provas do curso
        for (Curso curso : cursosCoordenador) {
            List<Prova> provasDoCurso = provaRepository.findByCursoId(curso.getId());
            listaProvas.addAll(provasDoCurso);
        }

        return listaProvas;
    }


    public Prova buscarPorId(int id) {
        return provaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prova não encontrada com id: " + id));
    }
    
    public Optional<Prova> buscarPorTituloEProfessorOptional(String titulo, int professorId) {
        return provaRepository.findByTituloAndProfessorId(titulo, professorId);
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
        Professor professor = this.getUsuarioLogado();
    	
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
    
    public Professor getUsuarioLogado() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado.");
        }

        // Verifica se o professor existe
        String email = auth.getName(); 
        Professor professor = professorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado para o usuário autenticado: " + email));

        return professor;
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
    
    public void removerQuestao(int provaId, int questaoId) {
        Prova prova = buscarPorId(provaId);
        Questao questao = questaoService.buscarPorId(questaoId);

        Optional<ProvaQuestao> provaQuestao = provaQuestaoRepository.findByProvaAndQuestao(prova, questao);

        if (provaQuestao.isEmpty()) {
            throw new RuntimeException("Associação Prova-Questão não encontrada para provaId=" 
                                       + provaId + " e questaoId=" + questaoId);
        }

        provaQuestaoRepository.delete(provaQuestao.get());   
    }
    
    public void removerTodasQuestoes(int provaId) {
        List<ProvaQuestao> lista = provaQuestaoRepository.findByProvaId(provaId);
        provaQuestaoRepository.deleteAll(lista);
    }
    
    public void gerarQuestoesAleatorias(int provaId, Map<String, String> quantidades) {

        Prova prova = provaRepository.findById(provaId).orElseThrow();

        // Exemplo de chave: "quantidades[12]"
        for (String chave : quantidades.keySet()) {
            String valor = quantidades.get(chave);

            // Ignorar valores inválidos
            if (valor == null || valor.isBlank()) continue;

            int quantidadeSolicitada;

            try {
                quantidadeSolicitada = Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                continue;
            }

            if (quantidadeSolicitada <= 0) continue;

            // Extrair ID da disciplina da string "quantidades[ID]"
            int disciplinaId = extrairIdDisciplina(chave);

            List<Questao> questoesDaDisciplina = 
                    questaoRepository.findByDisciplinaId(disciplinaId);

            if (questoesDaDisciplina.isEmpty())
                continue;

            // Limitar à quantidade existente
            int limite = Math.min(quantidadeSolicitada, questoesDaDisciplina.size());

            // Embaralhar para pegar aleatórias
            Collections.shuffle(questoesDaDisciplina);

            // Selecionar as primeiras N
            List<Questao> selecionadas = questoesDaDisciplina.subList(0, limite);

            // Criar links ProvaQuestao
            for (Questao q : selecionadas) {
                ProvaQuestao pq = new ProvaQuestao();
                pq.setProva(prova);
                pq.setQuestao(q);
                provaQuestaoRepository.save(pq);
            }
        }
    }

    private int extrairIdDisciplina(String chave) {
        // chave vem no formato: quantidades[10]
        try {
            return Integer.parseInt(chave.replace("quantidades[", "").replace("]", ""));
        } catch (Exception e) {
            return 0;
        }
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
    
	public void removerDisciplina(int provaId, int disciplinaId) {
		Prova prova = buscarPorId(provaId);
        Disciplina disciplina = disciplinaService.buscarPorId(disciplinaId);

        Optional<ProvaDisciplina> provaDisciplina = provaDisciplinaRepository.findByProvaAndDisciplina(prova, disciplina);

        if (provaDisciplina.isEmpty()) {
            throw new RuntimeException("Associação Prova-Questão não encontrada para provaId=" 
                                       + provaId + " e questaoId=" + disciplinaId);
        }

        provaDisciplinaRepository.delete(provaDisciplina.get()); 
		
	}

    public List<Questao> listarQuestoesDisponiveisPorProva(int provaId) {

        List<ProvaDisciplina> disciplinasProva = listarDisciplinasPorProva(provaId);

        if (disciplinasProva.isEmpty()) {
            return List.of();
        }

        List<ProvaQuestao> questoesDaProva = listarQuestoesPorProva(provaId);

        return questaoService.listarTodas().stream()
                .filter(questao -> disciplinasProva.stream()
                        .anyMatch(pd -> 
                            questao.getDisciplina().getId() == pd.getDisciplina().getId()
                        )
                )
 
                .filter(questao -> questoesDaProva.stream()
                        .noneMatch(q -> q.getQuestao().getId() == questao.getId())
                )
                .toList();
    }
	
}