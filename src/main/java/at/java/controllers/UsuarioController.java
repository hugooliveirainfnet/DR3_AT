package at.java.controllers;

import at.java.dtos.UsuarioDTOInput;
import at.java.dtos.UsuarioDTOOutput;
import at.java.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;
public class UsuarioController {
    private static final UsuarioService service =  UsuarioService.getInstance();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static void run() {

        post("/usuarios", (request, response) -> {
            UsuarioDTOInput dtoInput = objectMapper.readValue(request.body(), UsuarioDTOInput.class);
            service.inserir(dtoInput);

            response.status(201);
            return "";
        });

        put("/usuarios", (request, response) -> {
            UsuarioDTOInput dtoInput = objectMapper.readValue(request.body(), UsuarioDTOInput.class);
            service.alterar(dtoInput);

            response.status(204);
            return "";
        });

        get("/usuarios/:id", (request, response) -> {
            Long id = Long.valueOf(request.params("id"));
            UsuarioDTOOutput dtoOutput = service.buscar(id);
            String responseJson = objectMapper.writeValueAsString(dtoOutput);

            response.status(200);
            return responseJson;
        });

        get("/usuarios", (request, response) -> {
            String responseJson = objectMapper.writeValueAsString(service.listar());

            response.status(200);
            return responseJson;
        });

        delete("/usuarios/:id", (request, response) -> {
            Long id = Long.valueOf(request.params("id"));
            service.excluir(id);

            response.status(204);
            return "";
        });
    }
}
