package br.gov.sp.etec.Estacionamento.service;

import br.gov.sp.etec.Estacionamento.entity.VeiculoEntity;
import br.gov.sp.etec.Estacionamento.model.Veiculo;

import java.util.List;

public interface VeiculoService {
    public void cadastrarVeiculo(Veiculo veiculo);
    public List<VeiculoEntity> listarVeiculo();
    public List<VeiculoEntity> listarVeiculosEstacionados();
    public boolean deletarVeiculo(Long id);
    public VeiculoEntity atualizarVeiculo(VeiculoEntity veiculo);
    public VeiculoEntity registrarSaida(Long id);


}
