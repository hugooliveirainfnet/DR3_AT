package at.java.exceptions;

public class UsuarioExceptions extends Exception {

    public UsuarioExceptions(){
        super();
    }
    public static void inclusaoException() throws Exception {
        throw new RuntimeException("Usuario já cadastrado");
    }
}
