/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Junior
 */

public class Conexion {

    static final String NAME = "la_perla";
    static final String USER = "root";
    static final String PASS = "1234";
    static final String PORT = "3306";
    static final String TIMEZONE = "UTC";

    Connection con;

    public Connection getConexion() throws SQLException {
        try {
            String db = "jdbc:mysql://localhost:" + PORT + "/" + NAME + "?serverTimezone=" + TIMEZONE;
            con = DriverManager.getConnection(db, USER, PASS);
            return con;
        } catch (SQLException e) {
            // Lanzar la excepción
//            throw new SQLException("No se pudo establecer la conexión a la base de datos", e);
            mostrarAlerta("error",e.toString(),"");
        }
        return null;
    }
    public static Connection getConnection() {

        return null; // Aquí debes devolver la conexión real
    }
    private void mostrarAlerta(String titulo, String mensaje, String detalles) {
        Alert alerta = new Alert(AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.setContentText(detalles);
        alerta.showAndWait();
    }
}
