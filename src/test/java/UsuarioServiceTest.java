import at.java.dtos.UsuarioDTOInput;
import at.java.dtos.UsuarioDTOOutput;
import at.java.models.Usuario;
import at.java.services.UsuarioService;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;

public class UsuarioServiceTest {

    private final ModelMapper modelMapper = new ModelMapper();
    UsuarioService service;
    UsuarioDTOInput usuario = new UsuarioDTOInput();
    UsuarioDTOInput usuario1 = new UsuarioDTOInput();
    UsuarioDTOInput usuario2 = new UsuarioDTOInput();

    @Before
    public void init() {
        service = new UsuarioService();

        usuario.setId(11);
        usuario.setNome("Hugo");
        usuario.setSenha("1234");

        usuario1.setId(12);
        usuario1.setNome("oguh");
        usuario1.setSenha("4321");

        usuario2.setId(11);
        usuario2.setNome("Hugo Repetido");
        usuario2.setSenha("123");
    }

    @Test
    public void testeInsercaoUmUsuario() {
        service.inserir(usuario);
        assertEquals(1, service.listar().size());
    }
    @Test
    public void testeInsercaoUsuarioRepetido() {
        service.inserir(usuario);
        service.inserir(usuario1);
        service.inserir(usuario2);

        assertEquals(2, service.listar().size());
    }

    @Test
    public void testeBuscaUsuario() {
        service.inserir(usuario);
        service.inserir(usuario1);
        UsuarioDTOOutput dtoOutput = modelMapper.map(usuario1, UsuarioDTOOutput.class);
        assertEquals(dtoOutput, service.buscar(12));
    }

    @Test
    public void testeAtualizaUsuario () {
        service.inserir(usuario);
        service.inserir(usuario1);

        usuario1.setNome("novo nome");
        service.alterar(usuario1);

        assertEquals("novo nome", service.buscar(usuario1.getId()).getNome());
    }

    @Test
    public void testeExcluiUsuario() {
        service.inserir(usuario);
        service.inserir(usuario1);
        assertEquals(2, service.listar().size());
        service.excluir(usuario1.getId());
        assertEquals(1, service.listar().size());
        assertEquals(null, service.buscar(usuario1.getId()));
    }

}
