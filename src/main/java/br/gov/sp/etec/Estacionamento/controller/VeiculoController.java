package br.gov.sp.etec.Estacionamento.controller;

import br.gov.sp.etec.Estacionamento.entity.VeiculoEntity;
import br.gov.sp.etec.Estacionamento.model.Veiculo;
import br.gov.sp.etec.Estacionamento.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("veiculo")
public class VeiculoController {
    @Autowired
    VeiculoService service;
    @PostMapping("cadastro")
    public String cadastrar(Veiculo x, RedirectAttributes redirectAttributes){
        service.cadastrarVeiculo(x);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Entrada registrada com sucesso!");
        return "redirect:/painel?tab=veiculos";
    }

    @GetMapping("registrarSaida")
    public String telaRegistrarSaida(Model model){
        model.addAttribute("veiculosEstacionados", service.listarVeiculosEstacionados());
        return "registrarSaida";
    }

    @PostMapping("registrarSaida")
    public String processarSaida(Long id, Model model, RedirectAttributes redirectAttributes){
        VeiculoEntity atualizado = service.registrarSaida(id);
        if (atualizado == null) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Não foi possível registrar a saída. Selecione uma placa válida.");
            return "redirect:/veiculo/registrarSaida";
        }
        model.addAttribute("veiculo", atualizado);
        return "saidaRegistrada";
    }

    @GetMapping("registarEntrada")
    public String registrarEntrada(){
        return "registrarEntrada";
    }
}
