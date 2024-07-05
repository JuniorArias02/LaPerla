package Dao;

public class Cliente {
    private Long codigo;
    private String nombre;
    private Long telefono;


    public Cliente(){

    }

    public Cliente(Long codigo, String nombre, Long telefono) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return nombre; // Esto es importante para que el ComboBox muestre el nombre
    }
}
