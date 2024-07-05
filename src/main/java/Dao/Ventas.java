package Dao;

import java.util.Date;

public class Ventas {
    private Long codigo;
    private Date fecha;
    private Long cliente;
    private Long monto;

    public Ventas() {

    }

    public Ventas(Long codigo, Date fecha, Long cliente, Long monto) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.cliente = cliente;
        this.monto = monto;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
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
}
