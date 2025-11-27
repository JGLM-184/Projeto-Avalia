package br.edu.fatecguarulhos.projetoavalia.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.fatecguarulhos.projetoavalia.model.entity.Prova;
import br.edu.fatecguarulhos.projetoavalia.model.entity.ProvaDisciplina;
import br.edu.fatecguarulhos.projetoavalia.model.entity.ProvaQuestao;
import br.edu.fatecguarulhos.projetoavalia.model.entity.TentativaSimulado;
import br.edu.fatecguarulhos.projetoavalia.service.ProvaService;
import br.edu.fatecguarulhos.projetoavalia.service.TentativaSimuladoService;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

	@Autowired
	private TentativaSimuladoService tentativaSimuladoService;
	
    @Autowired
    private ProvaService provaService;

    @GetMapping("/voltar-login")
    public String voltarLogin() {
        return "login";
    }
    
    @GetMapping("/buscar-simulado")
    public String procurarSimulado() {
        return "buscarSimulado";
    }

    @GetMapping("/fazer-simulado")
    public String fazerSimulado(
            @RequestParam("codigo") String codigoSimulado,
            Model model
    ) {

        Optional<Prova> provaOpt =
                provaService.buscaPorCodigoSimulado(codigoSimulado);

        if (provaOpt.isEmpty()) {
            model.addAttribute("erro", "Código inválido ou simulado não encontrado.");
            return "buscarSimulado";
        }

        Prova prova = provaOpt.get();

        // Carregar questões da prova
        List<ProvaQuestao> provaQuestoes =
                provaService.listarQuestoesPorProva(prova.getId());

        // Carregar disciplinas associadas
        List<ProvaDisciplina> disciplinasProva =
                provaService.listarDisciplinasPorProva(prova.getId());

        model.addAttribute("prova", prova);
        model.addAttribute("questoes", provaQuestoes);
        model.addAttribute("disciplinas", disciplinasProva);
        model.addAttribute("totalQuestoes", provaQuestoes.size());

        return "simulado";
    }

    @PostMapping("/envio-simulado")
    public String envioSimulado(
            @RequestParam Map<String, String> params,
            Model model
    ) {
        try {
            String codigo = params.get("codigoSimulado");

            TentativaSimulado tentativa = tentativaSimuladoService.registrarTentativa(params, codigo);

            model.addAttribute("acertos", tentativa.getTotalAcertos());
            model.addAttribute("total", provaService.listarQuestoesPorProva(tentativa.getSimulado().getId()).size());

            return "envioSimulado";

        } catch (Exception e) {
        	model.addAttribute("erro", "Ocorreu um erro durante o envio das respostas.");
            return "buscar-simulado";
        }
    }
}
