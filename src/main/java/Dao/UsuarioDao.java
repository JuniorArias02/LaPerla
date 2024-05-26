package Dao;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

public class UsuarioDao {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public Usuario login(String usuario, String clave) throws IOException {
        String sql = "SELECT * FROM usuario WHERE usuario = ? AND clave = ?";
        Usuario us = new Usuario();
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, clave);
            rs = ps.executeQuery();
            if (rs.next()) {
                us.setId_usuario(rs.getInt("id_usuario"));
                us.setUsuario(rs.getString("usuario"));
                us.setNombre(rs.getString("nombre"));
                us.setGmail(rs.getString("gmail"));
                us.setClave(rs.getString("clave"));
            }
        } catch (SQLException e) {
           // mostrarAlerta("error", e.toString(), "");
            mostrarErrorConexionError();
        }
        return us;
    }

    public Usuario modificarUsuario(int id_usuario, String usuario, String nombre, String gmail, String clave) {
        Usuario us = new Usuario();
        String sql = "UPDATE usuario SET usuario = ?, nombre = ?, gmail = ?, clave = ? WHERE id_usuario = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, nombre);
            ps.setString(3, gmail);
            ps.setString(4, clave);
            ps.setInt(5, id_usuario);
//            pra validar si mas de una fila fue afectada
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Usuario actualizado correctamente.");
            } else {
                System.out.println("No se pudo actualizar el usuario.");
            }
        } catch (SQLException e) {
            mostrarAlerta("error", e.toString(), "");

        }
        return us;
    }

    public Usuario informacion(int id_usuario) {
        Usuario us = new Usuario();
        String sql = "SELECT usuario,nombre,gmail,clave from usuario where id_usuario = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_usuario);
            rs = ps.executeQuery();
            if (rs.next()) {
//                us.setId_usuario(rs.getInt("id_usuario"));
                us.setUsuario(rs.getString("usuario"));
                us.setNombre(rs.getString("nombre"));
                us.setGmail(rs.getString("gmail"));
                us.setClave(rs.getString("clave"));
            }
        } catch (SQLException e) {
            mostrarAlerta("error", e.toString(), "");
        }
        return us;
    }

    public Usuario mostrarNombre(int id_usuario) {
        Usuario us = new Usuario();
        String sql = "SELECT nombre FROM usuario where id_usuario = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id_usuario);
            rs = ps.executeQuery();
            if (rs.next()) {
                us.setNombre(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            mostrarAlerta("error", e.toString(), "");
        }
        return us;
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



    private void mostrarAlerta(String titulo, String mensaje, String detalles) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.setContentText(detalles);
        alerta.showAndWait();
    }


}
