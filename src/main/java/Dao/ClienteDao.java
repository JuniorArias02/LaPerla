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

public class ClienteDao {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;


    public Cliente agregarCliente(Long codigo, String nombre, Long telefono) throws IOException {
        System.out.println("agregarCliente llamado");
        String checkCodigoSql = "SELECT COUNT(*) FROM cliente WHERE codigo = ?";
        String checkTelefonoSql = "SELECT COUNT(*) FROM cliente WHERE telefono = ?";
        String insertSql = "INSERT INTO cliente (codigo, nombre, telefono) VALUES (?, ?, ?)";
        Cliente cli = new Cliente();

        try {
            con = cn.getConexion();

            // Verificar si el código ya existe
            ps = con.prepareStatement(checkCodigoSql);
            ps.setLong(1, codigo);
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
            ps.setLong(1, codigo);
            ps.setString(2, nombre);
            ps.setLong(3, telefono);
            ps.executeUpdate();
            // Establecer los valores del objeto Proveedor
            cli.setCodigo(codigo);
            cli.setNombre(nombre);
            cli.setTelefono(telefono);
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
        return cli;
    }


    public void modificarCliente(Long codigo, String nuevoNombre, long nuevoTelefono) {
        String modifySql = "UPDATE cliente SET nombre = ?, telefono = ? WHERE codigo = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(modifySql);
            ps.setString(1, nuevoNombre);
            ps.setLong(2, nuevoTelefono);
            ps.setLong(3, codigo);
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("cliente modificado exitosamente.");
            } else {
                System.out.println("No se encontró el cliente con el código especificado.");
            }

        } catch (SQLException e) {
            mostrarAlerta("error", e.toString(), "");
        }
    }

    public void eliminarCliente(Long codigo) {
        String deleteSql = "DELETE FROM cliente WHERE codigo = ?";

        try {
            con = cn.getConexion();
            ps = con.prepareStatement(deleteSql);
            ps.setLong(1, codigo);
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

    public ObservableList<Cliente> ListaClientes(String valor) {
        ObservableList<Cliente> clienteList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM cliente ORDER BY codigo ASC";
        String buscar = "SELECT * FROM cliente WHERE codigo LIKE ? OR nombre LIKE ? OR telefono LIKE ?";
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
                Cliente cli = new Cliente();
                cli.setCodigo(rs.getLong("codigo"));
                cli.setNombre(rs.getString("nombre"));
                cli.setTelefono(rs.getLong("telefono"));
                clienteList.add(cli);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Cambia esto a una alerta si es necesario
        }
        return clienteList;
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
}
