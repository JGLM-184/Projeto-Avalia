package br.edu.fatecguarulhos.projetoavalia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class AuthController {
	
	//TELA DE LOGIN
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
    /*
    //PÁGINA QUE INDICA ACESSO NEGADO
    @GetMapping("/erro403")
    public String erro403() {
        return "erro403";
    }
    */


}
