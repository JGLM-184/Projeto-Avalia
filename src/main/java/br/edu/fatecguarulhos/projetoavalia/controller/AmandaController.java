package br.edu.fatecguarulhos.projetoavalia.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import br.edu.fatecguarulhos.projetoavalia.dto.TrocaSenhaDTO;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Professor;
import br.edu.fatecguarulhos.projetoavalia.repository.ProfessorRepository;
import br.edu.fatecguarulhos.projetoavalia.service.ProfessorService;

@Controller
@RequestMapping
public class AmandaController {
	/* NÃO MEXER NESTA CLASSE - NÃO MEXER NESTA CLASSE - NÃO MEXER NESTA CLASSE - NÃO MEXER NESTA CLASSE*/
	/* NÃO MEXER NESTA CLASSE - NÃO MEXER NESTA CLASSE - NÃO MEXER NESTA CLASSE - NÃO MEXER NESTA CLASSE*/
	
	@Autowired
    private ProfessorRepository professorRepository;
	
	@Autowired
    private ProfessorService professorService;
    
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
	
	//TELA DE CADASTRO DE QUESTÕES
    @GetMapping("/cadastroQuestao")
    public String cadastroQuestao(Model model) {
    	model.addAttribute("paginaAtiva", "cadastroQuestao");
    	model.addAttribute("pageTitle", "Cadastro de Questão");
        return "cadastroQuestao";
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
    
  //TELA DE GERENCIAR QUESTÕES
    @GetMapping("/gerenciarQuestoes")
    public String gerenciarQuestoes(Model model) {
    	model.addAttribute("paginaAtiva", "gerenciarQuestoes");
    	model.addAttribute("pageTitle", "Gerenciar Questões");
        return "gerenciarQuestoes";
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
            model.addAttribute("professor", professor); // <-- essencial
            return "index"; // ou mantém o popup aberto
        }

        return "redirect:/"; // volta pra tela inicial
    }

	
}
