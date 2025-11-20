package br.edu.fatecguarulhos.projetoavalia.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.fatecguarulhos.projetoavalia.dto.ProvaDTO;
import br.edu.fatecguarulhos.projetoavalia.dto.ProvaDisciplinaDTO;
import br.edu.fatecguarulhos.projetoavalia.dto.ProvaQuestaoDTO;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Curso;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Professor;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Prova;
import br.edu.fatecguarulhos.projetoavalia.model.entity.ProvaDisciplina;
import br.edu.fatecguarulhos.projetoavalia.model.entity.ProvaQuestao;
import br.edu.fatecguarulhos.projetoavalia.repository.ProfessorRepository;
import br.edu.fatecguarulhos.projetoavalia.service.CursoService;
import br.edu.fatecguarulhos.projetoavalia.service.DisciplinaService;
import br.edu.fatecguarulhos.projetoavalia.service.ProvaService;
import br.edu.fatecguarulhos.projetoavalia.service.QuestaoService;

@Controller
@RequestMapping("/provas")
public class ProvaController {

    @Autowired
    private ProvaService provaService;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private DisciplinaService disciplinaService;
    
    @Autowired
    private ProfessorRepository professorRepository;
    
    @Autowired
    private QuestaoService questaoService;
    
    @GetMapping("/montar")
    public String montarProva(Model model) {
        Professor professorLogado = provaService.getUsuarioLogado();
        List<Curso> cursosProfessor = cursoService.listarCursosPorProfessor(professorLogado.getId());

        model.addAttribute("provaDTO", new ProvaDTO());
        model.addAttribute("cursos", cursosProfessor);

        return "montarProva";
    }
    
    @PostMapping("/salvar")
    public String salvarProva(@ModelAttribute ProvaDTO provaDTO) {

        int professorId = provaService.getUsuarioLogado().getId();

        Optional<Prova> existenteOpt = provaService.buscarPorTituloEProfessorOptional(
                provaDTO.getTitulo(), professorId);

        if (existenteOpt.isPresent()) {
            return "redirect:/provas/editar/" + existenteOpt.get().getId();
        }

        try {
            Prova nova = provaService.criar(provaDTO);
            return "redirect:/provas/editar/" + nova.getId();
        } catch (DataIntegrityViolationException ex) {
            Optional<Prova> existenteAfterRace = provaService.buscarPorTituloEProfessorOptional(
                    provaDTO.getTitulo(), professorId);

            if (existenteAfterRace.isPresent()) {
                return "redirect:/provas/editar/" + existenteAfterRace.get().getId();
            }

            throw ex;
        }
    }
    
	@GetMapping("/banco")
	public String bancoProvas(Model model) {
	      
		model.addAttribute("provas", provaService.listarProvasVisiveis());
	
	    return "bancoProvas";
	}
	
	@GetMapping("/editar/{id}")
	public String editarProva(@PathVariable int id, Model model) {
		ProvaDTO provaDTO = new ProvaDTO(provaService.buscarPorId(id));
		Professor professorLogado = provaService.getUsuarioLogado();
	    Prova provaExistente = provaService.buscarPorId(id);
	    
	    // Verificar permissão - professor só pode editar suas próprias provas
	    if (!provaService.getUsuarioLogado().isCoordenador() && !(provaExistente.getProfessor().getId() == professorLogado.getId())) {
	        return "redirect:/provas/banco";
	    }
	
	    // Buscar questões já associadas à prova
	    List<ProvaQuestao> provaQuestoes = provaService.listarQuestoesPorProva(id);
	    
	    List<Integer> questõesSelecionadas = provaQuestoes.stream()
	            .map(pq -> pq.getQuestao().getId())
	            .collect(Collectors.toList());

	    model.addAttribute("paginaAtiva", "bancoProvas");
	    model.addAttribute("pageTitle", "Editar Prova");
	    model.addAttribute("modoEdicao", true);
	    model.addAttribute("provaId", id);
	    
        model.addAttribute("cursos", cursoService.listarCursosPorProfessor(professorLogado.getId()));
        
        // Disciplinas já vinculadas à prova
        List<ProvaDisciplina> disciplinasProva = provaService.listarDisciplinasPorProva(id);
        model.addAttribute("disciplinasProva", disciplinasProva);

        List<Integer> idsDisciplinasProva = disciplinasProva.stream()
                .map(pd -> pd.getDisciplina().getId())
                .toList();

        List<Disciplina> disciplinasDisponiveis;

        // Coordenador: disciplinas do curso
        if (professorLogado.isCoordenador()) {
            disciplinasDisponiveis =
                disciplinaService.buscarPorCursoId(provaExistente.getCurso().getId());
        }
        // Professor comum: disciplinas do professor
        else {
            disciplinasDisponiveis =
                disciplinaService.listarDisciplinasPorProfessor(professorLogado.getId());
        }

        // Remover disciplinas que já estão na prova
        disciplinasDisponiveis = disciplinasDisponiveis.stream()
                .filter(d -> !idsDisciplinasProva.contains(d.getId()))
                .toList();
        
        if (!disciplinasDisponiveis.isEmpty()) {
        	model.addAttribute("disciplinas", disciplinasDisponiveis);
        }
        else {
        	model.addAttribute("disciplinas", List.of());
        }

	
	    // Carregar todas as questões disponíveis
        if (provaExistente.isSimulado()) {
            model.addAttribute("questoesDisponiveis", 
                    provaService.listarQuestoesDisponiveisSimulado(id));
        } else {
            model.addAttribute("questoesDisponiveis", 
                    provaService.listarQuestoesDisponiveisPorProva(id));
        }

        model.addAttribute("provaQuestoes", provaQuestoes);
        model.addAttribute("totalQuestoes", provaQuestoes.size());
        
	    // Preencher dados da prova existente
	    model.addAttribute("questoesSelecionadas", new HashSet<>(questõesSelecionadas));
	    model.addAttribute("disciplinaIdAtual", 0);
	    model.addAttribute("pesquisaAtual", "");
	    model.addAttribute("tituloProvaAtual", provaExistente.getTitulo());
	    model.addAttribute("cursoIdAtual", provaExistente.getCurso().getId());
	    model.addAttribute("simuladoAtual", provaExistente.isSimulado());
	    
	    model.addAttribute("isCoordenador", professorLogado.isCoordenador());
	    model.addAttribute("isProvaCoordenador", provaExistente.getProfessor().isCoordenador());
	    
	    model.addAttribute("provaDTO", provaDTO);
        model.addAttribute("provaDisciplinaDTO", new ProvaDisciplinaDTO());
        model.addAttribute("provaQuestaoDTO", new ProvaQuestaoDTO());
	
	    return "editarProva";
	}
    
    @PostMapping("/editar/{id}/adicionar-questao")
    public String adicionarQuestao(@PathVariable int id, @ModelAttribute ProvaQuestaoDTO provaQuestaoDTO) {
    	provaService.adicionarQuestao(provaQuestaoDTO);
    	return "redirect:/provas/editar/" + id + "?aba=disponiveis#abas";
    }
    
    @PostMapping("/editar/{provaId}/remover-questao/{questaoId}")
    public String removerQuestao(@PathVariable int provaId,
    							 @PathVariable int questaoId) {

        provaService.removerQuestao(provaId, questaoId);
        return "redirect:/provas/editar/" + provaId + "?aba=incluidas#abas";
    }
    
    @PostMapping("/editar/{id}/adicionar-disciplina")
    public String adicionarDisciplina(@PathVariable int id, @ModelAttribute ProvaDisciplinaDTO provaDisciplinaDTO) {
    	provaDisciplinaDTO.setProva(provaService.buscarPorId(id));
    	provaService.adicionarDisciplina(provaDisciplinaDTO);
    	return "redirect:/provas/editar/" + id;
    }
    
    @PostMapping("/editar/{provaId}/remover-disciplina/{disciplinaId}")
    public String removerDisciplina(@PathVariable int provaId,
    								@PathVariable int disciplinaId) {
    	provaService.removerDisciplina(provaId, disciplinaId);
    	return "redirect:/provas/editar/" + provaId;
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarProva(@PathVariable int id, @ModelAttribute ProvaDTO provaDTO) {
        provaService.atualizar(id, provaDTO);
        return "redirect:/provas/banco";
    }
    
    @PostMapping("/editar/{provaId}/gerar-aleatorias")
    public String gerarQuestoesAleatorias(
            @PathVariable int provaId,
            @RequestParam(required = false) Map<String, String> quantidades) {

        // Segurança: ignora se nada foi enviado
        if (quantidades == null || quantidades.isEmpty()) {
            return "redirect:/provas/editar/" + provaId + "?aba=incluidas#abas";
        }

        // 1) Remover todas as questões já associadas à prova
        provaService.removerTodasQuestoes(provaId);

        // 2) Gerar questões aleatórias conforme quantidades
        provaService.gerarQuestoesAleatorias(provaId, quantidades);

        // 3) Retornar para aba de questões incluídas
        return "redirect:/provas/editar/" + provaId + "?aba=incluidas#abas";
    }

    @GetMapping("/excluir/{id}")
    public String excluirProva(@PathVariable int id) {
        provaService.excluir(id);
        return "redirect:/provas/banco";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhesProva(@PathVariable int id, Model model) {
        model.addAttribute("provaDTO", new ProvaDTO(provaService.buscarPorId(id)));
        model.addAttribute("questoes", provaService.listarQuestoesPorProva(id));
        model.addAttribute("disciplinas", provaService.listarDisciplinasPorProva(id));
        return "previaProva";
    }
}


//    // ==================== TELAS DE TESTE ====================
//
//    // TELA DE CRIAÇÃO DE PROVA (testeProva.html)
//    @GetMapping("/criar")
//    public String criarProva(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Optional<Professor> professorOpt = professorRepository.findByEmail(auth.getName());
//        
//        // SEMPRE adicionar o provaDTO ao modelo
//        model.addAttribute("provaDTO", new ProvaDTO());
//        
//        if (professorOpt.isEmpty()) {
//            model.addAttribute("erro", "Professor não encontrado. Faça login novamente.");
//            // Adicionar cursos vazios para evitar erro no template
//            model.addAttribute("cursos", List.of());
//            model.addAttribute("provas", List.of());
//            return "testeProva";
//        }
//        
//        Professor professorLogado = professorOpt.get();
//
//        // Coordenador vê todos os cursos, professor vê apenas seus cursos
//        if (isCoordenador(auth)) {
//            model.addAttribute("cursos", cursoService.listarTodos());
//        } else {
//            model.addAttribute("cursos", professorLogado.getCursos());
//        }
//
//        // Lista todas as provas para a tabela
//        if (isCoordenador(auth)) {
//            model.addAttribute("provas", provaService.listarTodas());
//        } else {
//            model.addAttribute("provas", provaService.listarTodas().stream()
//                    .filter(prova -> prova.getProfessor().getEmail().equals(auth.getName()))
//                    .toList());
//        }
//
//        return "testeProva";
//    }
//
//    // BANCO DE PROVAS (bancoProvas.html)
//    @GetMapping("/banco")
//    public String bancoProvas(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        // Coordenador vê todas as provas, professor vê apenas as suas
//        if (isCoordenador(auth)) {
//            model.addAttribute("provas", provaService.listarTodas());
//        } else {
//            model.addAttribute("provas", provaService.listarTodas().stream()
//                    .filter(prova -> prova.getProfessor().getEmail().equals(auth.getName()))
//                    .toList());
//        }
//
//        return "bancoProvas";
//    }
//
//    // TELA DE EDIÇÃO DA PROVA (testeProvaEdicao.html)
//    @GetMapping("/editar/{id}")
//    public String editarProva(@PathVariable int id, Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        
//        ProvaDTO provaDTO = new ProvaDTO(provaService.buscarPorId(id));
//        
//        // Verificar permissão - professor só pode editar suas próprias provas
//        if (!isCoordenador(auth) && !provaDTO.getProfessor().getEmail().equals(auth.getName())) {
//            return "redirect:/provas/criar";
//        }
//
//        // Lista de disciplinas baseadas no curso da prova
//        model.addAttribute("disciplinas", disciplinaService.buscarPorCursoId(provaDTO.getCurso().getId()));
//        
//        // Lista de disciplinas e questões já associadas à prova
//        model.addAttribute("disciplinasProva", provaService.listarDisciplinasPorProva(id));
//        model.addAttribute("questoesProva", provaService.listarQuestoesPorProva(id));
//
//        // Lista de questões disponíveis (de disciplinas já associadas)
//        model.addAttribute("questoesDisponiveis", provaService.listarQuestoesDisponiveisPorProva(id));
//
//        // Dados para o formulário
//        model.addAttribute("provaDTO", provaDTO);
//        model.addAttribute("provaDisciplinaDTO", new ProvaDisciplinaDTO());
//        model.addAttribute("provaQuestaoDTO", new ProvaQuestaoDTO());
//
//        return "testeProvaEdicao";
//    }
//
//    @GetMapping("/detalhes/{id}")
//    public String detalhesProva(@PathVariable int id, Model model) {
//        model.addAttribute("provaDTO", new ProvaDTO(provaService.buscarPorId(id)));
//        model.addAttribute("questoes", provaService.listarQuestoesPorProva(id));
//        model.addAttribute("disciplinas", provaService.listarDisciplinasPorProva(id));
//        return "testeProvaDetalhes";
//    }
//
//    // ==================== TELAS OFICIAIS ====================
//
//    // TELA OFICIAL DE MONTAR PROVA COM QUESTÕES, PESQUISA E SELEÇÃO
//    @GetMapping("/montar")
//    public String montarProva(
//            @RequestParam(value = "disciplinaId", required = false) Integer disciplinaId,
//            @RequestParam(value = "pesquisa", required = false) String pesquisa,
//            @RequestParam(value = "questoesSelecionadas", required = false) List<Integer> questõesSelecionadas,
//            @RequestParam(value = "acao", required = false) String acao,
//            @RequestParam(value = "tituloProva", required = false) String tituloProva,
//            @RequestParam(value = "cursoId", required = false) Integer cursoId,
//            @RequestParam(value = "simulado", required = false) Boolean simulado,
//            @RequestParam(value = "limparCampos", required = false) Boolean limparCampos,
//            Model model) {
//        
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Professor professorLogado = professorRepository.findByEmail(auth.getName())
//                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
//
//        model.addAttribute("paginaAtiva", "montarProva");
//        model.addAttribute("pageTitle", "Montar Prova");
//        
//        // Carregar cursos
//        if (isCoordenador(auth)) {
//            model.addAttribute("cursos", cursoService.listarTodos());
//            model.addAttribute("disciplinas", disciplinaService.listarTodas());
//        } else {
//            model.addAttribute("cursos", professorLogado.getCursos());
//            List<Disciplina> disciplinasProfessor = new ArrayList<>();
//            for (Curso curso : professorLogado.getCursos()) {
//                disciplinasProfessor.addAll(disciplinaService.buscarPorCursoId(curso.getId()));
//            }
//            model.addAttribute("disciplinas", disciplinasProfessor);
//        }
//
//        // Se for para limpar campos (após salvar com sucesso)
//        if (limparCampos != null && limparCampos) {
//            System.out.println("=== LIMPANDO TODOS OS CAMPOS ===");
//            // Limpar todos os campos
//            tituloProva = "";
//            cursoId = null;
//            simulado = false;
//            questõesSelecionadas = new ArrayList<>();
//            disciplinaId = 0;
//            pesquisa = "";
//            
//            // Adicionar flag para o frontend limpar o localStorage também
//            model.addAttribute("limparCampos", true);
//            model.addAttribute("mensagem", "Prova salva com sucesso! Campos limpos para nova prova.");
//        }
//
//        // Carregar questões baseado nos filtros
//        List<Questao> questõesFiltradas = new ArrayList<>();
//        
//        if (disciplinaId != null && disciplinaId > 0) {
//            // Questões de uma disciplina específica
//            questõesFiltradas = questaoService.buscarPorDisciplina(disciplinaId);
//            model.addAttribute("disciplinaSelecionada", disciplinaService.buscarPorId(disciplinaId));
//        } else if (disciplinaId != null && disciplinaId == 0) {
//            // Todas as questões
//            if (isCoordenador(auth)) {
//                questõesFiltradas = questaoService.listarTodas();
//            } else {
//                questõesFiltradas = questaoService.listarPorCursosEDisciplinasDoProfessor(professorLogado);
//            }
//        } else {
//            // Primeiro acesso - mostrar todas as questões
//            if (isCoordenador(auth)) {
//                questõesFiltradas = questaoService.listarTodas();
//            } else {
//                questõesFiltradas = questaoService.listarPorCursosEDisciplinasDoProfessor(professorLogado);
//            }
//            disciplinaId = 0; // Mostrar "Todas as disciplinas"
//        }
//
//        // Aplicar filtro de pesquisa se existir
//        if (pesquisa != null && !pesquisa.trim().isEmpty()) {
//            String termoPesquisa = pesquisa.toLowerCase().trim();
//            List<Questao> questõesPesquisadas = new ArrayList<>();
//            
//            for (Questao questao : questõesFiltradas) {
//                // Pesquisar no enunciado, disciplina ou curso
//                if (questao.getEnunciado().toLowerCase().contains(termoPesquisa) ||
//                    questao.getDisciplina().getNome().toLowerCase().contains(termoPesquisa) ||
//                    questao.getCurso().getNome().toLowerCase().contains(termoPesquisa) ||
//                    questao.getAutor().getNome().toLowerCase().contains(termoPesquisa)) {
//                    questõesPesquisadas.add(questao);
//                }
//            }
//            questõesFiltradas = questõesPesquisadas;
//            model.addAttribute("termoPesquisa", pesquisa);
//        }
//
//        // Processar ações dos botões
//        Set<Integer> questõesSelecionadasSet = new HashSet<>();
//        if (questõesSelecionadas != null) {
//            questõesSelecionadasSet.addAll(questõesSelecionadas);
//        }
//
//        // Aplicar ações
//        if (acao != null) {
//            switch (acao) {
//                case "aleatorias":
//                    if (!questõesFiltradas.isEmpty()) {
//                        questõesSelecionadasSet = escolherQuestoesAleatorias(questõesFiltradas, questõesSelecionadasSet);
//                        model.addAttribute("mensagem", "Questões escolhidas aleatoriamente!");
//                        
//                        // Salvar a ordem das questões selecionadas para a prévia
//                        salvarOrdemQuestoesSelecionadas(questõesSelecionadasSet, questõesFiltradas);
//                    }
//                    break;
//                case "embaralhar":
//                    if (!questõesFiltradas.isEmpty()) {
//                        // Embaralhar a lista de questões filtradas
//                        Collections.shuffle(questõesFiltradas);
//                        model.addAttribute("mensagem", "Questões embaralhadas!");
//                        
//                        // Salvar a ordem embaralhada para a prévia
//                        salvarOrdemEmbaralhada(questõesFiltradas);
//                    }
//                    break;
//                case "embaralharAlternativas":
//                    model.addAttribute("mensagem", "Alternativas embaralhadas! (Funcionalidade em desenvolvimento)");
//                    break;
//                case "salvar":
//                    if (!questõesSelecionadasSet.isEmpty()) {
//                        // SALVAR PROVA NO BANCO DE DADOS
//                        boolean salvou = salvarProvaComQuestoes(tituloProva, cursoId, simulado, questõesSelecionadasSet, professorLogado);
//                        if (salvou) {
//                            // Redirecionar para limpar todos os campos
//                            return "redirect:/provas/montar?limparCampos=true&mensagem=Prova+salva+com+sucesso";
//                        } else {
//                            model.addAttribute("erro", "Erro ao salvar prova. Verifique se preencheu título e curso.");
//                        }
//                    } else {
//                        model.addAttribute("erro", "Selecione pelo menos uma questão para salvar a prova!");
//                    }
//                    break;
//                case "pdf":
//                    if (!questõesSelecionadasSet.isEmpty()) {
//                        model.addAttribute("mensagem", "PDF gerado com " + questõesSelecionadasSet.size() + " questões!");
//                    } else {
//                        model.addAttribute("erro", "Selecione pelo menos uma questão para gerar o PDF!");
//                    }
//                    break;
//            }
//        }
//
//        // MANTER TODOS OS PARÂMETROS APÓS A AÇÃO - CORREÇÃO DO PROBLEMA
//        model.addAttribute("questoesSelecionadas", questõesSelecionadasSet);
//        model.addAttribute("questoes", questõesFiltradas);
//        
//        // Adicionar os parâmetros atuais para manter o estado da página
//        model.addAttribute("disciplinaIdAtual", disciplinaId != null ? disciplinaId : 0);
//        model.addAttribute("pesquisaAtual", pesquisa != null ? pesquisa : "");
//        model.addAttribute("tituloProvaAtual", tituloProva != null ? tituloProva : "");
//        model.addAttribute("cursoIdAtual", cursoId != null ? cursoId : "");
//        model.addAttribute("simuladoAtual", simulado != null ? simulado : false);
//
//        return "montarProva";
//    }
//
//    // NOVA TELA PARA EDITAR PROVA - IDÊNTICA À MONTAR PROVA MAS PARA EDIÇÃO
//    @GetMapping("/editar-prova/{id}")
//    public String editarProvaCompleta(@PathVariable int id, Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        
//        // Buscar a prova existente
//        Prova provaExistente = provaService.buscarPorId(id);
//        
//        // Verificar permissão - professor só pode editar suas próprias provas
//        if (!isCoordenador(auth) && !provaExistente.getProfessor().getEmail().equals(auth.getName())) {
//            return "redirect:/provas/banco";
//        }
//
//        // Buscar questões já associadas à prova
//        List<ProvaQuestao> provaQuestoes = provaService.listarQuestoesPorProva(id);
//        List<Integer> questõesSelecionadas = provaQuestoes.stream()
//                .map(pq -> pq.getQuestao().getId())
//                .collect(Collectors.toList());
//
//        Professor professorLogado = professorRepository.findByEmail(auth.getName())
//                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
//
//        model.addAttribute("paginaAtiva", "bancoProvas");
//        model.addAttribute("pageTitle", "Editar Prova");
//        model.addAttribute("modoEdicao", true);
//        model.addAttribute("provaId", id);
//        
//        // Carregar cursos
//        if (isCoordenador(auth)) {
//            model.addAttribute("cursos", cursoService.listarTodos());
//            model.addAttribute("disciplinas", disciplinaService.listarTodas());
//        } else {
//            model.addAttribute("cursos", professorLogado.getCursos());
//            List<Disciplina> disciplinasProfessor = new ArrayList<>();
//            for (Curso curso : professorLogado.getCursos()) {
//                disciplinasProfessor.addAll(disciplinaService.buscarPorCursoId(curso.getId()));
//            }
//            model.addAttribute("disciplinas", disciplinasProfessor);
//        }
//
//        // Carregar todas as questões disponíveis
//        List<Questao> questõesFiltradas;
//        if (isCoordenador(auth)) {
//            questõesFiltradas = questaoService.listarTodas();
//        } else {
//            questõesFiltradas = questaoService.listarPorCursosEDisciplinasDoProfessor(professorLogado);
//        }
//
//        // Preencher dados da prova existente
//        model.addAttribute("questoesSelecionadas", new HashSet<>(questõesSelecionadas));
//        model.addAttribute("questoes", questõesFiltradas);
//        model.addAttribute("disciplinaIdAtual", 0);
//        model.addAttribute("pesquisaAtual", "");
//        model.addAttribute("tituloProvaAtual", provaExistente.getTitulo());
//        model.addAttribute("cursoIdAtual", provaExistente.getCurso().getId());
//        model.addAttribute("simuladoAtual", provaExistente.isSimulado());
//
//        return "editarProva";
//    }
//
//    // API PARA ATUALIZAR PROVA EXISTENTE
//    @PostMapping("/atualizar-prova/{id}")
//    public String atualizarProvaCompleta(
//            @PathVariable int id,
//            @RequestParam(value = "questoesSelecionadas", required = false) List<Integer> questõesSelecionadas,
//            @RequestParam(value = "tituloProva", required = false) String tituloProva,
//            @RequestParam(value = "cursoId", required = false) Integer cursoId,
//            @RequestParam(value = "simulado", required = false) Boolean simulado,
//            Model model) {
//        
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Prova provaExistente = provaService.buscarPorId(id);
//        
//        // Verificar permissão
//        if (!isCoordenador(auth) && !provaExistente.getProfessor().getEmail().equals(auth.getName())) {
//            return "redirect:/provas/banco";
//        }
//
//        try {
//            // Atualizar dados básicos da prova
//            if (tituloProva != null && !tituloProva.trim().isEmpty()) {
//                provaExistente.setTitulo(tituloProva.trim());
//            }
//            
//            if (cursoId != null) {
//                Curso curso = cursoService.buscarPorId(cursoId);
//                if (curso != null) {
//                    provaExistente.setCurso(curso);
//                }
//            }
//            
//            if (simulado != null) {
//                provaExistente.setSimulado(simulado);
//            }
//
//            // Atualizar questões da prova
//            if (questõesSelecionadas != null && !questõesSelecionadas.isEmpty()) {
//                // Remover questões antigas
//                List<ProvaQuestao> questaoAntigas = provaService.listarQuestoesPorProva(id);
//                for (ProvaQuestao provaQuestao : questaoAntigas) {
//                    // Aqui você precisaria de um método para remover questões da prova
//                    // provaService.removerQuestao(provaQuestao);
//                }
//
//                // Adicionar novas questões
//                for (Integer questaoId : questõesSelecionadas) {
//                    try {
//                        Questao questao = questaoService.buscarPorId(questaoId);
//                        ProvaQuestaoDTO provaQuestaoDTO = new ProvaQuestaoDTO();
//                        provaQuestaoDTO.setProva(provaExistente);
//                        provaQuestaoDTO.setQuestao(questao);
//                        provaService.adicionarQuestao(provaQuestaoDTO);
//                    } catch (Exception e) {
//                        System.err.println("Erro ao adicionar questão " + questaoId + ": " + e.getMessage());
//                    }
//                }
//            }
//
//            // Salvar prova atualizada
//            provaService.atualizar(id, new ProvaDTO(provaExistente));
//            
//            model.addAttribute("mensagem", "Prova atualizada com sucesso!");
//            return "redirect:/provas/banco?sucesso=Prova+atualizada+com+sucesso";
//            
//        } catch (Exception e) {
//            System.err.println("Erro ao atualizar prova: " + e.getMessage());
//            model.addAttribute("erro", "Erro ao atualizar prova: " + e.getMessage());
//            return "redirect:/provas/editar-prova/" + id + "?erro=Erro+ao+atualizar+prova";
//        }
//    }
//
//    // Método para salvar prova no banco de dados
//    private boolean salvarProvaComQuestoes(String tituloProva, Integer cursoId, Boolean simulado, 
//                                          Set<Integer> questõesSelecionadas, Professor professor) {
//        try {
//            // Validar dados obrigatórios
//            if (tituloProva == null || tituloProva.trim().isEmpty() || cursoId == null) {
//                return false;
//            }
//
//            // Buscar curso
//            Curso curso = cursoService.buscarPorId(cursoId);
//            if (curso == null) {
//                return false;
//            }
//
//            // Criar DTO da prova
//            ProvaDTO provaDTO = new ProvaDTO();
//            provaDTO.setTitulo(tituloProva.trim());
//            provaDTO.setCurso(curso);
//            provaDTO.setProfessor(professor);
//            provaDTO.setSimulado(simulado != null ? simulado : false);
//            provaDTO.setAtivo(true);
//
//            // Criar prova
//            Prova provaSalva = provaService.criar(provaDTO);
//
//            // Adicionar questões selecionadas à prova
//            for (Integer questaoId : questõesSelecionadas) {
//                try {
//                    Questao questao = questaoService.buscarPorId(questaoId);
//                    ProvaQuestaoDTO provaQuestaoDTO = new ProvaQuestaoDTO();
//                    provaQuestaoDTO.setProva(provaSalva);
//                    provaQuestaoDTO.setQuestao(questao);
//                    provaService.adicionarQuestao(provaQuestaoDTO);
//                } catch (Exception e) {
//                    System.err.println("Erro ao adicionar questão " + questaoId + ": " + e.getMessage());
//                }
//            }
//
//            System.out.println("Prova salva com sucesso! ID: " + provaSalva.getId() + ", Título: " + provaSalva.getTitulo());
//            return true;
//        } catch (Exception e) {
//            System.err.println("Erro ao salvar prova: " + e.getMessage());
//            return false;
//        }
//    }
//
//    // Método para escolher questões aleatórias
//    private Set<Integer> escolherQuestoesAleatorias(List<Questao> questõesDisponiveis, Set<Integer> selecionadasAtuais) {
//        Set<Integer> novasSelecoes = new HashSet<>(selecionadasAtuais);
//        
//        if (questõesDisponiveis.isEmpty()) {
//            return novasSelecoes;
//        }
//
//        // Escolher até 5 questões aleatórias
//        List<Questao> questõesAleatorias = new ArrayList<>(questõesDisponiveis);
//        Collections.shuffle(questõesAleatorias);
//        
//        int quantidade = Math.min(5, questõesAleatorias.size());
//        for (int i = 0; i < quantidade; i++) {
//            novasSelecoes.add(questõesAleatorias.get(i).getId());
//        }
//        
//        return novasSelecoes;
//    }
//
//    // Método para salvar a ordem das questões selecionadas (para a prévia)
//    private void salvarOrdemQuestoesSelecionadas(Set<Integer> questõesSelecionadas, List<Questao> questõesFiltradas) {
//        try {
//            // Criar uma lista ordenada das questões selecionadas
//            List<Integer> ordemQuestoes = new ArrayList<>();
//            
//            // Manter a ordem das questões filtradas, mas apenas as selecionadas
//            for (Questao questao : questõesFiltradas) {
//                if (questõesSelecionadas.contains(questao.getId())) {
//                    ordemQuestoes.add(questao.getId());
//                }
//            }
//            
//            // Aqui você poderia salvar esta ordem no banco de dados ou em sessão
//            // Por enquanto, vamos apenas logar para debug
//            System.out.println("Ordem das questões selecionadas: " + ordemQuestoes);
//            
//        } catch (Exception e) {
//            System.err.println("Erro ao salvar ordem das questões: " + e.getMessage());
//        }
//    }
//
//    // Método para salvar a ordem embaralhada (para a prévia)
//    private void salvarOrdemEmbaralhada(List<Questao> questõesEmbaralhadas) {
//        try {
//            // Extrair apenas os IDs na ordem embaralhada
//            List<Integer> ordemQuestoes = questõesEmbaralhadas.stream()
//                    .map(Questao::getId)
//                    .collect(Collectors.toList());
//            
//            // Aqui você poderia salvar esta ordem no banco de dados ou em sessão
//            // Por enquanto, vamos apenas logar para debug
//            System.out.println("Ordem embaralhada das questões: " + ordemQuestoes);
//            
//        } catch (Exception e) {
//            System.err.println("Erro ao salvar ordem embaralhada: " + e.getMessage());
//        }
//    }
//
//    // TELA DE EDITAR CABEÇALHO
//    @GetMapping("/editar-cabecalho")
//    public String editarCabecalho(Model model) {
//        model.addAttribute("paginaAtiva", "montarProva");
//        model.addAttribute("pageTitle", "Editar Cabeçalho");
//        return "editarCabecalho";
//    }
//
//    // TELA DE PRÉVIA DA PROVA - CORRIGIDA PARA MÚLTIPLAS QUESTÕES
//    @GetMapping("/previa")
//    public String previaProva(
//            @RequestParam(value = "questoesSelecionadas", required = false) List<Integer> questõesSelecionadas,
//            Model model) {
//        
//        System.out.println("=== ACESSANDO PRÉVIA DA PROVA ===");
//        System.out.println("Questões recebidas como parâmetro: " + questõesSelecionadas);
//        
//        model.addAttribute("paginaAtiva", "montarProva");
//        model.addAttribute("pageTitle", "Prévia da Prova");
//        
//        // Se questões foram passadas como parâmetro, adicionar ao modelo
//        if (questõesSelecionadas != null && !questõesSelecionadas.isEmpty()) {
//            model.addAttribute("questoesSelecionadasParam", questõesSelecionadas);
//            System.out.println("Questões adicionadas ao modelo: " + questõesSelecionadas.size());
//        } else {
//            System.out.println("Nenhuma questão recebida como parâmetro");
//        }
//        
//        return "previaProva";
//    }
//
//    // ==================== APIs ====================
//
//    // API PARA SALVAR PROVA
//    @PostMapping("/salvar")
//    public String salvarProva(@ModelAttribute ProvaDTO provaDTO, Model model) {
//        try {
//            provaService.criar(provaDTO);
//            return "redirect:/provas/criar?sucesso";
//        } catch (RuntimeException e) {
//            model.addAttribute("erro", "Erro ao criar prova: " + e.getMessage());
//            return criarProva(model);
//        }
//    }
//
//    // API PARA ADICIONAR DISCIPLINA À PROVA
//    @PostMapping("/editar/{id}/adicionar-disciplina")
//    public String adicionarDisciplina(@PathVariable int id, @ModelAttribute ProvaDisciplinaDTO provaDisciplinaDTO) {
//        provaDisciplinaDTO.setProva(provaService.buscarPorId(id));
//        provaService.adicionarDisciplina(provaDisciplinaDTO);
//        return "redirect:/provas/editar/" + id;
//    }
//
//    // API PARA ADICIONAR QUESTÃO À PROVA
//    @PostMapping("/editar/{id}/adicionar-questao")
//    public String adicionarQuestao(@PathVariable int id, @ModelAttribute ProvaQuestaoDTO provaQuestaoDTO) {
//        provaService.adicionarQuestao(provaQuestaoDTO);
//        return "redirect:/provas/editar/" + id;
//    }
//
//    // API PARA ATUALIZAR PROVA
//    @PostMapping("/atualizar/{id}")
//    public String atualizarProva(@PathVariable int id, @ModelAttribute ProvaDTO provaDTO) {
//        provaService.atualizar(id, provaDTO);
//        return "redirect:/provas/criar";
//    }
//
//    // API PARA EXCLUIR PROVA
//    @GetMapping("/excluir/{id}")
//    public String excluirProva(@PathVariable int id) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        ProvaDTO provaDTO = new ProvaDTO(provaService.buscarPorId(id));
//        
//        // Verificar permissão
//        if (!isCoordenador(auth) && !provaDTO.getProfessor().getEmail().equals(auth.getName())) {
//            return "redirect:/provas/criar";
//        }
//
//        provaService.excluir(id);
//        return "redirect:/provas/criar";
//    }
//
//    // API PARA BUSCAR DISCIPLINAS POR CURSO (usada no frontend)
//    @ResponseBody
//    @GetMapping("/api/disciplinas/por-curso/{cursoId}")
//    public List<Disciplina> listarDisciplinasPorCurso(@PathVariable int cursoId) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Optional<Professor> professorOpt = professorRepository.findByEmail(auth.getName());
//        
//        if (professorOpt.isEmpty()) {
//            return List.of();
//        }
//        
//        Professor professorLogado = professorOpt.get();
//
//        List<Disciplina> disciplinas = disciplinaService.buscarPorCursoId(cursoId);
//        
//        // Professor só vê suas disciplinas, coordenador vê todas
//        if (!isCoordenador(auth)) {
//            disciplinas = disciplinas.stream()
//                    .filter(disciplina -> professorLogado.getDisciplinas().contains(disciplina))
//                    .toList();
//        }
//        
//        return disciplinas;
//    }
//
//    // API PARA BUSCAR QUESTÕES POR DISCIPLINA (usada no frontend oficial)
//    @ResponseBody
//    @GetMapping("/api/questoes/por-disciplina/{disciplinaId}")
//    public List<Questao> listarQuestoesPorDisciplina(@PathVariable int disciplinaId) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Optional<Professor> professorOpt = professorRepository.findByEmail(auth.getName());
//        
//        if (professorOpt.isEmpty()) {
//            return List.of();
//        }
//        
//        // Verificar se o professor tem acesso à disciplina
//        Professor professorLogado = professorOpt.get();
//        if (!isCoordenador(auth)) {
//            boolean temAcesso = professorLogado.getDisciplinas().stream()
//                    .anyMatch(disciplina -> disciplina.getId() == disciplinaId);
//            if (!temAcesso) {
//                return List.of();
//            }
//        }
//        
//        return questaoService.buscarPorDisciplina(disciplinaId);
//    }
//
//    // API PARA BUSCAR TODAS AS QUESTÕES (usada no frontend oficial)
//    @ResponseBody
//    @GetMapping("/api/questoes/todas")
//    public List<Questao> listarTodasQuestoes() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Optional<Professor> professorOpt = professorRepository.findByEmail(auth.getName());
//        
//        if (professorOpt.isEmpty()) {
//            return List.of();
//        }
//        
//        Professor professorLogado = professorOpt.get();
//
//        // Coordenador vê todas as questões, professor vê apenas as suas disciplinas
//        if (isCoordenador(auth)) {
//            return questaoService.listarTodas();
//        } else {
//            return questaoService.listarPorCursosEDisciplinasDoProfessor(professorLogado);
//        }
//    }
//
//    // API PARA BUSCAR QUESTÕES POR IDs (usada na prévia) - CORRIGIDA
//    @ResponseBody
//    @GetMapping("/api/questoes/por-ids")
//    public List<Questao> listarQuestoesPorIds(@RequestParam List<Integer> ids) {
//        System.out.println("=== BUSCANDO QUESTÕES POR IDs NO BACKEND ===");
//        System.out.println("IDs recebidos: " + ids);
//        
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Optional<Professor> professorOpt = professorRepository.findByEmail(auth.getName());
//        
//        if (professorOpt.isEmpty()) {
//            System.out.println("Professor não encontrado");
//            return List.of();
//        }
//        
//        Professor professorLogado = professorOpt.get();
//        List<Questao> todasQuestoes;
//        
//        // Coordenador vê todas as questões, professor vê apenas as suas disciplinas
//        if (isCoordenador(auth)) {
//            todasQuestoes = questaoService.listarTodas();
//        } else {
//            todasQuestoes = questaoService.listarPorCursosEDisciplinasDoProfessor(professorLogado);
//        }
//        
//        // Filtrar apenas as questões cujos IDs foram solicitados
//        List<Questao> questõesFiltradas = todasQuestoes.stream()
//                .filter(questao -> ids.contains(questao.getId()))
//                .collect(Collectors.toList());
//        
//        System.out.println("Total de questões disponíveis: " + todasQuestoes.size());
//        System.out.println("Questões encontradas pelos IDs: " + questõesFiltradas.size());
//        
//        // Log detalhado das questões encontradas
//        for (Questao questao : questõesFiltradas) {
//            System.out.println(" - Questão ID: " + questao.getId() + " | Enunciado: " + 
//                (questao.getEnunciado().length() > 50 ? 
//                 questao.getEnunciado().substring(0, 50) + "..." : questao.getEnunciado()));
//        }
//        
//        return questõesFiltradas;
//    }
//
//    // API PARA BUSCAR PROVAS POR PROFESSOR (opcional)
//    @ResponseBody
//    @GetMapping("/api/provas/professor/{professorId}")
//    public List<ProvaDTO> listarProvasPorProfessor(@PathVariable int professorId) {
//        return provaService.listarTodas().stream()
//                .filter(prova -> prova.getProfessor().getId() == professorId)
//                .map(ProvaDTO::new)
//                .toList();
//    }
//
//    private boolean isCoordenador(Authentication authentication) {
//        return authentication.getAuthorities().stream()
//            .anyMatch(a -> a.getAuthority().equals("ROLE_COORDENADOR"));
//    }
//}