package at.java;

import at.java.controllers.UsuarioController;
import spark.Spark;

public class Main {

    public static void main(String[] args) {
        Spark.port(8080);
        UsuarioController.run();
    }
}