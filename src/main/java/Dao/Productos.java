package Dao;

public class Productos {
    private int codigoProducto;
    private String nombreProducto;
    private int precioProducto;
    private String categoriaProducto;
    private int codigoProveedorPro;
    private int stockProducto;
    private int idProveedor;
    private String nombreProveedor;

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Productos(int codigoProducto, String nombreProducto, int precioProducto, String categoriaProducto, int codigoProveedorPro, int stockProducto) {
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.categoriaProducto = categoriaProducto;
        this.codigoProveedorPro = codigoProveedorPro;
        this.stockProducto = stockProducto;
    }

    public Productos() {

    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getStockProducto() {
        return stockProducto;
    }

    public void setStockProducto(int stockProducto) {
        this.stockProducto = stockProducto;
    }

    public int getCodigoProveedorPro() {
        return codigoProveedorPro;
    }

    public void setCodigoProveedorPro(int codigoProveedorPro) {
        this.codigoProveedorPro = codigoProveedorPro;
    }

    public String getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(String categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

    public int getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(int precioProducto) {
        this.precioProducto = precioProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
}
