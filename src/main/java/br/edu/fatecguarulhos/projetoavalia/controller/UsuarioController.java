package br.edu.fatecguarulhos.projetoavalia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
import br.edu.fatecguarulhos.projetoavalia.dto.TrocaSenhaDTO;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Professor;
import br.edu.fatecguarulhos.projetoavalia.repository.CursoRepository;
import br.edu.fatecguarulhos.projetoavalia.repository.DisciplinaRepository;
import br.edu.fatecguarulhos.projetoavalia.repository.ProfessorRepository;
import br.edu.fatecguarulhos.projetoavalia.service.DisciplinaService;
import br.edu.fatecguarulhos.projetoavalia.service.ProfessorService;

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
	public String painelUsuarios(Model model, Authentication authentication) {

	    String emailLogado = authentication.getName();

	    Professor usuarioLogado = professorRepository.findByEmail(emailLogado)
	            .orElseThrow(() -> new RuntimeException("Usuário logado não encontrado."));

	    List<ProfessorDetalheDTO> professores = professorService.listarTodosDetalhado();

	    model.addAttribute("professores", professores);
	    model.addAttribute("usuarioLogado", usuarioLogado); // <-- ADD AQUI
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
	public String salvarNovo(@ModelAttribute ProfessorCadastroDTO dto, RedirectAttributes redirectAttributes) {
	    professorService.criarProfessor(dto);
	    redirectAttributes.addFlashAttribute("successMessage", "Usuário cadastrado com sucesso!");
	    return "redirect:/users/painel";
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
	public String salvarEdicao(@PathVariable int id, @ModelAttribute ProfessorAtualizarDTO dto, RedirectAttributes redirectAttributes) {
	    professorService.atualizarProfessor(id, dto);
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

	    
	    
	 @PostMapping("/alterar-senha")
	 public String alterarSenha(@ModelAttribute TrocaSenhaDTO dto,
			 Authentication authentication,
			 Model model) {

		 Professor professor = professorRepository.findByEmail(authentication.getName())
				 .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

		 try {
			 professorService.trocarSenha(professor, dto.getNovaSenha(), dto.getConfirmarSenha());
		 } catch (IllegalArgumentException e) {
			 model.addAttribute("erro", e.getMessage());
			 model.addAttribute("professor", professor);
			 return "index";
		 }

		 return "redirect:/";
	 }
	 
	 @ResponseBody
	 @GetMapping("/disciplinas/por-cursos")
	 public List<Disciplina> listarDisciplinasPorCursos(@RequestParam List<Integer> cursosIds) {
	     return disciplinaService.buscarPorCursosIds(cursosIds);
	 }


	
}
