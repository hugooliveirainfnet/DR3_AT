import at.java.dtos.UsuarioDTOInput;
import at.java.dtos.UsuarioDTOOutput;
import at.java.models.Usuario;
import at.java.services.UsuarioService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UsuarioControllerIntegrationTest {

    private HttpURLConnection connection;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static Usuario usuario = new Usuario();

    public void inicializaConexao(String urlString, String metodo) throws IOException {
        URL url = new URL(urlString);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(metodo);
    }
    public String retornaDadosConexao() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuffer stringBuffer = new StringBuffer();
        String inputLine = bufferedReader.readLine();
        while ( inputLine != null) {
            stringBuffer.append(inputLine);
            inputLine = bufferedReader.readLine();
        }
        bufferedReader.close();
        return stringBuffer.toString();
    }

    @BeforeClass
    public static void buscaUsuarioExterno() throws IOException {
        URL url = new URL("https://randomuser.me/api/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuffer stringBuffer = new StringBuffer();
        String inputLine = bufferedReader.readLine();
        while ( inputLine != null) {
            stringBuffer.append(inputLine);
            inputLine = bufferedReader.readLine();
        }
        bufferedReader.close();
        String json = stringBuffer.toString();
        UsuarioDTOTest dtoTest = objectMapper.readValue(json,UsuarioDTOTest.class);

        String value = dtoTest.getResults().get(0).getId().getValue();
        if(value != null && !value.isEmpty()) {
            String id = value.replaceAll("[^0-9]", "");
            if(!id.isEmpty())
                usuario.setId( Long.valueOf( id ) );
            else
                usuario.setId(1L);
        }
        else
            usuario.setId(1L);

        usuario.setNome( dtoTest.getResults().get(0).getLogin().getUsername() );
        usuario.setSenha( dtoTest.getResults().get(0).getLogin().getPassword() );

        conn.disconnect();

    }

    @After
    public void finalizaConexao(){
        connection.disconnect();
    }

    @Test
    public void esperaStatusCode201AoInserirNovoUsuario() throws IOException {
        inicializaConexao("http://localhost:8080/usuarios", "POST");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

//        Usuario usuario = new Usuario(1, "Hugo", "123");

        String responseJson = objectMapper.writeValueAsString(usuario);
        connection.getOutputStream().write(responseJson.getBytes());

        assertEquals(201, connection.getResponseCode());

    }

    @Test
    public void esperaStatusCode200EQueAListaNaoEstejaVaziaAposInserirUmUsuario() throws IOException {
        esperaStatusCode201AoInserirNovoUsuario();
        inicializaConexao("http://localhost:8080/usuarios", "GET");
        assertEquals(200, connection.getResponseCode());

        List<UsuarioDTOOutput> dtoOutput = objectMapper.readValue(retornaDadosConexao(),
                new TypeReference<List<UsuarioDTOOutput>>(){});

        assertNotEquals(0, dtoOutput.size());
    }

    @Test
    public void esperaStatusCode200EoRetornoSejaIgualAoUsuarioInserido() throws IOException {
        esperaStatusCode201AoInserirNovoUsuario();

        inicializaConexao("http://localhost:8080/usuarios/"+usuario.getId(), "GET");
        assertEquals(200, connection.getResponseCode());

        UsuarioDTOOutput dtoOutput = objectMapper.readValue(retornaDadosConexao(), UsuarioDTOOutput.class);

        assertEquals(usuario.getId(), dtoOutput.getId());
    }

    @Test
    public void esperaStatusCode204AoModificarUmUsuarioExistenteEORetornoSejaIgualAoUsuarioModificado() throws IOException {
        esperaStatusCode201AoInserirNovoUsuario();
        inicializaConexao("http://localhost:8080/usuarios", "PUT");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        usuario.setNome("usuario Modificado");

        String responseJson = objectMapper.writeValueAsString(usuario);
        connection.getOutputStream().write(responseJson.getBytes());

        assertEquals(204, connection.getResponseCode());

        inicializaConexao("http://localhost:8080/usuarios/"+usuario.getId(), "GET");

        UsuarioDTOOutput dtoOutput = objectMapper.readValue(retornaDadosConexao(), UsuarioDTOOutput.class);

        assertEquals(usuario.getNome(), dtoOutput.getNome());

    }

    @Test
    public void esperaStatusCode204AoExcluirUmUsuario() throws IOException {
        esperaStatusCode201AoInserirNovoUsuario();
        inicializaConexao("http://localhost:8080/usuarios/"+usuario.getId(), "DELETE");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

       assertEquals(204, connection.getResponseCode());

    }
}
