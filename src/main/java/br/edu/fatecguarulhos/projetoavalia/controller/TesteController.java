package br.edu.fatecguarulhos.projetoavalia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.edu.fatecguarulhos.projetoavalia.dto.CursoDTO;
import br.edu.fatecguarulhos.projetoavalia.dto.DisciplinaDTO;
import br.edu.fatecguarulhos.projetoavalia.dto.QuestaoDTO;
import br.edu.fatecguarulhos.projetoavalia.service.CursoService;
import br.edu.fatecguarulhos.projetoavalia.service.DisciplinaService;
import br.edu.fatecguarulhos.projetoavalia.service.ProfessorService;
import br.edu.fatecguarulhos.projetoavalia.service.QuestaoService;

@Controller
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private QuestaoService questaoService;

    @Autowired
    private ProfessorService professorService;

    // ------------------- LISTAR -------------------
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("disciplinas", disciplinaService.listarTodas());
        model.addAttribute("questoes", questaoService.listarTodas());
        model.addAttribute("professores", professorService.listarTodos());
        
        // DTO para formulário de criação/edição de questão
        QuestaoDTO questaoDTO = new QuestaoDTO();
        
        // Inicializa lista de alternativas com 5 objetos por padrão
        questaoDTO.addAlternativa(5);
        
        model.addAttribute("questaoDTO", questaoDTO);
        return "teste";
    }

    // ------------------- CRIAR -------------------
    @PostMapping("/salvar-curso")
    public String salvarCurso(@ModelAttribute CursoDTO dto) {
        cursoService.salvar(dto);
        return "redirect:/teste";
    }

    @PostMapping("/salvar-disciplina")
    public String salvarDisciplina(@ModelAttribute DisciplinaDTO dto) {
        disciplinaService.salvar(dto);
        return "redirect:/teste";
    }

    @PostMapping("/salvar-questao")
    public String salvarQuestao(@ModelAttribute QuestaoDTO dto,
                                @RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            questaoService.salvar(dto, file);
        } else {
            questaoService.salvar(dto);
        }
        return "redirect:/teste";
    }

    // ------------------- EDITAR -------------------
    @GetMapping("/editar-curso/{id}")
    public String editarCurso(@PathVariable int id, Model model) {
        model.addAttribute("cursoDTO", new CursoDTO(cursoService.buscarPorId(id)));
        model.addAttribute("isEdicaoCurso", true);
        return "teste";
    }

    @GetMapping("/editar-disciplina/{id}")
    public String editarDisciplina(@PathVariable int id, Model model) {
        model.addAttribute("disciplinaDTO", new DisciplinaDTO(disciplinaService.buscarPorId(id)));
        model.addAttribute("isEdicaoDisciplina", true);
        return "teste";
    }

    @GetMapping("/editar-questao/{id}")
    public String editarQuestao(@PathVariable int id, Model model) {
        model.addAttribute("questaoDTO", new QuestaoDTO(questaoService.buscarPorId(id)));
        model.addAttribute("professores", professorService.listarTodos());
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("disciplinas", disciplinaService.listarTodas());
        model.addAttribute("isEdicaoQuestao", true);
        return "teste";
    }

    @PostMapping("/atualizar-curso/{id}")
    public String atualizarCurso(@PathVariable int id, @ModelAttribute CursoDTO dto) {
        cursoService.atualizar(id, dto);
        return "redirect:/teste";
    }

    @PostMapping("/atualizar-disciplina/{id}")
    public String atualizarDisciplina(@PathVariable int id, @ModelAttribute DisciplinaDTO dto) {
        disciplinaService.atualizar(id, dto);
        return "redirect:/teste";
    }

    @PostMapping("/atualizar-questao/{id}")
    public String atualizarQuestao(@PathVariable int id,
                                   @ModelAttribute QuestaoDTO dto,
                                   @RequestParam(value = "file", required = false) MultipartFile file) {
        questaoService.atualizar(id, dto);
        if (file != null && !file.isEmpty()) {
            questaoService.atualizar(id, dto, file);
        }
        return "redirect:/teste";
    }

    // ------------------- EXCLUIR -------------------
    @GetMapping("/excluir-curso/{id}")
    public String excluirCurso(@PathVariable int id) {
        cursoService.excluir(id);
        return "redirect:/teste";
    }

    @GetMapping("/excluir-disciplina/{id}")
    public String excluirDisciplina(@PathVariable int id) {
        disciplinaService.excluir(id);
        return "redirect:/teste";
    }

    @GetMapping("/excluir-questao/{id}")
    public String excluirQuestao(@PathVariable int id) {
        questaoService.excluir(id);
        return "redirect:/teste";
    }
}
