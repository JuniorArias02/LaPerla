package Dao;

public class DetalleVenta {
    private long codigo;
    private String producto;


    public DetalleVenta(long codigo, String producto) {
        this.codigo = codigo;
        this.producto = producto;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
}
