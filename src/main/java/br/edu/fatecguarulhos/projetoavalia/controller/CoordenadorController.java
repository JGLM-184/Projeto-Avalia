package br.edu.fatecguarulhos.projetoavalia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.fatecguarulhos.projetoavalia.dto.TrocaSenhaDTO;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Professor;
import br.edu.fatecguarulhos.projetoavalia.repository.ProfessorRepository;
import br.edu.fatecguarulhos.projetoavalia.service.ProfessorService;

@Controller
@RequestMapping("/coordenador")
public class CoordenadorController {
	
	@Autowired
	ProfessorRepository professorRepository;
	
	@Autowired
	ProfessorService professorService;


	    @GetMapping("/painel")
	    public String painelCoordenador(Model model) {
	        model.addAttribute("pageTitle", "Painel do Coordenador");
	        return "painel";
	    }
	    
	    @PostMapping("/alterar-senha")
	    public String alterarSenha(@RequestParam int idProfessor,
	                               @ModelAttribute TrocaSenhaDTO dto,
	                               RedirectAttributes redirectAttributes) {

	        Professor professor = professorRepository.findById(idProfessor)
	                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

	        try {
	            professorService.trocarSenha(professor, dto.getNovaSenha(), dto.getConfirmarSenha());
	            redirectAttributes.addFlashAttribute("successMessage", "Senha alterada com sucesso!");
	        } catch (IllegalArgumentException e) {
	            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
	        }

	        return "redirect:/users/painel";
	    }

}

