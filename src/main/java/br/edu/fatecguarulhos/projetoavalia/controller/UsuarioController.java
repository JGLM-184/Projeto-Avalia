package br.edu.fatecguarulhos.projetoavalia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.fatecguarulhos.projetoavalia.dto.TrocaSenhaDTO;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Professor;
import br.edu.fatecguarulhos.projetoavalia.repository.ProfessorRepository;
import br.edu.fatecguarulhos.projetoavalia.service.ProfessorService;

@Controller
@RequestMapping("/users")
public class UsuarioController {

	@Autowired
    private ProfessorRepository professorRepository;
	
	@Autowired
    private ProfessorService professorService;
	
	

	 @GetMapping("/painel")
	    public String painelUsuarios(Model model) {
	        model.addAttribute("pageTitle", "Gerenciar usuários");
	        return "painelUsuarios";
	    }
	 
	 @GetMapping("/cadastrar")
	    public String criarUsuario(Model model) {
	        model.addAttribute("pageTitle", "Cadastrar usuário");
	        return "formularioUsuario";
	    }
	
	 @GetMapping("/editar")
	    public String editarUsuario(Model model) {
	        model.addAttribute("pageTitle", "Editar usuário");
	        return "formularioUsuario";
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
