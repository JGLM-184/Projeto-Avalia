package br.edu.fatecguarulhos.projetoavalia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fatecguarulhos.projetoavalia.repository.QuestaoRepository;

@Service
public class QuestaoService {

	@Autowired
	QuestaoRepository questaoRepository;
}
