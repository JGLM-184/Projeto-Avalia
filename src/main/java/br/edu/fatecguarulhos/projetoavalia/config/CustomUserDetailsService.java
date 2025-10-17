package br.edu.fatecguarulhos.projetoavalia.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
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

        // TRANSFORMA BOOLEAN DE "ADMIN" EM UM ROLE
        //ROLE_ADMIN É A TAG QUE O SPRING USA PARA RECONHECER QUE AQUELE USUÁRIO TEM PRIVILÉGIOS DE ADMINISTRADOR
        String role = professor.isCoordenador() ? "ROLE_ADMIN" : "ROLE_USER";

        //AUTENTICAÇÃO SEGURA COM A SENHA CRIPTOGRAFADA
        return User.builder()
                .username(professor.getEmail())
                .password(professor.getSenha())
                .authorities(Collections.singletonList(() -> role))
                .build();
    }
}
