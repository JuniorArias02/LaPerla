package Dao;

import java.util.Date;
import java.util.List;

public class Ventas {
    private Long codigoVenta;
    private Date fecha;
    private Long cliente;
    private Long monto;
    private List<DetalleVenta> detalles;
    private String nombreCliente;

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Ventas() {

    }

    public Ventas(Long codigoVenta, Date fecha, Long cliente, Long monto) {
        this.codigoVenta = codigoVenta;
        this.fecha = fecha;
        this.cliente = cliente;
        this.monto = monto;

    }


    public Long getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(Long codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getCliente() {
        return cliente;
    }

    public void setCliente(Long cliente) {
        this.cliente = cliente;
    }

    public Long getMonto() {
        return monto;
    }

    public void setMonto(Long monto) {
        this.monto = monto;
    }



    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }
}
