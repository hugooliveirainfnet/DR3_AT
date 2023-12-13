package at.java.services;

import at.java.models.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();

    public void inserir(Usuario usuario) {
        if(!usuarios.contains(usuario))
            usuarios.add(usuario);
    }
    public void alterar(Usuario usuario) {
        var usuarioExiste = buscar(usuario.getId());

        if(usuarioExiste != null) {
            for (Usuario usu : usuarios) {
                if (usu.getId() == usuario.getId()) {
                    usu.setNome(usuario.getNome());
                    usu.setSenha(usuario.getSenha());
                }
            }
        }
    }
    public Usuario buscar(int id) {
        Optional<Usuario> usu = usuarios.stream().filter(usuario -> usuario.getId() == id ).findFirst();
        if(usu.isPresent()) return usu.get();

        return null;
    }
    public List<Usuario> listar() {
        return usuarios;
    }
    public void excluir(int id) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getId() == id) {
                iterator.remove();
                return;
            }
        }
    }
}
