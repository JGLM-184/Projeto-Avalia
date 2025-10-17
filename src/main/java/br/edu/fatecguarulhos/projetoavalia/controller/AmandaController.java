package br.edu.fatecguarulhos.projetoavalia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class AmandaController {
	/* NÃO MEXER NESTA CLASSE - NÃO MEXER NESTA CLASSE - NÃO MEXER NESTA CLASSE - NÃO MEXER NESTA CLASSE*/
	/* NÃO MEXER NESTA CLASSE - NÃO MEXER NESTA CLASSE - NÃO MEXER NESTA CLASSE - NÃO MEXER NESTA CLASSE*/
	
	//TELA INICIAL
    @GetMapping("/")
    public String inicio(Model model) {
    	model.addAttribute("pageTitle", "Início");
        return "index";
    }
	
	
	//TELA DE CADASTRO DE QUESTÕES
    @GetMapping("/cadastroQuestao")
    public String cadastroQuestao(Model model) {
    	model.addAttribute("paginaAtiva", "cadastroQuestao");
    	model.addAttribute("pageTitle", "Cadastro de Questão");
        return "cadastroQuestao";
    }
	
  //TELA DE LOGIN
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

	
	
}
