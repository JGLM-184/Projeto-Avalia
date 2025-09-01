package br.edu.fatecguarulhos.projetoavalia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fatecguarulhos.projetoavalia.repository.AlternativaRepository;

@Service
public class AlternativaService {

	@Autowired
	AlternativaRepository alternativaRepository;
}
