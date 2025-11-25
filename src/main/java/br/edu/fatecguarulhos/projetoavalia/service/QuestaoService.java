package br.edu.fatecguarulhos.projetoavalia.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.edu.fatecguarulhos.projetoavalia.dto.AlternativaDTO;
import br.edu.fatecguarulhos.projetoavalia.dto.QuestaoDTO;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Alternativa;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Curso;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Disciplina;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Professor;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Questao;
import br.edu.fatecguarulhos.projetoavalia.repository.AlternativaRepository;
import br.edu.fatecguarulhos.projetoavalia.repository.CursoRepository;
import br.edu.fatecguarulhos.projetoavalia.repository.DisciplinaRepository;
import br.edu.fatecguarulhos.projetoavalia.repository.ProfessorRepository;
import br.edu.fatecguarulhos.projetoavalia.repository.QuestaoRepository;

@Service
@Transactional
public class QuestaoService {

    @Autowired
    private QuestaoRepository questaoRepository;

    @Autowired
    private AlternativaRepository alternativaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    //PASTA EXTERNA PARA IMAGENS NA EC2
    private final String PASTA_IMAGENS = "/home/ec2-user/imagens/questoes/";
    private final String DIRETORIO_IMAGEM = "/imagens/questoes/";


    //-------------------- CONSULTAS --------------------

    public List<Questao> listarTodas() {
        return questaoRepository.findAll();
    }

    public Questao buscarPorId(int id) {
        return questaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Questão não encontrada com id: " + id));
    }

    public List<Questao> buscarPorCurso(int Id) {
        Curso curso = cursoRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));
        return questaoRepository.findByCurso(curso);
    }

    public List<Questao> buscarPorDisciplina(int disciplinaId) {
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
        return questaoRepository.findByDisciplina(disciplina);
    }

    public List<Questao> buscarPorAutor(int Id) {
        Professor professor = professorRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        return questaoRepository.findByAutor(professor);
    }
    
    public int contarAlternativasPorId(int questaoId) {
    	return alternativaRepository.countByQuestaoId(questaoId);
    }
    
    public List<Questao> listarPorCursosEDisciplinasDoProfessor(Professor professor) {
        return questaoRepository.findByCursosOrDisciplinas(professor.getCursos(), professor.getDisciplinas());
    }
    
    public List<Questao> pesquisar(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return questaoRepository.findAll(); 
        }
        return questaoRepository.pesquisarPorTermo(termo);
    }
    
    public List<Questao> buscarQuestoesPorIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        return questaoRepository.findAllById(ids);
    }
    
//Adicionado por Leonardo para mostrar as questões marcadas na previa da prova
    
    public List<String> buscarSugestoes(String termo) {
        termo = termo.toLowerCase();

        // Busque nas questões que contêm o termo no enunciado
        List<String> sugestoes = questaoRepository.findByEnunciadoContainingIgnoreCase(termo)
                .stream()
                .map(Questao::getEnunciado)
                .toList();

        // Também busque pelas disciplinas
        List<String> disciplinas = questaoRepository.findByDisciplinaNomeContainingIgnoreCase(termo)
                .stream()
                .map(q -> q.getDisciplina().getNome())
                .toList();

        // E pelos cursos
        List<String> cursos = questaoRepository.findByCursoNomeContainingIgnoreCase(termo)
                .stream()
                .map(q -> q.getCurso().getNome())
                .toList();

        // Unir e remover duplicados
        return sugestoes.stream()
                .distinct()
                .limit(10)
                .toList();
    }


    /* adicionado por amanda */
    
    //-------------------- CRUD --------------------

    public Questao salvar(QuestaoDTO dto) {
    	
    	// Pega o professor logado
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Professor autor = professorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));


        Curso curso = cursoRepository.findById(dto.getCurso().getId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        Disciplina disciplina = disciplinaRepository.findById(dto.getDisciplina().getId())
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

        Questao questao = new Questao();
        questao.setEnunciado(dto.getEnunciado());
        questao.setImagem(dto.getImagem());
        questao.setAutor(autor);
        questao.setCurso(curso);
        questao.setDisciplina(disciplina);
        questao.setSimulado(dto.isSimulado());

        Questao questaoSalva = questaoRepository.save(questao);

        // Salvar alternativas vinculadas
        if (dto.getAlternativas() != null) {
        	int i = 0;
            for (AlternativaDTO altDTO : dto.getAlternativas()) {
            	if (!altDTO.getTexto().equals("")) {            		
	                Alternativa alternativa = new Alternativa();
	                alternativa.setTexto(altDTO.getTexto());
	                if (i++ == dto.getAlternativaCorretaIndex()) {
	                	altDTO.setCorreto(true);
	                }
	                alternativa.setCorreto(altDTO.isCorreto());
	                alternativa.setQuestao(questaoSalva);
	                alternativaRepository.save(alternativa);
            	}
            }
        }

        return questaoSalva;
    }

    // Sobrecarga com imagem
    public Questao salvar(QuestaoDTO dto, MultipartFile file) {
        // Salva a questão sem imagem
        Questao questaoSalva = salvar(dto);

        // Salva a imagem com o ID gerado
        if (file != null && !file.isEmpty()) {
            String caminhoImagem = salvarImagem("questao_" + questaoSalva.getId(), file);
            questaoSalva.setImagem(caminhoImagem);
            questaoRepository.save(questaoSalva); // Atualiza o registro com o caminho correto
        }

        return questaoSalva;
    }


    //-------------------- MÉTODO DE SALVAR IMAGEM --------------------

    public String salvarImagem(String nomeImagem, MultipartFile file) {
        try {
            String nomeAntigo = file.getOriginalFilename();
            String extensaoImagem = "";

            if (nomeAntigo != null && nomeAntigo.contains(".")) {
                extensaoImagem = nomeAntigo.substring(nomeAntigo.lastIndexOf("."));
            }

            String novoNomeImagem = nomeImagem + "_" + System.currentTimeMillis() + extensaoImagem;

            Path pastaUpload = Paths.get(PASTA_IMAGENS);
            Files.createDirectories(pastaUpload);

            Files.copy(file.getInputStream(),
                    pastaUpload.resolve(novoNomeImagem),
                    StandardCopyOption.REPLACE_EXISTING);

            return DIRETORIO_IMAGEM + novoNomeImagem;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem: " + e.getMessage());
        }
    }

    
  //-------------------- EXCLUIR IMAGEM NA PARTE DE ATUALIZAR QUESTÃO --------------------
    public void removerImagem(int id) {
        Questao questao = buscarPorId(id);

        // Se tiver imagem salva, tenta excluir do disco
        if (questao.getImagem() != null && !questao.getImagem().isEmpty()) {
            try {
            	String nome = questao.getImagem().replace("/imagens/questoes/", "");
            	Path caminhoImagem = Paths.get(PASTA_IMAGENS + nome);
                Files.deleteIfExists(caminhoImagem);
            } catch (IOException e) {
                System.err.println("Erro ao excluir imagem: " + e.getMessage());
            }
        }

        // Remove referência da imagem no banco
        questao.setImagem(null);
        questaoRepository.save(questao);
    }

    //-------------------- ATUALIZAR --------------------
    public Optional<Questao> atualizar(int id, QuestaoDTO dto) {
        Optional<Questao> questaoOpt = questaoRepository.findById(id);

        if (questaoOpt.isPresent()) {
            Questao questao = questaoOpt.get();

            Professor autor = professorRepository.findById(dto.getAutor().getId())
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

            Curso curso = cursoRepository.findById(dto.getCurso().getId())
                    .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

            Disciplina disciplina = disciplinaRepository.findById(dto.getDisciplina().getId())
                    .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

            questao.setEnunciado(dto.getEnunciado());
            questao.setImagem(dto.getImagem());
            questao.setAutor(autor);
            questao.setCurso(curso);
            questao.setDisciplina(disciplina);
            questao.setSimulado(dto.isSimulado());

            // Atualiza alternativas: remove antigas e salva novas
            alternativaRepository.deleteByQuestao(questao);
            if (dto.getAlternativas() != null) {
            	int i = 0;
                for (AlternativaDTO altDTO : dto.getAlternativas()) {
                	if (!altDTO.getTexto().equals("")) {            		
    	                Alternativa alternativa = new Alternativa();
    	                alternativa.setTexto(altDTO.getTexto());
    	                if (i++ == dto.getAlternativaCorretaIndex()) {
    	                	altDTO.setCorreto(true);
    	                }
    	                alternativa.setCorreto(altDTO.isCorreto());
    	                alternativa.setQuestao(questao);
    	                alternativaRepository.save(alternativa);
                	}
                }
            }

            questaoRepository.save(questao);
        }

        return questaoOpt;
    }
    
    public Optional<Questao> atualizar(int id, QuestaoDTO dto, MultipartFile file) {
        Optional<Questao> questaoOpt = questaoRepository.findById(id);

        if (questaoOpt.isPresent()) {
        	dto.setImagem(salvarImagem("questao_" + id, file));
        }

        return atualizar(id, dto);
    }

    //-------------------- EXCLUIR --------------------
    public void excluir(int id) {
        Optional<Questao> questaoOpt = questaoRepository.findById(id);

        if (questaoOpt.isPresent()) {
            Questao questao = questaoOpt.get();

            if (questao.getImagem() != null) {
                try {
                    String nome = questao.getImagem().replace("/imagens/questoes/", "");
                    Path caminhoImagem = Paths.get(PASTA_IMAGENS + nome);
                    Files.deleteIfExists(caminhoImagem);
                } catch (IOException e) {
                    System.err.println("Não foi possível excluir a imagem: " + e.getMessage());
                }
            }

            alternativaRepository.deleteByQuestao(questao);
            questaoRepository.deleteById(id);
        }
    }
    
}
