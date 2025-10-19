package br.edu.fatecguarulhos.projetoavalia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	//MAPEIA A URL /IMAGENS/QUESTOES/** PARA A PASTA DO PROJETO
        registry.addResourceHandler("/imagens/questoes/**")
                .addResourceLocations("file:src/main/resources/static/imagens/questoes/");
    }
}
