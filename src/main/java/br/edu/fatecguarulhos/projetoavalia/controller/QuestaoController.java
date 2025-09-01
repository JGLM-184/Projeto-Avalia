package br.edu.fatecguarulhos.projetoavalia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.edu.fatecguarulhos.projetoavalia.service.QuestaoService;

@RestController
public class QuestaoController {

	@Autowired
	QuestaoService questaoService;
}
