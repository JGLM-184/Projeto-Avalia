package br.edu.fatecguarulhos.projetoavalia.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import br.edu.fatecguarulhos.projetoavalia.dto.QuestaoDTO;
import br.edu.fatecguarulhos.projetoavalia.dto.TrocaSenhaDTO;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Professor;
import br.edu.fatecguarulhos.projetoavalia.repository.ProfessorRepository;
import br.edu.fatecguarulhos.projetoavalia.service.ProfessorService;

import br.edu.fatecguarulhos.projetoavalia.service.CursoService;
import br.edu.fatecguarulhos.projetoavalia.service.DisciplinaService;
import br.edu.fatecguarulhos.projetoavalia.service.ProvaService;
import br.edu.fatecguarulhos.projetoavalia.service.QuestaoService;

@Controller
@RequestMapping
public class AmandaController {
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
    
    @Autowired
    private ProvaService provaService;
    
  //TELA INICIAL
	@GetMapping("/")
	public String index(Model model, Authentication authentication) {
	    model.addAttribute("mostrarPopup", false);

	    if (authentication != null) {
	        Professor professor = professorRepository.findByEmail(authentication.getName())
	                .orElse(null);
	        model.addAttribute("professor", professor);
	    }
	    return "index";
	}

  //TELA DE LOGIN
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
	
    @GetMapping("/cadastroQuestao")
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
    
    @PostMapping("/cadastroQuestao")
    public String salvarQuestao(@ModelAttribute QuestaoDTO dto,
                                @RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            questaoService.salvar(dto, file);
        } else {
            questaoService.salvar(dto);
        }
        return "redirect:/cadastroQuestao";
    }


    
    //TELA DE GERENCIAR QUESTÕES
    @GetMapping("/bancoDeQuestoes")
    public String bancoDeQuestoes(Model model) {
    	model.addAttribute("paginaAtiva", "bancoQuestoes");
    	model.addAttribute("pageTitle", "Banco de Questões");
    	
    	model.addAttribute("questoes", questaoService.listarTodas());
        model.addAttribute("professores", professorService.listarTodos());
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("disciplinas", disciplinaService.listarTodas());
    	
        return "bancoDeQuestoes";
    }
    
  //TELA DE MONTAR PROVA
    @GetMapping("/montarProva")
    public String montarProva(Model model) {
    	model.addAttribute("paginaAtiva", "montarProva");
    	model.addAttribute("pageTitle", "Montar prova");
        return "montarProva";
    }
    
  //TELA DO BANCO DE PROVAS
    @GetMapping("/bancoProvas")
    public String bancoProvas(Model model) {
    	model.addAttribute("paginaAtiva", "bancoProvas");
    	model.addAttribute("pageTitle", "Banco de Provas");
        return "bancoProvas";
    }
    
  
	
    
    @PostMapping("/alterar-senha")
    public String alterarSenha(@ModelAttribute TrocaSenhaDTO dto,
                               Authentication authentication,
                               Model model) {

        Professor professor = professorRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        try {
            professorService.trocarSenha(professor, dto.getNovaSenha(), dto.getConfirmarSenha());
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("professor", professor);
            return "index"; // ou mantém o popup aberto
        }

        return "redirect:/"; // volta pra tela inicial
    }

	
}
