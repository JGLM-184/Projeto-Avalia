package br.edu.fatecguarulhos.projetoavalia.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Alternativa;
import br.edu.fatecguarulhos.projetoavalia.model.entity.Prova;
import br.edu.fatecguarulhos.projetoavalia.model.entity.ProvaQuestao;
import br.edu.fatecguarulhos.projetoavalia.model.entity.TentativaSimulado;
import br.edu.fatecguarulhos.projetoavalia.repository.AlternativaRepository;
import br.edu.fatecguarulhos.projetoavalia.repository.ProvaRepository;
import br.edu.fatecguarulhos.projetoavalia.repository.TentativaSimuladoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TentativaSimuladoService {

	@Autowired
	private ProvaService provaService;
	
    @Autowired
    private ProvaRepository provaRepository;

    @Autowired
    private AlternativaRepository alternativaRepository;

    @Autowired
    private TentativaSimuladoRepository tentativaRepository;

    public Prova buscarPorCodigo(String codigo) {
        return provaRepository.findByCodigoSimuladoIgnoreCase(codigo)
            .orElseThrow(() -> new RuntimeException("Simulado não encontrado para o código informado."));
    }

    public TentativaSimulado registrarTentativa(
        Map<String, String> params,
        String codigoSimulado
    ) {
        Prova prova = buscarPorCodigo(codigoSimulado);

        String aluno = params.get("nomeAluno");
        int semestre = Integer.parseInt(params.get("semestreAluno"));

        int acertos = 0;

        for (ProvaQuestao questao : provaService.listarQuestoesPorProva(prova.getId())) {

            String param = "q_" + questao.getQuestao().getId();

            if (!params.containsKey(param)) continue;

            int idAlt = Integer.parseInt(params.get(param));

            Alternativa alternativa = alternativaRepository.findById(idAlt).orElse(null);

            if (alternativa != null && alternativa.isCorreto()) {
                acertos++;
            }
        }

        TentativaSimulado tentativa = new TentativaSimulado();
        tentativa.setAluno(aluno);
        tentativa.setSemestre(semestre);
        tentativa.setSimulado(prova);
        tentativa.setTotalAcertos(acertos);
        tentativa.setDataEnvio(LocalDateTime.now());

        return tentativaRepository.save(tentativa);
    }

	public List<TentativaSimulado> buscarPorProva(int id) {
		return tentativaRepository.findBySimuladoOrderByDataEnvioDesc(provaService.buscarPorId(id));
	}

	public void excluir(int id) {
		tentativaRepository.deleteById(id);
	}

	public void excluirPorProva(int id) {
		tentativaRepository.deleteBySimulado(provaService.buscarPorId(id));
		
	}
}
