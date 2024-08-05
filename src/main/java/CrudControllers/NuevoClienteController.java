/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package CrudControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Controllers.ClienteController;
import Dao.Cliente;
import Dao.ClienteDao;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Junior
 */
public class NuevoClienteController implements Initializable {
    Cliente cli;
    ClienteDao cliDao;
    ClienteController clienteController;

    @javafx.fxml.FXML
    private TextField nombreCliente;

    @javafx.fxml.FXML
    private TextField codigoCliente;

    @javafx.fxml.FXML
    private TextField telefonoCliente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cliDao = new ClienteDao();

        // Filtro para permitir solo números en el campo código del cliente
        codigoCliente.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                codigoCliente.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Filtro para permitir solo números en el campo teléfono del cliente
        telefonoCliente.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                telefonoCliente.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Filtro para permitir solo letras y espacios en el campo nombre del cliente
        nombreCliente.addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[a-zA-Z\\s]")) {
                event.consume();
            }
        });
    }

    public void setClienteController(ClienteController clienteController) {
        this.clienteController = clienteController;
    }

    @javafx.fxml.FXML
    public void confirmarAgregarCliente(ActionEvent actionEvent) throws IOException {
        if (this.codigoCliente.getText().isEmpty() || this.nombreCliente.getText().isEmpty() || this.telefonoCliente.getText().isEmpty()) {
            mostrarOperacionErronea();
        } else {
            Long codigo = Long.valueOf(this.codigoCliente.getText());
            String nombre = this.nombreCliente.getText();
            Long telefono = Long.valueOf(this.telefonoCliente.getText());

            cli = cliDao.agregarCliente(codigo, nombre, telefono);
            if (cli != null) {
                clienteController.iniciarCargaDatos();
                mostrarOperacionExitosa();
                limpiarCampos();
            } else {
                mostrarOperacionErronea();
            }
        }
    }

    public void limpiarCampos() {
        this.codigoCliente.setText("");
        this.nombreCliente.setText("");
        this.telefonoCliente.setText("");
    }

    @javafx.fxml.FXML
    public void cancelarAgregarCliente(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = stage.getOwner().getScene().getRoot();
        root.setEffect(null);
        stage.close();
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
}
