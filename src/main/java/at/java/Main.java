package at.java;

import at.java.controllers.UsuarioController;
import at.java.exceptions.UsuarioExceptions;
import at.java.services.UsuarioService;
import spark.Spark;
import static spark.Spark.exception;

public class Main {

    public static void main(String[] args) {
        Spark.port(8080);
        exception(Exception.class, (e, request, response) -> {
            response.status(400);
            response.body("Erro de validação: " + e.getMessage());
        });
        UsuarioController.run();

    }
}