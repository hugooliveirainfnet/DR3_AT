package at.java.controllers;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;
public class UsuarioController {

    public static void run() {

        post("/usuarios/:id", (request, response) -> {
            return "";
        });
        put("/usuarios/:id", (request, response) -> {
            return "";
        });
        get("/usuarios/:id", (request, response) -> {
            return "";
        });
        get("/usuarios/:id", (request, response) -> {
            return "";
        });
        delete("/usuarios/:id", (request, response) -> {
            return "";
        });
    }
}
