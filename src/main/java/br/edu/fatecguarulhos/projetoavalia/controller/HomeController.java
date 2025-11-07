package br.edu.fatecguarulhos.projetoavalia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Professor;
import br.edu.fatecguarulhos.projetoavalia.repository.ProfessorRepository;

@Controller
@RequestMapping()
public class HomeController {
	
	@Autowired
    private ProfessorRepository professorRepository;
	
	//TELA INICIAL
		@GetMapping("/inicio")
		public String index(Model model, Authentication authentication) {
		    model.addAttribute("mostrarPopup", false);

		    if (authentication != null) {
		        Professor professor = professorRepository.findByEmail(authentication.getName())
		                .orElse(null);
		        model.addAttribute("professor", professor);
		    }
		    return "index";
		}
}
