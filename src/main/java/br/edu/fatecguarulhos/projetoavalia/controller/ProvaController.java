package br.edu.fatecguarulhos.projetoavalia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.edu.fatecguarulhos.projetoavalia.dto.ProvaDTO;
import br.edu.fatecguarulhos.projetoavalia.dto.ProvaDisciplinaDTO;
import br.edu.fatecguarulhos.projetoavalia.dto.ProvaQuestaoDTO;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Professor;
import br.edu.fatecguarulhos.projetoavalia.repository.ProfessorRepository;
import br.edu.fatecguarulhos.projetoavalia.service.CursoService;
import br.edu.fatecguarulhos.projetoavalia.service.DisciplinaService;
import br.edu.fatecguarulhos.projetoavalia.service.ProvaService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/provas")
public class ProvaController {

    @Autowired
    private ProvaService provaService;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private DisciplinaService disciplinaService;
    
    @Autowired
    private ProfessorRepository professorRepository;

    // ==================== TELAS ====================

    // TELA DE CRIAÇÃO DE PROVA (testeProva.html)
    @GetMapping("/criar")
    public String criarProva(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Professor> professorOpt = professorRepository.findByEmail(auth.getName());
        
        // SEMPRE adicionar o provaDTO ao modelo
        model.addAttribute("provaDTO", new ProvaDTO());
        
        if (professorOpt.isEmpty()) {
            model.addAttribute("erro", "Professor não encontrado. Faça login novamente.");
            // Adicionar cursos vazios para evitar erro no template
            model.addAttribute("cursos", List.of());
            model.addAttribute("provas", List.of());
            return "testeProva";
        }
        
        Professor professorLogado = professorOpt.get();

        // Coordenador vê todos os cursos, professor vê apenas seus cursos
        if (isCoordenador(auth)) {
            model.addAttribute("cursos", cursoService.listarTodos());
        } else {
            model.addAttribute("cursos", professorLogado.getCursos());
        }

        // Lista todas as provas para a tabela
        if (isCoordenador(auth)) {
            model.addAttribute("provas", provaService.listarTodas());
        } else {
            model.addAttribute("provas", provaService.listarTodas().stream()
                    .filter(prova -> prova.getProfessor().getEmail().equals(auth.getName()))
                    .toList());
        }

        return "testeProva";
    }

    // BANCO DE PROVAS (bancoProvas.html)
    @GetMapping("/banco")
    public String bancoProvas(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Coordenador vê todas as provas, professor vê apenas as suas
        if (isCoordenador(auth)) {
            model.addAttribute("provas", provaService.listarTodas());
        } else {
            model.addAttribute("provas", provaService.listarTodas().stream()
                    .filter(prova -> prova.getProfessor().getEmail().equals(auth.getName()))
                    .toList());
        }

        return "bancoProvas";
    }

    // TELA DE EDIÇÃO DA PROVA (testeProvaEdicao.html)
    @GetMapping("/editar/{id}")
    public String editarProva(@PathVariable int id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        ProvaDTO provaDTO = new ProvaDTO(provaService.buscarPorId(id));
        
        // Verificar permissão - professor só pode editar suas próprias provas
        if (!isCoordenador(auth) && !provaDTO.getProfessor().getEmail().equals(auth.getName())) {
            return "redirect:/provas/criar";
        }

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

    @GetMapping("/detalhes/{id}")
    public String detalhesProva(@PathVariable int id, Model model) {
        model.addAttribute("provaDTO", new ProvaDTO(provaService.buscarPorId(id)));
        model.addAttribute("questoes", provaService.listarQuestoesPorProva(id));
        model.addAttribute("disciplinas", provaService.listarDisciplinasPorProva(id));
        return "testeProvaDetalhes";
    }

    // ==================== APIs ====================

    // API PARA SALVAR PROVA
    @PostMapping("/salvar")
    public String salvarProva(@ModelAttribute ProvaDTO provaDTO, Model model) {
        try {
            provaService.criar(provaDTO);
            return "redirect:/provas/criar?sucesso";
        } catch (RuntimeException e) {
            model.addAttribute("erro", "Erro ao criar prova: " + e.getMessage());
            return criarProva(model);
        }
    }

    // API PARA ADICIONAR DISCIPLINA À PROVA
    @PostMapping("/editar/{id}/adicionar-disciplina")
    public String adicionarDisciplina(@PathVariable int id, @ModelAttribute ProvaDisciplinaDTO provaDisciplinaDTO) {
        provaDisciplinaDTO.setProva(provaService.buscarPorId(id));
        provaService.adicionarDisciplina(provaDisciplinaDTO);
        return "redirect:/provas/editar/" + id;
    }

    // API PARA ADICIONAR QUESTÃO À PROVA
    @PostMapping("/editar/{id}/adicionar-questao")
    public String adicionarQuestao(@PathVariable int id, @ModelAttribute ProvaQuestaoDTO provaQuestaoDTO) {
        provaService.adicionarQuestao(provaQuestaoDTO);
        return "redirect:/provas/editar/" + id;
    }

    // API PARA ATUALIZAR PROVA
    @PostMapping("/atualizar/{id}")
    public String atualizarProva(@PathVariable int id, @ModelAttribute ProvaDTO provaDTO) {
        provaService.atualizar(id, provaDTO);
        return "redirect:/provas/criar";
    }

    // API PARA EXCLUIR PROVA
    @GetMapping("/excluir/{id}")
    public String excluirProva(@PathVariable int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ProvaDTO provaDTO = new ProvaDTO(provaService.buscarPorId(id));
        
        // Verificar permissão
        if (!isCoordenador(auth) && !provaDTO.getProfessor().getEmail().equals(auth.getName())) {
            return "redirect:/provas/criar";
        }

        provaService.excluir(id);
        return "redirect:/provas/criar";
    }

    // API PARA BUSCAR DISCIPLINAS POR CURSO (usada no frontend)
    @ResponseBody
    @GetMapping("/api/disciplinas/por-curso/{cursoId}")
    public List<Disciplina> listarDisciplinasPorCurso(@PathVariable int cursoId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Professor> professorOpt = professorRepository.findByEmail(auth.getName());
        
        if (professorOpt.isEmpty()) {
            return List.of();
        }
        
        Professor professorLogado = professorOpt.get();

        List<Disciplina> disciplinas = disciplinaService.buscarPorCursoId(cursoId);
        
        // Professor só vê suas disciplinas, coordenador vê todas
        if (!isCoordenador(auth)) {
            disciplinas = disciplinas.stream()
                    .filter(disciplina -> professorLogado.getDisciplinas().contains(disciplina))
                    .toList();
        }
        
        return disciplinas;
    }

    private boolean isCoordenador(Authentication authentication) {
        return authentication.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_COORDENADOR"));
    }
}