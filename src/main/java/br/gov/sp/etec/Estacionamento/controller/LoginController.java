package br.gov.sp.etec.Estacionamento.controller;

import br.gov.sp.etec.Estacionamento.entity.VeiculoEntity;
import br.gov.sp.etec.Estacionamento.model.Usuario;
import br.gov.sp.etec.Estacionamento.service.UsuarioService;
import br.gov.sp.etec.Estacionamento.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @GetMapping("/cadastrar")
    public String cadastrar() {
        return "cadastro";
    }

    @GetMapping("/painel")
    public String painel(Model model) {
        List<VeiculoEntity> estacionados = veiculoService.listarVeiculosEstacionados();
        model.addAttribute("veiculos", estacionados);
        model.addAttribute("totalEstacionados", estacionados.size());
        adicionarContadores(model, estacionados.size());
        return "painel";
    }

    @GetMapping("/movimentacoes")
    public String movimentacoes(Model model) {
        List<VeiculoEntity> movimentacoes = new ArrayList<>(veiculoService.listarVeiculo());
        movimentacoes.sort((a, b) -> Long.compare(b.getId(), a.getId()));
        model.addAttribute("movimentacoes", movimentacoes);
        model.addAttribute("totalMovimentacoes", movimentacoes.size());
        model.addAttribute("tempoMedio", VeiculoEntity.formatarMinutos(veiculoService.tempoMedioMinutos()));
        return "movimentacoes";
    }

    @GetMapping("/relatorios")
    public String relatorios(Model model) {
        long ocupadas = veiculoService.listarVeiculosEstacionados().size();
        adicionarContadores(model, ocupadas);
        model.addAttribute("totalMovimentacoes", veiculoService.listarVeiculo().size());
        model.addAttribute("tempoMedio", VeiculoEntity.formatarMinutos(veiculoService.tempoMedioMinutos()));
        model.addAttribute("ocupacaoPercentual", Math.round(ocupadas * 100.0 / VeiculoService.TOTAL_VAGAS));
        return "relatorios";
    }

    private void adicionarContadores(Model model, long ocupadas) {
        model.addAttribute("vagasOcupadas", ocupadas);
        model.addAttribute("vagasDisponiveis", Math.max(0, VeiculoService.TOTAL_VAGAS - ocupadas));
        model.addAttribute("totalVagas", VeiculoService.TOTAL_VAGAS);
    }

    @PostMapping("/login")
    public String efetuarLogin(String email, String senha) {
        Usuario usuario = service.verificaEmail(email);

        if (usuario != null && senha != null && senha.equals(usuario.getSenha())) {
            return "redirect:/painel";
        }

        return "erro";
    }

    @PostMapping("/efetuar-cadastro")
    public String efetuarCadastrar(Usuario usuario) {
        service.CadastroUsuario(usuario);
        return "CadastroSucess";
    }
}
