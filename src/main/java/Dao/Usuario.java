package Dao;

public class Usuario {
    private  int id_usuario;
    private String usuario;
    private String nombre;
    private String gmail;
    private String clave;

    public Usuario() {
    }

    public Usuario(int id_usuario, String usuario, String nombre, String gmail, String clave) {
        this.id_usuario = id_usuario;
        this.usuario = usuario;
        this.nombre = nombre;
        this.gmail = gmail;
        this.clave = clave;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
