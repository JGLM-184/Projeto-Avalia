package br.edu.fatecguarulhos.projetoavalia.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Professor;
import br.edu.fatecguarulhos.projetoavalia.repository.ProfessorRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
    private ProfessorRepository professorRepository;

    //VERIFICA SE O USUÁRIO EXISTE
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    Professor professor = professorRepository.findByEmail(email)
	            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

	    if (!professor.isAtivo()) {
	        throw new org.springframework.security.authentication.DisabledException(
	                "Usuário inativo. Contate o coordenador."
	        );
	    }

	    String role = professor.isCoordenador() ? "ROLE_ADMIN" : "ROLE_USER";

	    return User.builder()
	            .username(professor.getEmail())
	            .password(professor.getSenha())
	            .authorities(Collections.singletonList(() -> role))
	            .build();
	}
}
