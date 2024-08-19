/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package CrudControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Controllers.ProveedorController;
import Dao.Proveedor;
import Dao.ProveedorDao;
import com.mycompany.la_perla.PrincipalController;
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
public class NuevoProveedorController implements Initializable {
    Proveedor prov;
    ProveedorDao provDao;
    private ProveedorController proveedorController;

    @javafx.fxml.FXML
    private TextField nombreProveedor;
    @javafx.fxml.FXML
    private TextField codigoProveedor;
    @javafx.fxml.FXML
    private TextField telefonoProveedor;

    /**
     * Initializes the controller class.
     */
    public void setProveedorController(ProveedorController proveedorController) {
        this.proveedorController = proveedorController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        provDao = new ProveedorDao();

        // Añadir un filtro para permitir solo letras en el campo de nombre del proveedor
        nombreProveedor.addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[a-zA-Z\\s]")) {
                event.consume();
            }
        });


        // Filtro para permitir solo números en el campo código del cliente
        codigoProveedor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                codigoProveedor.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Filtro para permitir solo números en el campo teléfono del cliente
        telefonoProveedor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                telefonoProveedor.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @javafx.fxml.FXML
    public void cancelarAgregarProveedor(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = stage.getOwner().getScene().getRoot();
        root.setEffect(null);
        stage.close();
    }

    @javafx.fxml.FXML
    public void confirmarAgregarProveedor(ActionEvent actionEvent) throws IOException {
        try {
            if (this.codigoProveedor.getText().isEmpty() ||
                    this.nombreProveedor.getText().isEmpty() ||
                    this.telefonoProveedor.getText().isEmpty()) {
                mostrarErrorCamposVacios();
            } else {
                int codigo = Integer.parseInt(this.codigoProveedor.getText());
                String nombre = this.nombreProveedor.getText();
                Long telefono = Long.valueOf(this.telefonoProveedor.getText());

                prov = provDao.agregarProveedor(codigo, nombre, telefono);
                if (prov != null) {
                    proveedorController.iniciarCargaDatos();
                    mostrarOperacionExitosa();
                    limpiarCampos();
                } else {
                    mostrarOperacionErronea();
                }
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "No se pudo agregar el proveedor: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo agregar el proveedor: " + e.getMessage(), Alert.AlertType.ERROR);
        }

    }


    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        this.codigoProveedor.setText("");
        this.nombreProveedor.setText("");
        this.telefonoProveedor.setText("");


    }

    private void mostrarAlerta(String titulo, String mensaje, String detalles) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.setContentText(detalles);
        alerta.showAndWait();
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

    private void mostrarErrorCamposVacios() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/alertas/ErrorCamposVacios.fxml"));
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
