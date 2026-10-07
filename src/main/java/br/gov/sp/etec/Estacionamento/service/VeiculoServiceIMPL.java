package br.gov.sp.etec.Estacionamento.service;

import br.gov.sp.etec.Estacionamento.entity.VeiculoEntity;
import br.gov.sp.etec.Estacionamento.model.Usuario;
import br.gov.sp.etec.Estacionamento.model.Veiculo;
import br.gov.sp.etec.Estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Service
public class VeiculoServiceIMPL implements VeiculoService {
    @Autowired
    VeiculoRepository repository;
    @Override
    public void cadastrarVeiculo(Veiculo veiculo) {
        repository.save(retornaVeiculoEtt(veiculo));
    }
    private VeiculoEntity retornaVeiculoEtt(Veiculo veiculo ){
        VeiculoEntity entity = new VeiculoEntity();
        entity.setHoraEntrada(LocalDateTime.now());
        entity.setEstacionado(true);
        entity.setCor(veiculo.getCor());
        entity.setModelo(veiculo.getModelo());
        entity.setObservacoes(veiculo.getObservacoes());
        entity.setPlaca(veiculo.getPlaca());
        return entity;
    }
    @Override
    public List<VeiculoEntity> listarVeiculo() {
        List<VeiculoEntity> veiculos = repository.findAll();
        return veiculos;
    }

    @Override
    public List<VeiculoEntity> listarVeiculosEstacionados() {
        return repository.findAll().stream()
                .filter(VeiculoEntity::isEstacionado)
                .sorted(Comparator.comparing(VeiculoEntity::getId))
                .toList();
    }

    @Override
    public boolean deletarVeiculo(Long id) {
        if (id == null || !repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    @Override
    public VeiculoEntity atualizarVeiculo(VeiculoEntity veiculo) {
        return repository.save(veiculo);
    }

    @Override
    public VeiculoEntity registrarSaida(Long id) {
        if (id == null) {
            return null;
        }
        VeiculoEntity entity = repository.findById(id).orElse(null);
        if (entity == null || !entity.isEstacionado()) {
            return null;
        }
        entity.setEstacionado(false);
        entity.setHoraSaida(LocalDateTime.now());
        return repository.save(entity);
    }
    @Override
    public long tempoMedioMinutos() {
        return Math.round(repository.findAll().stream()
                .filter(v -> v.getHoraSaida() != null)
                .mapToLong(VeiculoEntity::getMinutosPermanencia)
                .average()
                .orElse(0));
    }
    private List<Veiculo> toListVeiculo(List<VeiculoEntity> entities){
        List<Veiculo> veiculos = new ArrayList<>();
        for (VeiculoEntity v : entities){
            Veiculo veiculo = new Veiculo();
            veiculo.setPlaca(v.getPlaca());
            veiculo.setCor(v.getCor());
            veiculo.setObservacoes(v.getObservacoes());
            veiculo.setModelo(v.getModelo());
            veiculos.add(veiculo);
        }
        return veiculos;
    }
}
