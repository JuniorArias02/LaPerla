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

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Random;

public class UsuarioDao {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    private Connection connection;

    public UsuarioDao() {
        try {
            this.connection = new Conexion().getConexion(); // Inicializa la conexión aquí
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UsuarioDao(Connection connection) {
        this.connection = connection;
    }

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



    public boolean validarUsuarioYCorreo(String usuario, String gmail) throws SQLException {
        String query = "SELECT id_usuario FROM usuario WHERE usuario = ? AND gmail = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, usuario);
            stmt.setString(2, gmail);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public String generarCodigoVerificacion(int longitud) {
        Random random = new Random();
        StringBuilder codigo = new StringBuilder(longitud);
        for (int i = 0; i < longitud; i++) {
            codigo.append(random.nextInt(10));
        }
        return codigo.toString();
    }

    public void almacenarCodigoVerificacion(int idUsuario, String codigo) throws SQLException {
        String query = "INSERT INTO codigo_verificacion (id_usuario, codigo, fecha_expiracion) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            stmt.setString(2, codigo);
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis() + 3600 * 1000)); // 1 hora de expiración
            stmt.executeUpdate();
        }
    }

    public void enviarCorreo(String toEmail, String codigo) {
        final String fromEmail = "proyectolaperla18@gmail.com"; // Correo de remitente
        final String password = "gmxs sofw ikvp zwuw"; // Contraseña del remitente

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Código de Verificación");
            message.setText("Tu código de verificación es: " + codigo);

            Transport.send(message);
            System.out.println("Correo enviado exitosamente!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void recuperarContrasena(String usuario, String gmail) throws SQLException {
        if (validarUsuarioYCorreo(usuario, gmail)) {
            String codigo = generarCodigoVerificacion(6);
            int idUsuario = obtenerIdUsuarioPorCorreo(gmail);
            almacenarCodigoVerificacion(idUsuario, codigo);
            enviarCorreo(gmail, codigo);
        } else {
            throw new SQLException("Usuario o correo no válidos");
        }
    }

    private int obtenerIdUsuarioPorCorreo(String gmail) throws SQLException {
        String query = "SELECT id_usuario FROM usuario WHERE gmail = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, gmail);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_usuario");
                } else {
                    throw new SQLException("Correo no encontrado");
                }
            }
        }
    }

    public boolean validarCodigo(String codigo) throws SQLException {
        String query = "SELECT id_usuario FROM codigo_verificacion WHERE codigo = ? AND fecha_expiracion > CURRENT_TIMESTAMP";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Método para actualizar la contraseña
    public void actualizarContrasena(String codigo, String nuevaContrasena) throws SQLException {
        // Obtener el id del usuario a partir del código de verificación
        int idUsuario = obtenerIdUsuarioPorCodigo(codigo);

        // Actualizar la contraseña del usuario
        String query = "UPDATE usuario SET clave = ? WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nuevaContrasena);
            stmt.setInt(2, idUsuario);
            stmt.executeUpdate();
        }
    }

    private int obtenerIdUsuarioPorCodigo(String codigo) throws SQLException {
        String query = "SELECT id_usuario FROM codigo_verificacion WHERE codigo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_usuario");
                } else {
                    throw new SQLException("Código de verificación no encontrado");
                }
            }
        }
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
