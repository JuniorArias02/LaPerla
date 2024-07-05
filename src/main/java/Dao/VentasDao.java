package Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentasDao {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;


    public ObservableList<Ventas> ListaVentas(String valor) {
        ObservableList<Ventas> ventasList = FXCollections.observableArrayList();
        String sql ="select * from ventas";
        String buscar = "SELECT * FROM ventas WHERE codigo LIKE ? OR  cliente LIKE ?";
        PreparedStatement ps;
        ResultSet rs;
        Connection con;
        try {
            con = cn.getConexion();
            if (valor == null || valor.isEmpty()) {
                ps = con.prepareStatement(sql);
            } else {
                ps = con.prepareStatement(buscar);
                ps.setString(1, valor + "%");
                ps.setString(2, valor + "%");
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                Ventas vent = new Ventas();
                vent.setCodigo(rs.getLong("codigo"));
                vent.setFecha(rs.getDate("fecha"));
                vent.setCliente(rs.getLong("cliente"));
                vent.setMonto(rs.getLong("monto"));
                ventasList.add(vent);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Cambia esto a una alerta si es necesario
        }
        return ventasList;
    }

}
