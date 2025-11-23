package br.edu.fatecguarulhos.projetoavalia.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.fatecguarulhos.projetoavalia.dto.ProfessorAtualizarDTO;
import br.edu.fatecguarulhos.projetoavalia.dto.ProfessorCadastroDTO;
import br.edu.fatecguarulhos.projetoavalia.dto.ProfessorDetalheDTO;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Professor;
import br.edu.fatecguarulhos.projetoavalia.repository.CursoRepository;
import br.edu.fatecguarulhos.projetoavalia.repository.DisciplinaRepository;
import br.edu.fatecguarulhos.projetoavalia.repository.ProfessorRepository;
import br.edu.fatecguarulhos.projetoavalia.service.DisciplinaService;
import br.edu.fatecguarulhos.projetoavalia.service.ProfessorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/users")
public class UsuarioController {

	@Autowired
    private ProfessorRepository professorRepository;
	
	@Autowired
    private ProfessorService professorService;
	
	@Autowired
    private CursoRepository cursoRepository;
	
	@Autowired
    private DisciplinaRepository disciplinaRepository;
	
	@Autowired
    private DisciplinaService disciplinaService;
	

	@GetMapping("/painel")
	public String painelUsuarios(Model model, Authentication authentication,
	                             HttpServletRequest request, HttpServletResponse response) throws Exception {

	    //Se não tem auth  volta pro login
	    if (authentication == null) {
	        return "redirect:/login";
	    }

	    String emailSessao = authentication.getName();

	    //Buscar no banco
	    Professor professorBanco = professorRepository.findByEmail(emailSessao).orElse(null);

	    //e não existe mais
	    if (professorBanco == null) {
	        request.getSession().invalidate();
	        response.sendRedirect("/login?expirada");
	        return null;
	    }

	    //Se está inativo  derruba sessão
	    if (!professorBanco.isAtivo()) {
	        request.getSession().invalidate();
	        response.sendRedirect("/login?inativo");
	        return null;
	    }

	    //Se email mudou  derruba sessão
	    if (!professorBanco.getEmail().equals(emailSessao)) {
	        request.getSession().invalidate();
	        response.sendRedirect("/login?emailAlterado");
	        return null;
	    }

	    //Se passou em todas verificações  entra no painel
	    List<ProfessorDetalheDTO> professores = professorService.listarTodosDetalhado();

	    model.addAttribute("professores", professores);
	    model.addAttribute("usuarioLogado", professorBanco);
	    model.addAttribute("pageTitle", "Gerenciar usuários");

	    return "painelUsuarios";
	}





	 
	@GetMapping("/cadastrar")
	public String criarUsuario(Model model) {

	    var cursos = cursoRepository.findAll();
	    var disciplinas = disciplinaRepository.findAll();

	    System.out.println("Cursos carregados: " + cursos.size());
	    System.out.println("Disciplinas carregadas: " + disciplinas.size());

	    model.addAttribute("professor", new ProfessorCadastroDTO());
	    model.addAttribute("listaCursos", cursos);
	    model.addAttribute("listaDisciplinas", disciplinas);
	    return "formularioUsuario";
	}

	
	@PostMapping("/cadastrar")
	public String salvarNovo(
	        @ModelAttribute ProfessorCadastroDTO dto,
	        RedirectAttributes redirectAttributes) {

	    try {
	        professorService.criarProfessor(dto);
	        redirectAttributes.addFlashAttribute("successMessage", "Usuário cadastrado com sucesso!");
	        return "redirect:/users/painel";

	    } catch (IllegalStateException e) {
	        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
	        return "redirect:/users/cadastrar";
	    }
	}

	
	
	
	
	
	
	@GetMapping("/editar/{id}")
	public String editarUsuario(@PathVariable int id, Model model) {

	    Professor professor = professorRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

	    // Monta o DTO de atualização preenchido
	    ProfessorAtualizarDTO dto = new ProfessorAtualizarDTO();
	    dto.setId(id); //  <-- ADICIONAR ESTA LINHA
	    dto.setNome(professor.getNome());
	    dto.setEmail(professor.getEmail());
	    dto.setRe(professor.getRe());
	    dto.setCoordenador(professor.isCoordenador());
	    dto.setAtivo(professor.isAtivo());

	    dto.setIdsDisciplinas(
	            professor.getDisciplinas().stream().map(d -> d.getId()).toList()
	    );
	    dto.setIdsCursos(
	            professor.getCursos().stream().map(c -> c.getId()).toList()
	    );

	    model.addAttribute("pageTitle", "Editar usuário");
	    model.addAttribute("professor", dto);
	    model.addAttribute("idProfessor", id);
	    model.addAttribute("listaCursos", cursoRepository.findAll());
	    model.addAttribute("listaDisciplinas", disciplinaRepository.findAll());

	    return "formularioUsuario";
	}
	 
	 
	@PostMapping("/editar/{id}")
	public String salvarEdicao(@PathVariable int id,
	                           @ModelAttribute ProfessorAtualizarDTO dto,
	                           HttpServletRequest request,
	                           RedirectAttributes redirectAttributes) {

	    Professor atualizado;

	    try {
	        atualizado = professorService.atualizarProfessor(id, dto);

	    } catch (IllegalStateException e) { 
	        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
	        return "redirect:/users/editar/" + id;
	    }

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String emailLogado = auth.getName();

	    if (atualizado.getEmail().equals(emailLogado)) {
	        request.getSession().invalidate();
	        SecurityContextHolder.clearContext();
	        return "redirect:/login";
	    }

	    redirectAttributes.addFlashAttribute("successMessage", "Alterações salvas com sucesso!");
	    return "redirect:/users/painel";
	}


	 
	 
	 @GetMapping("/excluir/{id}")
	 public String excluirProfessor(@PathVariable int id, RedirectAttributes redirectAttributes) {
	     try {
	         professorService.excluirProfessor(id);
	         redirectAttributes.addFlashAttribute("successMessage", "Usuário excluído com sucesso!");
	     } catch (IllegalStateException e) {
	    	 redirectAttributes.addFlashAttribute("errorMessage", "Este professor possui questões cadastradas e não pode ser excluído.");
	     }
	     return "redirect:/users/painel";
	 }

	 @ResponseBody
	 @GetMapping("/disciplinas/por-cursos")
	 public List<Disciplina> listarDisciplinasPorCursos(@RequestParam List<Integer> cursosIds) {
	     return disciplinaService.buscarPorCursosIds(cursosIds);
	 }


	
}
