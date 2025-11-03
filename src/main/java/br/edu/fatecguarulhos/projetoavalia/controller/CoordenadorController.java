package br.edu.fatecguarulhos.projetoavalia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class CoordenadorController {

	    @GetMapping("/painel")
	    public String painelCoordenador(Model model) {
	        model.addAttribute("pageTitle", "Painel do Coordenador");
	        return "painel";
	    }
}

