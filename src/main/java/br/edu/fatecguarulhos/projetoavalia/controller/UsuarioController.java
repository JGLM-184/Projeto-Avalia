package br.edu.fatecguarulhos.projetoavalia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping
public class UsuarioController {


	 @GetMapping("/painel-usuarios")
	    public String painelUsuarios(Model model) {
	        model.addAttribute("pageTitle", "Gerenciar usuários");
	        return "painelUsuarios";
	    }
	 
	 @GetMapping("/criar-usuario")
	    public String criarUsuario(Model model) {
	        model.addAttribute("pageTitle", "Cadastrar usuário");
	        return "formularioUsuario";
	    }
	
	 @GetMapping("/editar-usuario")
	    public String editarUsuario(Model model) {
	        model.addAttribute("pageTitle", "Editar usuário");
	        return "formularioUsuario";
	    }
	
}
