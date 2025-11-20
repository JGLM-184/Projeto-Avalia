package br.edu.fatecguarulhos.projetoavalia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.fatecguarulhos.projetoavalia.dto.QuestaoDTO;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Professor;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Questao;
import br.edu.fatecguarulhos.projetoavalia.repository.ProfessorRepository;
import br.edu.fatecguarulhos.projetoavalia.service.CursoService;
import br.edu.fatecguarulhos.projetoavalia.service.DisciplinaService;
import br.edu.fatecguarulhos.projetoavalia.service.ProfessorService;
import br.edu.fatecguarulhos.projetoavalia.service.QuestaoService;

@Controller
@RequestMapping("/questao")
public class QuestaoController {
	/* NÃO MEXER NESTA CLASSE - NÃO MEXER NESTA CLASSE - NÃO MEXER NESTA CLASSE - NÃO MEXER NESTA CLASSE*/
	/* NÃO MEXER NESTA CLASSE - NÃO MEXER NESTA CLASSE - NÃO MEXER NESTA CLASSE - NÃO MEXER NESTA CLASSE*/
	
	@Autowired
    private ProfessorRepository professorRepository;
	
	@Autowired
    private ProfessorService professorService;
	
	@Autowired
    private CursoService cursoService;

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private QuestaoService questaoService;
    
	//Tela de montar questão
    @GetMapping("/cadastrar")
    public String cadastroQuestao(Model model) {

    	//Pega o autor logado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Professor autorLogado = professorRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        model.addAttribute("paginaAtiva", "cadastroQuestao");
        model.addAttribute("pageTitle", "Cadastro de Questão");
        
        model.addAttribute("autorLogado", autorLogado); // Envia para a tela
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("disciplinas", disciplinaService.listarTodas());
        model.addAttribute("questaoDTO", new QuestaoDTO(5));
        
        return "cadastroQuestao";
    }
    
    @PostMapping("/cadastrar")
    public String salvarQuestao(@ModelAttribute QuestaoDTO dto,
                                @RequestParam(value = "file", required = false) MultipartFile file,
                                RedirectAttributes redirectAttributes) {
        try {
            if (file != null && !file.isEmpty()) {
                questaoService.salvar(dto, file);
            } else {
                questaoService.salvar(dto);
            }
            redirectAttributes.addFlashAttribute("sucesso", true);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", true);
        }

        return "redirect:/questao/cadastrar";
    }

    
    //TELA DE LISTAR QUESTÕES
    @GetMapping("/banco")
    public String bancoDeQuestoes(@RequestParam(value = "meus", required = false) Boolean apenasMinhas,
                                  Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Professor professorLogado = professorService.buscarPorEmail(auth.getName());

        model.addAttribute("paginaAtiva", "bancoQuestoes");
        model.addAttribute("pageTitle", "Banco de Questões");
        model.addAttribute("professorLogado", professorLogado);

        List<Questao> questoes;

        // Se o checkbox estiver marcado, mostrar apenas as do autor logado
        if (Boolean.TRUE.equals(apenasMinhas)) {
            questoes = questaoService.buscarPorAutor(professorLogado.getId());
            // ainda restringe ao(s) curso(s) do professor (caso queira manter)
            questoes = questoes.stream()
                    .filter(q -> professorLogado.getCursos().contains(q.getCurso()))
                    .toList();
        } else {
            // mostra todas dentro dos cursos/disciplina do professor
            questoes = questaoService.listarPorCursosEDisciplinasDoProfessor(professorLogado);
        }

        model.addAttribute("questoes", questoes);
        model.addAttribute("professores", professorService.listarTodos());
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("disciplinas", disciplinaService.listarTodas());
        model.addAttribute("mostrarApenasMinhasQuestoes", apenasMinhas);

        return "bancoDeQuestoes";
    }
    
    @GetMapping("/pesquisa")
    public String pesquisarQuestoes(@RequestParam("nome") String termo, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Professor professorLogado = professorService.buscarPorEmail(auth.getName());

        List<Questao> questoes = questaoService.pesquisar(termo);

      // Restringir conforme os cursos do professor:
        questoes = questoes.stream()
        .filter(q -> professorLogado.getCursos().contains(q.getCurso()))
        .toList();
        
     // Se a lista estiver vazia, adiciona um atributo de mensagem
        if (questoes.isEmpty()) {
            model.addAttribute("mensagem", "Nenhuma questão encontrada com este enunciado.");
        }

        model.addAttribute("paginaAtiva", "bancoQuestoes");
        model.addAttribute("pageTitle", "Banco de Questões");
        model.addAttribute("professorLogado", professorLogado);
        model.addAttribute("questoes", questoes);
        model.addAttribute("professores", professorService.listarTodos());
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("disciplinas", disciplinaService.listarTodas());
        model.addAttribute("termoPesquisa", termo);

        return "bancoDeQuestoes";
    }
    

    
  // SUGESTÃO DE PESQUISA DE QUESTÃO POR ENUNCIADO, DISCIPLINA OU CURSO
    @GetMapping("/sugestoes")
    @ResponseBody
    public List<String> obterSugestoes(@RequestParam("termo") String termo) {
        return questaoService.buscarSugestoes(termo);
    }
    
    

  //TELA DE GERENCIAR QUESTÃO
    @GetMapping("/gerenciar")
    public String gerenciarQuestao(Model model) {
        return "gerenciarQuestao";
    }
    
    
    @GetMapping("/gerenciar/editar/{id}")
    public String editarQuestao(@PathVariable int id, Model model) {   	
        model.addAttribute("questaoDTO", new QuestaoDTO(questaoService.buscarPorId(id)));
        model.addAttribute("professores", professorService.listarTodos());
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("qntAlternativas", questaoService.contarAlternativasPorId(id));

        // Busca as disciplinas com base no curso da questão
        model.addAttribute("disciplinas", disciplinaService.buscarPorCursoQuestaoId(id));
        
        model.addAttribute("isEdicaoQuestao", true);
        
        return "gerenciarQuestoes"; // ou outra view específica de edição
    }

    
    @PostMapping("/gerenciar/atualizar/{id}")
    public String atualizarQuestao(@PathVariable int id,
                                   @ModelAttribute QuestaoDTO dto,
                                   @RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            questaoService.atualizar(id, dto, file);
        } else {
            questaoService.atualizar(id, dto);
        }
        return "redirect:/questao/banco";
    }
    

    @GetMapping("/gerenciar/excluir/{id}")
    public String excluirQuestao(@PathVariable int id) {
        questaoService.excluir(id);
        return "redirect:/questao/banco";
    }
    
    @ResponseBody
    @GetMapping("/disciplinas/api/por-curso/{id}")
    public List<Disciplina> listarDisciplinasPorCurso(@PathVariable int id) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Professor professorLogado = professorService.buscarPorEmail(auth.getName());

        List<Disciplina> disciplinasDisponiveis; 
        
        if (professorLogado.isCoordenador()) {
            disciplinasDisponiveis =
                disciplinaService.buscarPorCursoId(id);
        }
        // Professor comum: disciplinas do professor
        else {
            disciplinasDisponiveis =
                disciplinaService.listarDisciplinasPorCursoProfessor(id, professorLogado.getId());
        }
    	return disciplinasDisponiveis;
    }
}
