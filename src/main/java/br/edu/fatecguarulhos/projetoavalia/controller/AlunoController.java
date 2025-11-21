package br.edu.fatecguarulhos.projetoavalia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

	@GetMapping("/buscar-simulado")
	public String procurarSimulado() {

	    return "buscarSimulado";
	}
	
	@GetMapping("/fazer-simulado")
	public String fazerSimulado() {

	    return "simulado";
	}
	
	@GetMapping("/envio-simulado")
	public String envioSimulado() {

	    return "envioSimulado";
	}
}
