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
	
	
}
