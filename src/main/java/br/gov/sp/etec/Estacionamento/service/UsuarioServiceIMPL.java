package br.gov.sp.etec.Estacionamento.service;

import br.gov.sp.etec.Estacionamento.entity.UsuarioEntity;
import br.gov.sp.etec.Estacionamento.model.Usuario;
import br.gov.sp.etec.Estacionamento.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServiceIMPL implements UsuarioService{
    @Autowired
    UsuarioRepository Repository;

    @Override
    public String CadastroUsuario(Usuario usuario) {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNome(usuario.getNome());
        usuarioEntity.setEmail(usuario.getEmail());
        usuarioEntity.setSenha(usuario.getSenha());
        usuarioEntity.setTelefone(usuario.getTelefone());
        usuarioEntity.setCpf(usuario.getCpf());
        usuarioEntity.setDataNascimento(usuario.getDataNascimento());
        Repository.save(usuarioEntity);
        return "Usuario cadastrado com sucesso!!";
    }

    @Override
    public List<Usuario> ListarUsuario() {
        List<Usuario> usuarios = new ArrayList<>();
        for (UsuarioEntity entity : Repository.findAll()) {
            usuarios.add(toUsuario(entity));
        }
        return usuarios;
    }

    @Override
    public String atualizarUsuario(Usuario usuario) {
        return "";
    }

    @Override
    public String deletarUsuario(Long Id) {
        return "";
    }

    @Override
    public Usuario verificaEmail(String Email) {
        Usuario u = toUsuario(Repository.findByEmail(Email));
        return u;
    }
    private Usuario toUsuario(UsuarioEntity entity) {
        if (entity == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(entity.getEmail());
        usuario.setNome(entity.getNome());
        usuario.setSenha(entity.getSenha());
        usuario.setTelefone(entity.getTelefone());
        usuario.setCpf(entity.getCpf());
        usuario.setDataNascimento(entity.getDataNascimento());
        return usuario;
    }
}
