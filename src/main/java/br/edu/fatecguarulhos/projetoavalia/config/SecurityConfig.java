package br.edu.fatecguarulhos.projetoavalia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		 http
	        .authorizeHttpRequests(auth -> auth
	        		//TORNA ESSAS ROTAS PÚBLICAS (NÃO PEDE LOGIN) PARA O DESENVOLVIMENTO
	        	    .requestMatchers("/login**", "/css/**", "/js/**").permitAll()
	        	    
	        	    //QUALQUER USUÁRIO AUTENTICADO PODE ACESSAR ESSAS ROTAS
	        	    .requestMatchers("/**").authenticated()
	        	    
	        	    //SOMENTE UM COORDENADOR PODE ACESSAS ESSAS ROTAS
	        	    //.requestMatchers("/", "/", "/").hasRole("ADMIN")
	        	    
	        	    //SOMENTE COORDENADOR PODE FAZER CRUD
	        	    //.requestMatchers("/").hasRole("ADMIN")
	        	    //.anyRequest().authenticated()
	        	)
	        
	        //CUIDA DA PARTE DE LOGIN, CHAMA A API QUE TEM O LOGIN.HTML E LEVA AO INICIO.HTML
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
        //CUIDA DA PARTE DE LOGOU, CHAMA A API QUE TEM O INICIO.HTML AO SAIR
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            //CSRF: PROTEÇÃO CONTRA REQUISIÇÕES MALICIOSAS USANDO A SESSÃO DO USUÁRIO
            //PODE SER DESABILITADA EM TESTES PARA FACILITAR
            .csrf(csrf -> csrf.disable());

        return http.build();
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
