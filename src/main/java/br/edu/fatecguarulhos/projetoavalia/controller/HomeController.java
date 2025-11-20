package br.edu.fatecguarulhos.projetoavalia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Professor;
import br.edu.fatecguarulhos.projetoavalia.repository.ProfessorRepository;
import br.edu.fatecguarulhos.projetoavalia.service.ProfessorService;

@Controller
public class HomeController {
	
	@Autowired
    private ProfessorRepository professorRepository;
	
	@Autowired
    private ProfessorService professorService;
	
	//TELA INICIAL
	@GetMapping("/inicio")
	public String inicio(Model model, Authentication authentication) {

	    if (authentication != null) {
	        Professor professor = professorRepository.findByEmail(authentication.getName()).orElse(null);
	        model.addAttribute("professor", professor);

	        if (professor != null && professor.isPrimeiroAcesso()) {
	            model.addAttribute("mostrarPopupTrocaSenha", true);
	        }
	    }

	    return "index";
	}
	
	
	@PostMapping("/primeira-senha")
	public String alterarSenhaPrimeiroAcesso(
	        @RequestParam String novaSenha,
	        @RequestParam String confirmarSenha,
	        Authentication authentication,
	        RedirectAttributes redirectAttributes) {

	    Professor professor = professorRepository.findByEmail(authentication.getName())
	            .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

	    try {
	        professorService.trocarSenha(professor, novaSenha, confirmarSenha);
	        redirectAttributes.addFlashAttribute("successMessage", "Senha alterada com sucesso!");

	    } catch (IllegalArgumentException e) {
	        redirectAttributes.addFlashAttribute("erroTrocaSenha", e.getMessage());
	        redirectAttributes.addFlashAttribute("mostrarPopupTrocaSenha", true);
	    }

	    return "redirect:/inicio";
	}




}
