package br.edu.fatecguarulhos.projetoavalia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.fatecguarulhos.projetoavalia.dto.CursoDTO;
import br.edu.fatecguarulhos.projetoavalia.service.CursoService;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping("/painel")
    public String listarCursos(Model model) {
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("cursoDTO", new CursoDTO());
        return "gerenciarCursos";
    }

    @PostMapping("/salvar")
    public String salvarCurso(CursoDTO dto, RedirectAttributes redirectAttributes) {
        cursoService.salvar(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Curso criado com sucesso.");
        return "redirect:/cursos/painel";
    }

    @GetMapping("/editar/{id}")
    public String editarCurso(@PathVariable int id, Model model) {
        model.addAttribute("cursoDTO", new CursoDTO(cursoService.buscarPorId(id)));
        model.addAttribute("isEdicaoCurso", true);
        model.addAttribute("cursos", cursoService.listarTodos());
        return "gerenciarCursos";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarCurso(@PathVariable int id, CursoDTO dto, RedirectAttributes redirectAttributes) {
        cursoService.atualizar(id, dto);
        redirectAttributes.addFlashAttribute("successMessage", "Curso atualizado com sucesso.");
        return "redirect:/cursos/painel";
    }

    @GetMapping("/excluir/{id}")
    public String excluirCurso(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            cursoService.excluir(id);
            redirectAttributes.addFlashAttribute("successMessage", "Curso excluído com sucesso.");
        } catch (IllegalStateException ex) {
            // Regra de negócio bloqueou a exclusão: envia mensagem explicando o motivo
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir o curso.");
        }
        return "redirect:/cursos/painel";
    }
}
