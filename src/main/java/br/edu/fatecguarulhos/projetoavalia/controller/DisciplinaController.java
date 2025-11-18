package br.edu.fatecguarulhos.projetoavalia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.edu.fatecguarulhos.projetoavalia.dto.DisciplinaDTO;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;
import br.edu.fatecguarulhos.projetoavalia.service.CursoService;
import br.edu.fatecguarulhos.projetoavalia.service.DisciplinaService;

@Controller
@RequestMapping("/disciplinas")
public class DisciplinaController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private DisciplinaService disciplinaService;

    @GetMapping("/painel")
    public String listarDisciplinas(Model model) {
        model.addAttribute("disciplinas", disciplinaService.listarTodas());
        model.addAttribute("disciplinaDTO", new DisciplinaDTO());
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("isEdicaoDisciplina", false);
        return "gerenciarDisciplinas";
    }

    @PostMapping("/salvar")
    public String salvarDisciplina(@ModelAttribute DisciplinaDTO dto) {
        disciplinaService.salvar(dto);
        return "redirect:/disciplinas/painel";
    }

    @GetMapping("/editar/{id}")
    public String editarDisciplina(@PathVariable int id, Model model) {
        model.addAttribute("disciplinaDTO", new DisciplinaDTO(disciplinaService.buscarPorId(id)));
        model.addAttribute("disciplinas", disciplinaService.listarTodas());
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("isEdicaoDisciplina", true);
        return "gerenciarDisciplinas";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarDisciplina(@PathVariable int id, @ModelAttribute DisciplinaDTO dto) {
        disciplinaService.atualizar(id, dto);
        return "redirect:/disciplinas/painel";
    }

    @GetMapping("/excluir/{id}")
    public String excluirDisciplina(@PathVariable int id) {
        disciplinaService.excluir(id);
        return "redirect:/disciplinas/painel";
    }

    @ResponseBody
    @GetMapping("/api/disciplinas/por-curso/{id}")
    public List<Disciplina> listarDisciplinasPorCurso(@PathVariable int id) {
        return disciplinaService.buscarPorCursoId(id);
    }
}
