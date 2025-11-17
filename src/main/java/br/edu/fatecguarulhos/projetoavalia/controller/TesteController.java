package br.edu.fatecguarulhos.projetoavalia.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import br.edu.fatecguarulhos.projetoavalia.dto.CursoDTO;
import br.edu.fatecguarulhos.projetoavalia.dto.DisciplinaDTO;
import br.edu.fatecguarulhos.projetoavalia.dto.ProvaDTO;
import br.edu.fatecguarulhos.projetoavalia.dto.ProvaDisciplinaDTO;
import br.edu.fatecguarulhos.projetoavalia.dto.ProvaQuestaoDTO;
import br.edu.fatecguarulhos.projetoavalia.dto.QuestaoDTO;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Prova;
import br.edu.fatecguarulhos.projetoavalia.service.CursoService;
import br.edu.fatecguarulhos.projetoavalia.service.DisciplinaService;
import br.edu.fatecguarulhos.projetoavalia.service.ProfessorService;
import br.edu.fatecguarulhos.projetoavalia.service.ProvaService;
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
    
    @Autowired
    private ProvaService provaService;

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
    
    @ResponseBody
    @GetMapping("api/disciplinas/por-curso/{id}")
    public List<Disciplina> listarDisciplinasPorCurso(@PathVariable int id) {
    	return disciplinaService.buscarPorCursoId(id);
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
        
        //Objeto é criado com uma lista de 5 alternativas
        model.addAttribute("questaoDTO", new QuestaoDTO(5));

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
    	model.addAttribute("questaoDTO", new QuestaoDTO(questaoService.buscarPorId(id)));
    	model.addAttribute("professores", professorService.listarTodos());
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("qntAlternativas", questaoService.contarAlternativasPorId(id));

        //Busca as disciplinas com base no curso da questao
        model.addAttribute("disciplinas", disciplinaService.buscarPorCursoQuestaoId(id));
        
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
    public String listarProvas(Model model) {
        model.addAttribute("provas", provaService.listarTodas());
        model.addAttribute("provaDTO", new ProvaDTO());
        model.addAttribute("cursos", cursoService.listarTodos());
        return "testeProva";
    }

    @PostMapping("/provas/salvar")
    public String salvarProva(@ModelAttribute ProvaDTO provaDTO) {

        int professorId = provaService.getUsuarioLogado().getId();

        Optional<Prova> existenteOpt = provaService.buscarPorTituloEProfessorOptional(
                provaDTO.getTitulo(), professorId);

        if (existenteOpt.isPresent()) {
            return "redirect:/teste/provas/editar/" + existenteOpt.get().getId();
        }

        try {
            Prova nova = provaService.criar(provaDTO);
            return "redirect:/teste/provas/editar/" + nova.getId();
        } catch (DataIntegrityViolationException ex) {
            Optional<Prova> existenteAfterRace = provaService.buscarPorTituloEProfessorOptional(
                    provaDTO.getTitulo(), professorId);

            if (existenteAfterRace.isPresent()) {
                return "redirect:/teste/provas/editar/" + existenteAfterRace.get().getId();
            }

            throw ex;
        }
    }
    

    @GetMapping("/provas/editar/{id}")
    public String editarProva(@PathVariable int id, Model model) {
        ProvaDTO provaDTO = new ProvaDTO(provaService.buscarPorId(id));
        
        
        // Lista de disciplinas baseadas no curso da prova
        model.addAttribute("disciplinas", disciplinaService.buscarPorCursoId(provaDTO.getCurso().getId()));
        
        // Lista de disciplinas e questões já associadas à prova
        model.addAttribute("disciplinasProva", provaService.listarDisciplinasPorProva(id));
        model.addAttribute("questoesProva", provaService.listarQuestoesPorProva(id));

        // Lista de questões disponíveis (de disciplinas já associadas)
        model.addAttribute("questoesDisponiveis", provaService.listarQuestoesDisponiveisPorProva(id));

        // Dados para o formulário
        model.addAttribute("provaDTO", provaDTO);
        model.addAttribute("provaDisciplinaDTO", new ProvaDisciplinaDTO());
        model.addAttribute("provaQuestaoDTO", new ProvaQuestaoDTO());

        return "testeProvaEdicao";
    }
    
    @PostMapping("/provas/editar/{id}/adicionar-questao")
    public String adicionarQuestao(@PathVariable int id, @ModelAttribute ProvaQuestaoDTO provaQuestaoDTO) {
    	provaService.adicionarQuestao(provaQuestaoDTO);
    	return "redirect:/teste/provas/editar/" + id;
    }
    
    @PostMapping("/provas/editar/{id}/remover-questao")
    public String removerQuestao(@PathVariable("id") int provaId,
                                 @RequestParam(name = "questao.id", required = true) int questaoId) {

        provaService.removerQuestao(provaId, questaoId);

        return "redirect:/teste/provas/editar/" + provaId;
    }
    
    @PostMapping("/provas/editar/{id}/adicionar-disciplina")
    public String adicionarDisciplina(@PathVariable int id, @ModelAttribute ProvaDisciplinaDTO provaDisciplinaDTO) {
    	provaDisciplinaDTO.setProva(provaService.buscarPorId(id));
    	provaService.adicionarDisciplina(provaDisciplinaDTO);
    	return "redirect:/teste/provas/editar/" + id;
    }

    @PostMapping("/provas/atualizar/{id}")
    public String atualizarProva(@PathVariable int id, @ModelAttribute ProvaDTO provaDTO) {
        provaService.atualizar(id, provaDTO);
        return "redirect:/teste/provas";
    }

    @GetMapping("/provas/excluir/{id}")
    public String excluirProva(@PathVariable int id) {
        provaService.excluir(id);
        return "redirect:/teste/provas";
    }

    @GetMapping("/provas/detalhes/{id}")
    public String detalhesProva(@PathVariable int id, Model model) {
        model.addAttribute("provaDTO", new ProvaDTO(provaService.buscarPorId(id)));
        model.addAttribute("questoes", provaService.listarQuestoesPorProva(id));
        model.addAttribute("disciplinas", provaService.listarDisciplinasPorProva(id));
        return "testeProvaDetalhes";
    }
}
