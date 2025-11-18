package br.edu.fatecguarulhos.projetoavalia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.fatecguarulhos.projetoavalia.dto.CursoDTO;
import br.edu.fatecguarulhos.projetoavalia.service.CursoService;

@Controller
@RequestMapping("/cursos")
public class CursoController {
	
    @Autowired
    private CursoService cursoService;

    // PAINEL PRINCIPAL
    @GetMapping("/painel")
    public String listarCursos(Model model) {
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("cursoDTO", new CursoDTO());
        return "gerenciarCursos";
    }

    // SALVAR NOVO
    @PostMapping("/salvar")
    public String salvarCurso(@ModelAttribute CursoDTO dto) {
        cursoService.salvar(dto);
        return "redirect:/cursos/painel";
    }

    // CARREGAR PARA EDIÇÃO
    @GetMapping("/editar/{id}")
    public String editarCurso(@PathVariable int id, Model model) {
        model.addAttribute("cursoDTO", new CursoDTO(cursoService.buscarPorId(id)));
        model.addAttribute("isEdicaoCurso", true);
        model.addAttribute("cursos", cursoService.listarTodos()); // mantém lista
        return "gerenciarCursos";
    }

    // ATUALIZAR
    @PostMapping("/atualizar/{id}")
    public String atualizarCurso(@PathVariable int id, @ModelAttribute CursoDTO dto) {
        cursoService.atualizar(id, dto);
        return "redirect:/cursos/painel";
    }

    // EXCLUIR
    @GetMapping("/excluir/{id}")
    public String excluirCurso(@PathVariable int id) {
        cursoService.excluir(id);
        return "redirect:/cursos/painel";
    }
}
