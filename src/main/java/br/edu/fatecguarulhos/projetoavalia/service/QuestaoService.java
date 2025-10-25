package br.edu.fatecguarulhos.projetoavalia.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

    private final String PASTA_STATIC = "src/main/resources/static";
    private final String PASTA_IMAGENS = "src/main/resources/static/imagens/questoes/";
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

    //-------------------- CRUD --------------------

    public Questao salvar(QuestaoDTO dto) {
        Professor autor = professorRepository.findById(dto.getAutor().getId())
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
        dto.setImagem(salvarImagem(String.valueOf(dto.getId()), file));
        return salvar(dto);
    }

    //-------------------- MÉTODO DE SALVAR IMAGEM --------------------

    public String salvarImagem(String nomeImagem, MultipartFile file) {
        try {
            String nomeAntigo = file.getOriginalFilename();
            String extensaoImagem = "";

            if (nomeAntigo != null && nomeAntigo.contains(".")) {
                extensaoImagem = nomeAntigo.substring(nomeAntigo.lastIndexOf("."));
            }

            String novoNomeImagem = nomeImagem + extensaoImagem;

            Path pastaUpload = Paths.get(PASTA_IMAGENS);
            Files.createDirectories(pastaUpload);

            Files.copy(file.getInputStream(), pastaUpload.resolve(novoNomeImagem), StandardCopyOption.REPLACE_EXISTING);

            return DIRETORIO_IMAGEM + novoNomeImagem;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem: " + e.getMessage());
        }
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
        	dto.setImagem(salvarImagem(String.valueOf(dto.getId()), file));
        }

        return atualizar(id, dto);
    }

    //-------------------- EXCLUIR --------------------
    public void excluir(int id) {
        Optional<Questao> questaoOpt = questaoRepository.findById(id);

        if (questaoOpt.isPresent()) {
            Questao questao = questaoOpt.get();

            // Excluir imagem, se existir
            if (questao.getImagem() != null) {
                try {
                    Path caminhoImagem = Paths.get(PASTA_STATIC + questao.getImagem());
                    Files.deleteIfExists(caminhoImagem);
                } catch (IOException e) {
                    System.err.println("Não foi possível excluir a imagem: " + e.getMessage());
                }
            }

            // Excluir alternativas da questão
            alternativaRepository.deleteByQuestao(questao);

            questaoRepository.deleteById(id);
        }
    }
}
