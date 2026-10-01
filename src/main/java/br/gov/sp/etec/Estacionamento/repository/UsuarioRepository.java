package br.gov.sp.etec.Estacionamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.gov.sp.etec.Estacionamento.entity.UsuarioEntity;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity,Long> {
    UsuarioEntity findByEmail(String email);
}
