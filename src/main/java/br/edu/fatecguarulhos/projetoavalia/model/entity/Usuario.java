package br.edu.fatecguarulhos.projetoavalia.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario {

	//Atributos
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUsuario;
	
	//Construtores
	public Usuario() {
		
	}
	
	//Getters e Setters
	
	
}
