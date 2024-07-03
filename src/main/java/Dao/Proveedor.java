package Dao;

public class Proveedor {
    private int codigoProveedor;
    private String nombreProveedor;
    private long telefonoProveedor;


    public Proveedor() {

    }

    public Proveedor(int codigoProveedor, String nombreProveedor) {
        this.codigoProveedor = codigoProveedor;
        this.nombreProveedor = nombreProveedor;
    }

    public Proveedor(int codigoProveedor, String nombreProveedor, long telefonoProveedor) {
        this.codigoProveedor = codigoProveedor;
        this.nombreProveedor = nombreProveedor;
        this.telefonoProveedor = telefonoProveedor;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public long getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(long telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }

    @Override
    public String toString() {
        return nombreProveedor; // Esto es importante para que el ComboBox muestre el nombre
    }



}
