package br.edu.fatecguarulhos.projetoavalia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.fatecguarulhos.projetoavalia.dto.DisciplinaDTO;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;
import br.edu.fatecguarulhos.projetoavalia.service.CursoService;
import br.edu.fatecguarulhos.projetoavalia.service.DisciplinaService;

@Controller
@RequestMapping("/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private CursoService cursoService;

    @GetMapping("/painel")
    public String listarDisciplinas(Model model) {
    	
    	model.addAttribute("paginaAtiva", "painel");
	    model.addAttribute("pageTitle", "Gerenciar Disciplinas");
    	
        model.addAttribute("disciplinas", disciplinaService.listarTodas());
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("disciplinaDTO", new DisciplinaDTO());
        model.addAttribute("isEdicaoDisciplina", false);
        return "gerenciarDisciplinas";
    }

    @PostMapping("/salvar")
    public String salvarDisciplina(DisciplinaDTO dto, RedirectAttributes redirectAttributes) {
        disciplinaService.salvar(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Disciplina criada com sucesso.");
        return "redirect:/disciplinas/painel";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarDisciplina(@PathVariable int id, DisciplinaDTO dto, RedirectAttributes redirectAttributes) {
        disciplinaService.atualizar(id, dto);
        redirectAttributes.addFlashAttribute("successMessage", "Disciplina atualizada com sucesso.");
        return "redirect:/disciplinas/painel";
    }

    @GetMapping("/excluir/{id}")
    public String excluirDisciplina(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            disciplinaService.excluir(id);
            redirectAttributes.addFlashAttribute("successMessage", "Disciplina excluída com sucesso.");
        } catch (IllegalStateException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir a disciplina.");
        }
        return "redirect:/disciplinas/painel";
    }
    
    @ResponseBody
    @GetMapping("/por-cursos")
    public List<Disciplina> listarDisciplinasPorCursos(@RequestParam List<Integer> cursosIds) {
        return disciplinaService.buscarPorCursosIds(cursosIds);
    }
}
