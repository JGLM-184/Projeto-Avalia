package br.edu.fatecguarulhos.projetoavalia.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import br.edu.fatecguarulhos.projetoavalia.model.entity.TentativaSimulado;
import br.edu.fatecguarulhos.projetoavalia.service.CursoService;
import br.edu.fatecguarulhos.projetoavalia.service.DisciplinaService;
import br.edu.fatecguarulhos.projetoavalia.service.ProvaService;
import br.edu.fatecguarulhos.projetoavalia.service.QuestaoService;
import br.edu.fatecguarulhos.projetoavalia.service.TentativaSimuladoService;

@Controller
@RequestMapping("/provas")
public class ProvaController {

    @Autowired
    private ProvaService provaService;
    
    @Autowired
    private TentativaSimuladoService tentativaSimuladoService;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private DisciplinaService disciplinaService;
    
    @Autowired
    private QuestaoService questaoService;
    
    @GetMapping("/montar")
    public String montarProva(Model model) {
        Professor professorLogado = provaService.getUsuarioLogado();
        List<Curso> cursosProfessor = cursoService.listarCursosPorProfessor(professorLogado.getId());

        model.addAttribute("paginaAtiva", "montarProva");
        model.addAttribute("pageTitle", "Montar Prova");
        
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
	    
		model.addAttribute("paginaAtiva", "bancoProvas");
	    model.addAttribute("pageTitle", "Banco de Provas");
		
		model.addAttribute("provas", provaService.listarProvasVisiveis());
	
	    return "bancoProvas";
	}
	
	@GetMapping("/editar/{id}")
	public String editarProva(@PathVariable int id, @RequestParam(required = false) String modal,  Model model) {
		ProvaDTO provaDTO = new ProvaDTO(provaService.buscarPorId(id));
		Professor professorLogado = provaService.getUsuarioLogado();
	    Prova provaExistente = provaService.buscarPorId(id);
	    
	    // Verificar permissão - professor só pode editar suas próprias provas
	    if (!provaService.getUsuarioLogado().isCoordenador() && !(provaExistente.getProfessor().getId() == professorLogado.getId())) {
	        return "redirect:/provas/banco";
	    }
	
	    // Buscar questões já associadas à prova
	    List<ProvaQuestao> provaQuestoes = provaService.listarQuestoesPorProva(id);
	    
	    model.addAttribute("abrirModal", modal);
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
	    model.addAttribute("disciplinaIdAtual", 0);
	    model.addAttribute("pesquisaAtual", "");
	    model.addAttribute("tituloProvaAtual", provaExistente.getTitulo());
	    model.addAttribute("cursoIdAtual", provaExistente.getCurso().getId());
	    model.addAttribute("simuladoAtual", provaExistente.isSimulado());
        List<TentativaSimulado> tentativas = tentativaSimuladoService.buscarPorProva(id);
        model.addAttribute("tentativasSimulado", tentativas);
	    
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
        return "redirect:/provas/editar/" + id;
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
    
    @GetMapping("/editar/{id}/habilitar-simulado")
	public String habilitarSimulado(@PathVariable int id, Model model) {
    	provaService.habilitarSimulado(id);
	    return "redirect:/provas/editar/" + id;
    }
    
    @GetMapping("/editar/{id}/desabilitar-simulado")
	public String desabilitarSimulado(@PathVariable int id, Model model) {
    	provaService.desabilitarSimulado(id);
	    return "redirect:/provas/editar/" + id;
    }
    
    @PostMapping("/editar/{id}/simulado/excluir-todas")
    public String excluirTodas(@PathVariable int id) {
    	tentativaSimuladoService.excluirPorProva(id);
        return "redirect:/provas/editar/" + id + "?modal=resultados";
    }
    
    @PostMapping("/editar/{provaId}/simulado/excluir-tentativa/{tentativaId}")
    public String excluirTentativa(@PathVariable int provaId, @PathVariable int tentativaId) {
        tentativaSimuladoService.excluir(tentativaId);
        return "redirect:/provas/editar/" + provaId + "?modal=resultados";
    }
    
    
    @GetMapping("/excluir/{id}")
    public String excluirProva(@PathVariable int id) {
        provaService.excluir(id);
        return "redirect:/provas/banco";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhesProva(
            @PathVariable int id,
            @RequestParam(name = "embaralhar", required = false, defaultValue = "false") boolean embaralhar,
            Model model) {

        Prova prova = provaService.buscarPorId(id);

        if (prova == null) {
            return "redirect:/provas/banco";
        }

        model.addAttribute("paginaAtiva", "bancoProvas");
	    model.addAttribute("pageTitle", "Visualizar Prova");
        
        model.addAttribute("titulo", prova.getTitulo());
        model.addAttribute("curso", prova.getCurso());
        model.addAttribute("professor", prova.getProfessor());

        List<ProvaDisciplina> disciplinasProva = provaService.listarDisciplinasPorProva(id);

        List<Disciplina> disciplinas = disciplinasProva.stream()
                .map(ProvaDisciplina::getDisciplina)
                .filter(d -> d != null)
                .toList();

        model.addAttribute("disciplinas", disciplinas);

        List<ProvaQuestao> provaQuestoes = new ArrayList<>(provaService.listarQuestoesPorProva(id));

        if (embaralhar) {
            Collections.shuffle(provaQuestoes);
        }

        model.addAttribute("questoes", provaQuestoes);
        model.addAttribute("totalQuestoes", provaQuestoes.size());

        model.addAttribute("provaId", prova.getId());

        return "previaProva";
    }


}