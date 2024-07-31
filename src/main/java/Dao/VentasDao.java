package Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class VentasDao {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;



    public Ventas nuevaVenta(long cliente, long monto) throws SQLException {
        String insertSQL = "INSERT INTO ventas (cliente,monto) VALUES(?,?)";
        con = cn.getConexion();
        Ventas ventas = new Ventas();
        try {
            ps = con.prepareStatement(insertSQL);
            ps.setLong(1, cliente);
            ps.setLong(2, monto);

            ventas.setCliente(cliente);
            ventas.setMonto(monto);

            ps.executeUpdate();

        } catch (SQLException e) {
            mostrarAlerta("error", e.toString(), "");
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                mostrarAlerta("error", e.toString(), "");
            }
        }
        return ventas;
    }

    public void detalle_venta(long producto,int cantidad) throws SQLException {
        String selectSQL = "SELECT MAX(codigo) AS ultimo_id FROM ventas";
        String insertSQL = "INSERT INTO detalle_venta(codigo, producto,cantidad) VALUES (?,?,?)";

        con = cn.getConexion();

        try {
            // Obtener el último ID
            ps = con.prepareStatement(selectSQL);
            rs = ps.executeQuery();

            long ultimoId = 0;
            if (rs.next()) {
                ultimoId = rs.getLong("ultimo_id");
            }

            // Insertar en detalle_venta usando el último ID
            ps = con.prepareStatement(insertSQL);
            ps.setLong(1, ultimoId);
            ps.setLong(2, producto);
            ps.setInt(3,cantidad);

            ps.executeUpdate();

        } catch (SQLException e) {
            mostrarAlerta("error", e.toString(), "");
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                mostrarAlerta("error", e.toString(), "");
            }
        }
    }

    public ObservableList<Ventas> ListaVentas(String valor) {
        ObservableList<Ventas> ventasList = FXCollections.observableArrayList();
        String sql = "select * from ventas";
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
                vent.setCodigoVenta(rs.getLong("codigo"));
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


    private void mostrarAlerta(String titulo, String mensaje, String detalles) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.setContentText(detalles);
        alerta.showAndWait();
    }


}
