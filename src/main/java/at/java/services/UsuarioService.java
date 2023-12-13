package at.java.services;

import at.java.dtos.UsuarioDTOInput;
import at.java.dtos.UsuarioDTOOutput;
import at.java.models.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();
    private final ModelMapper modelMapper = new ModelMapper();

    public void inserir(UsuarioDTOInput dtoInput) {
        Usuario usuario = modelMapper.map(dtoInput, Usuario.class);
        if(!usuarios.contains(usuario))
            usuarios.add(usuario);
    }

    public void alterar(UsuarioDTOInput dtoInput) {
        Usuario usuario = modelMapper.map(dtoInput, Usuario.class);

        if(usuarios.contains(usuario)){
            for (Usuario usu : usuarios) {
                if (usu.getId() == usuario.getId()) {
                    usu.setNome(usuario.getNome());
                    usu.setSenha(usuario.getSenha());
                }
            }
        }
    }

    public UsuarioDTOOutput buscar(int id) {
        Optional<Usuario> usu = usuarios.stream().filter(usuario -> usuario.getId() == id ).findFirst();
        if(usu.isPresent()) {
            return modelMapper.map(usu.get(), UsuarioDTOOutput.class);
        }
        return null;
    }

    public List<UsuarioDTOOutput> listar() {
        return usuarios.stream().map(usuario ->
                modelMapper.map(usuario, UsuarioDTOOutput.class)).collect((Collectors.toList()));
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
