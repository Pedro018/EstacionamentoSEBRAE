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
        List<VeiculoEntity> veiculos = veiculoService.listarVeiculo();
        long totalEstacionados = veiculos.stream().filter(VeiculoEntity::isEstacionado).count();
        model.addAttribute("veiculos", veiculos);
        model.addAttribute("totalEstacionados", totalEstacionados);
        return "painel";
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
