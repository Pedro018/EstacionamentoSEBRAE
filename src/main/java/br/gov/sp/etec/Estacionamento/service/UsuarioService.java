package br.gov.sp.etec.Estacionamento.service;

import br.gov.sp.etec.Estacionamento.model.Usuario;
import java.util.List;

public interface UsuarioService {
    String CadastroUsuario(Usuario usuario);
    List<Usuario> ListarUsuario();
    String atualizarUsuario(Usuario usuario);
    String deletarUsuario(Long Id);
    Usuario verificaEmail(String Email);
}
