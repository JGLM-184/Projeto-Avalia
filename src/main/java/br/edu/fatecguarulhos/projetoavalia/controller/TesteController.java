package br.edu.fatecguarulhos.projetoavalia.controller;

import java.util.Collections;
import java.util.List;

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
import br.edu.fatecguarulhos.projetoavalia.model.entity.Questao;
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

    // ------------------- MENU PRINCIPAL -------------------
    @GetMapping
    public String menu() {
        return "teste"; // Página com links para cursos, disciplinas, questões e professores
    }

    // =====================================================
    // ===================== CURSOS ========================
    // =====================================================
    @GetMapping("/cursos")
    public String listarCursos(Model model) {
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("cursoDTO", new CursoDTO());
        return "testeCursos";
    }

    @PostMapping("/cursos/salvar")
    public String salvarCurso(@ModelAttribute CursoDTO dto) {
        cursoService.salvar(dto);
        return "redirect:/teste/cursos";
    }

    @GetMapping("/cursos/editar/{id}")
    public String editarCurso(@PathVariable int id, Model model) {
        model.addAttribute("cursoDTO", new CursoDTO(cursoService.buscarPorId(id)));
        model.addAttribute("isEdicaoCurso", true);
        return "testeCursos";
    }

    @PostMapping("/cursos/atualizar/{id}")
    public String atualizarCurso(@PathVariable int id, @ModelAttribute CursoDTO dto) {
        cursoService.atualizar(id, dto);
        return "redirect:/teste/cursos";
    }

    @GetMapping("/cursos/excluir/{id}")
    public String excluirCurso(@PathVariable int id) {
        cursoService.excluir(id);
        return "redirect:/teste/cursos";
    }

    // =====================================================
    // =================== DISCIPLINAS =====================
    // =====================================================
    @GetMapping("/disciplinas")
    public String listarDisciplinas(Model model) {
        model.addAttribute("disciplinas", disciplinaService.listarTodas());
        model.addAttribute("disciplinaDTO", new DisciplinaDTO());
        model.addAttribute("cursos", cursoService.listarTodos());
        return "testeDisciplinas";
    }

    @PostMapping("/disciplinas/salvar")
    public String salvarDisciplina(@ModelAttribute DisciplinaDTO dto) {
        disciplinaService.salvar(dto);
        return "redirect:/teste/disciplinas";
    }

    @GetMapping("/disciplinas/editar/{id}")
    public String editarDisciplina(@PathVariable int id, Model model) {
        model.addAttribute("disciplinaDTO", new DisciplinaDTO(disciplinaService.buscarPorId(id)));
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("isEdicaoDisciplina", true);
        return "testeDisciplinas";
    }

    @PostMapping("/disciplinas/atualizar/{id}")
    public String atualizarDisciplina(@PathVariable int id, @ModelAttribute DisciplinaDTO dto) {
        disciplinaService.atualizar(id, dto);
        return "redirect:/teste/disciplinas";
    }

    @GetMapping("/disciplinas/excluir/{id}")
    public String excluirDisciplina(@PathVariable int id) {
        disciplinaService.excluir(id);
        return "redirect:/teste/disciplinas";
    }

    // =====================================================
    // ===================== QUESTÕES ======================
    // =====================================================
    @GetMapping("/questoes")
    public String listarQuestoes(Model model) {
        model.addAttribute("questoes", questaoService.listarTodas());
        model.addAttribute("professores", professorService.listarTodos());
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("disciplinas", disciplinaService.listarTodas());
        
        QuestaoDTO questaoDTO = new QuestaoDTO(5);
        model.addAttribute("questaoDTO", questaoDTO);

        return "testeQuestoes";
    }

    @PostMapping("/questoes/salvar")
    public String salvarQuestao(@ModelAttribute QuestaoDTO dto,
                                @RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            questaoService.salvar(dto, file);
        } else {
            questaoService.salvar(dto);
        }
        return "redirect:/teste/questoes";
    }

    @GetMapping("/questoes/editar/{id}")
    public String editarQuestao(@PathVariable int id, Model model) {
    	QuestaoDTO questaoDTO = new QuestaoDTO(questaoService.buscarPorId(id));
    	questaoDTO.setTamanhoListaAlternativas(questaoService.contarAlternativasPorId(id));
        model.addAttribute("questaoDTO", new QuestaoDTO(questaoService.buscarPorId(id)));
        model.addAttribute("professores", professorService.listarTodos());
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("disciplinas", disciplinaService.listarTodas());
        model.addAttribute("isEdicaoQuestao", true);
        
        return "testeQuestoes";
    }

    @PostMapping("/questoes/atualizar/{id}")
    public String atualizarQuestao(@PathVariable int id,
                                   @ModelAttribute QuestaoDTO dto,
                                   @RequestParam(value = "file", required = false) MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            questaoService.atualizar(id, dto, file);
        } else {
            questaoService.atualizar(id, dto);
        }
        return "redirect:/teste/questoes";
    }

    @GetMapping("/questoes/excluir/{id}")
    public String excluirQuestao(@PathVariable int id) {
        questaoService.excluir(id);
        return "redirect:/teste/questoes";
    }

    // =====================================================
    // ==================== PROFESSORES ====================
    // =====================================================
    @GetMapping("/professores")
    public String listarProfessores(Model model) {
        model.addAttribute("professores", professorService.listarTodos());
        return "testeProfessores";
    }
    
    // =====================================================
    // ======================= PROVA =======================
    // =====================================================
    @GetMapping("/provas")
    public String selecionarDisciplina(Model model) {
        model.addAttribute("disciplinas", disciplinaService.listarTodas());
        return "testeProva";
    }

    @PostMapping("/provas/gerar-prova")
    public String gerarProva(@RequestParam int disciplinaId, @RequestParam int qntQuestoes, Model model) {
        // Busca todas as questões da disciplina
        List<Questao> questoes = questaoService.buscarPorDisciplina(disciplinaId);

        // Embaralha e seleciona algumas questões aleatórias
        Collections.shuffle(questoes);
        List<Questao> questoesSelecionadas = questoes.stream().limit(qntQuestoes).toList();

        model.addAttribute("questoes", questoesSelecionadas);
        return "testeProvaGerada";
    }
}
