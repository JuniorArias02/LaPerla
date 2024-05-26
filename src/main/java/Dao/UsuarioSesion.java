package Dao;

public class UsuarioSesion {
    private static UsuarioSesion instancia;
    private int idUsuario;

    private UsuarioSesion(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public static UsuarioSesion getInstancia(int idUsuario) {
        if (instancia == null) {
            instancia = new UsuarioSesion(idUsuario);
        }
        return instancia;
    }

    public static void limpiarInstancia() {
        instancia = null;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
}
