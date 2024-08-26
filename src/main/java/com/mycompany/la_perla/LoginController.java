package com.mycompany.la_perla;

import Dao.*;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class LoginController implements Initializable {

    Conexion conexion = new Conexion();
    Usuario us;
    UsuarioDao usDao;
    //hola como estas

    @FXML
    private Button btniniciarSesion;
    @FXML
    private PasswordField clave;
    @FXML
    private TextField usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usDao = new UsuarioDao();

        mostrarNotificacion("La Perla", "Bienvenido al Software de Inventario La Perla");
    }


    @FXML
    private void IniciarSesion(ActionEvent event) throws IOException {
        if (this.usuario.getText().isEmpty() || this.clave.getText().isEmpty()) {
            mostrarInfoCamposVacios();
        } else {
            String usuarioIngresado = this.usuario.getText();
            String claveIngresada = this.clave.getText();

            // Usar el método login de UsuarioDao
            us = usDao.login(usuarioIngresado, claveIngresada);

            if (us != null) {
                // Configura el ícono y el título del `Stage` principal aquí
                Stage stage = (Stage) btniniciarSesion.getScene().getWindow();
                stage.setTitle("Software La Perla");
                int id_user = us.getId_usuario();
                UsuarioSesion sesion = UsuarioSesion.getInstancia(id_user);
                PrincipalController.setUsuarioSesion(sesion);
                App.setRoot("Principal");
            } else {
                mostrarErrorLogin();
            }
        }
    }

    private void mostrarNotificacion(String titulo, String mensaje) {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();

            // Cargar la imagen .ico desde el classpath
            Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/perlaIcon.png"));

            TrayIcon trayIcon = new TrayIcon(image, "Software La Perla");
            trayIcon.setImageAutoSize(true);  // Ajustar el tamaño de la imagen automáticamente
            trayIcon.setToolTip("Software La Perla");
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println("Error al añadir el icono a la bandeja del sistema");
                return;
            }

            trayIcon.displayMessage(titulo, mensaje, TrayIcon.MessageType.INFO);
        } else {
            System.err.println("El sistema no soporta notificaciones");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, String detalles) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.setContentText(detalles);
        alerta.showAndWait();
    }

    private void validarConexion() {
        try {
            if (conexion != null && conexion.getConexion() != null) {
                mostrarAlerta("Conexión exitosa", "La conexión a la base de datos se ha establecido correctamente, animos", "");
            } else {
                mostrarAlerta("Error de conexión", "No se pudo establecer la conexión a la base de datos", "Verifica tus credenciales y la configuración de la base de datos");
            }
        } catch (SQLException e) {
            // Lanzar una RuntimeException para propagar la excepción y detener la ejecución
            throw new RuntimeException(e);
        }
    }

    private void mostrarInfoCamposVacios() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/alertas/ErrorCamposVacios.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.8), event -> stage.close()));
        timeline.play();
    }

    private void mostrarErrorLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/alertas/ErrorLogin.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.8), event -> stage.close()));
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


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> stage.close()));
        timeline.play();
    }

    private void mostrarOperacionExitosa() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/alertas/OperacionExitosa.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> stage.close()));
        timeline.play();
    }

    private void mostrarOperacionErronea() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/alertas/OperacionErronea.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> stage.close()));
        timeline.play();
    }


    @FXML
    public void OlvidasteLaContrasena(MouseEvent mouseEvent) throws IOException {
        App.setRoot("OlvidasteContrasena");
    }


}
