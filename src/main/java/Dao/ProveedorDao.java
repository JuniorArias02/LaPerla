package Dao;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProveedorDao {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public Proveedor agregarProveedor(int codigo, String nombre, long telefono) throws IOException {
        System.out.println("agregarProveedor llamado");
        String checkCodigoSql = "SELECT COUNT(*) FROM proveedor WHERE codigo = ?";
        String checkTelefonoSql = "SELECT COUNT(*) FROM proveedor WHERE telefono = ?";
        String insertSql = "INSERT INTO proveedor (codigo, nombre, telefono) VALUES (?, ?, ?)";
        Proveedor prov = new Proveedor();

        try {
            con = cn.getConexion();

            // Verificar si el código ya existe
            ps = con.prepareStatement(checkCodigoSql);
            ps.setInt(1, codigo);
            rs = ps.executeQuery();
            rs.next();
            int countCodigo = rs.getInt(1);
            if (countCodigo > 0) {
                // Código ya existe, manejar según necesidad (por ejemplo, lanzar una excepción)
                throw new IOException("El código ya existe. No se puede insertar el registro.");
            }

            // Verificar si el teléfono ya existe
            ps = con.prepareStatement(checkTelefonoSql);
            ps.setLong(1, telefono);
            rs = ps.executeQuery();
            rs.next();
            int countTelefono = rs.getInt(1);
            if (countTelefono > 0) {
                mostrarErrorNumeroExistente();
            }
            // Código y teléfono no existen, proceder con la inserción
            ps = con.prepareStatement(insertSql);
            ps.setInt(1, codigo);
            ps.setString(2, nombre);
            ps.setLong(3, telefono);
            ps.executeUpdate();
            // Establecer los valores del objeto Proveedor
            prov.setCodigoProveedor(codigo);
            prov.setNombreProveedor(nombre);
            prov.setTelefonoProveedor(telefono);
        } catch (SQLException e) {
            mostrarAlerta("error", e.toString(), "");

        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                mostrarAlerta("error", e.toString(), "");
            }
        }
        return prov;
    }


    public void modificarProveedor(int codigo, String nuevoNombre, long nuevoTelefono) {
        String modifySql = "UPDATE proveedor SET nombre = ?, telefono = ? WHERE codigo = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(modifySql);
            ps.setString(1, nuevoNombre);
            ps.setLong(2, nuevoTelefono);
            ps.setInt(3, codigo);
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Proveedor modificado exitosamente.");
            } else {
                System.out.println("No se encontró el proveedor con el código especificado.");
            }

        } catch (SQLException e) {
            mostrarAlerta("error", e.toString(), "");
        }
    }

    public void eliminarProveedor(int codigo) {
        String deleteSql = "DELETE FROM proveedor WHERE codigo = ?";

        try {
            con = cn.getConexion();
            ps = con.prepareStatement(deleteSql);
            ps.setInt(1, codigo);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                mostrarAlerta("error", "exito", "");
            } else {
                mostrarAlerta("error", "error", "");
            }
        } catch (SQLException e) {
            mostrarAlerta("error", e.toString(), ""); // Mostrar mensaje de error si hay una excepción SQL
        }
    }

    public Proveedor buscarProveedor(int codigo) {
        String buscarSql = "SELECT codigo, nombre, telefono FROM proveedor WHERE codigo = ?";
        Proveedor prov = null;

        try {
            con = cn.getConexion();
            ps = con.prepareStatement(buscarSql);
            ps.setInt(1, codigo);
            rs = ps.executeQuery();

            if (rs.next()) {
                prov = new Proveedor();
                prov.setCodigoProveedor(rs.getInt("codigo"));
                prov.setNombreProveedor(rs.getString("nombre"));
                prov.setTelefonoProveedor(rs.getLong("telefono"));
            }
        } catch (SQLException e) {
            mostrarAlerta("error", e.toString(), ""); // Manejar excepción y mostrar alerta
        }

        return prov;
    }
    public ObservableList<Proveedor> ListaProveedor(String valor) {
        ObservableList<Proveedor> proveedorList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM proveedor ORDER BY codigo ASC";
        String buscar = "SELECT * FROM proveedor WHERE codigo LIKE ? OR nombre LIKE ? OR telefono LIKE ?";
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
                ps.setString(3, valor + "%");
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                Proveedor prov = new Proveedor();
                prov.setCodigoProveedor(rs.getInt("codigo"));
                prov.setNombreProveedor(rs.getString("nombre"));
                prov.setTelefonoProveedor(rs.getLong("telefono"));
                proveedorList.add(prov);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Cambia esto a una alerta si es necesario
        }
        return proveedorList;
    }

    public Map<String, Proveedor> obtenerProveedores() throws SQLException {
        Map<String, Proveedor> proveedores = new HashMap<>();
        String query = "SELECT codigo, nombre FROM proveedor";
        con = cn.getConexion();
        try (PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");
                Proveedor proveedor = new Proveedor(codigo, nombre);
                proveedores.put(nombre, proveedor);
            }
        }
        // Aquí iría tu lógica para obtener los proveedores de la base de datos
        return proveedores;
    }


    private void mostrarAlerta(String titulo, String mensaje, String detalles) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.setContentText(detalles);
        alerta.showAndWait();
    }


    /**
     * @throws IOException
     */
    private void mostrarErrorNumeroExistente() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/alertas/TelefonoExistente.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> stage.close()));
        timeline.play();
    }

    private void mostrarErrorConexionError() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/alertas/ErrorConexionBD.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> stage.close()));
        timeline.play();
    }


}
