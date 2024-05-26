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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Junior
 */
public class EliminarProveedorController implements Initializable {

    @javafx.fxml.FXML
    private TextField nombreProveedor;
    @javafx.fxml.FXML
    private TextField codigoProveedor;
    @javafx.fxml.FXML
    private TextField telefonoProveedor;

    private Proveedor prov;
    private ProveedorDao provDao;
    private ProveedorController provDaoC;
    private PrincipalController pc;

    /**
     * Initializes the controller class.
     */
    public void setProveedorController(ProveedorController proveedorController) {
        this.provDaoC = proveedorController;
    }
    public void setProveedor(Proveedor prov) {
        this.prov = prov;
        this.codigoProveedor.setText(String.valueOf(prov.getCodigoProveedor()));
        this.nombreProveedor.setText(prov.getNombreProveedor());
        this.telefonoProveedor.setText(String.valueOf(prov.getTelefonoProveedor()));

        this.codigoProveedor.setDisable(true);
        this.nombreProveedor.setDisable(true);
        this.telefonoProveedor.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        provDao = new ProveedorDao();

        // Agregar el evento KeyPressed al campo codigoProveedor
        codigoProveedor.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleCodigoProveedorKeyPressed();
            }
        });
    }

    @FXML
    private void handleCodigoProveedorKeyPressed() {
        if (!codigoProveedor.getText().isEmpty()) {
            try {
                int codigo = Integer.parseInt(codigoProveedor.getText());
                Proveedor proveedor = provDao.buscarProveedor(codigo);
                if (proveedor != null) {
                    nombreProveedor.setText(proveedor.getNombreProveedor());
                    telefonoProveedor.setText(String.valueOf(proveedor.getTelefonoProveedor()));
                } else {
                    mostrarAlerta("Proveedor no encontrado", "No se encontró un proveedor con el código proporcionado.", "");
                }
            } catch (NumberFormatException e) {
                mostrarAlerta("Error de formato", "El código del proveedor debe ser un número.", "");
            }
        }
    }


    @javafx.fxml.FXML
    public void cancelarEliminarProveedor(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = stage.getOwner().getScene().getRoot();
        root.setEffect(null);
        stage.close();
    }

    @javafx.fxml.FXML
    public void confirmarEliminarProveedor(ActionEvent actionEvent) throws IOException {
        if (this.codigoProveedor.getText().isEmpty() ||
                this.nombreProveedor.getText().isEmpty() ||
                this.telefonoProveedor.getText().isEmpty()) {
            mostrarInfoCamposVacios();
        } else {
            int codigo = Integer.parseInt(this.codigoProveedor.getText());
            String nombre = this.nombreProveedor.getText();
            long telefono = Long.parseLong(this.telefonoProveedor.getText());

            prov.setCodigoProveedor(codigo);
            prov.setNombreProveedor(nombre);
            prov.setTelefonoProveedor(telefono);

            provDao.eliminarProveedor(codigo);
            mostrarOperacionExitosa();
            limpiarCampos();

            // Actualizar la tabla después de agregar un nuevo proveedor
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            PrincipalController principalController = (PrincipalController) stage.getUserData();
            principalController.proveedorController.cargarDatosEnTabla("");
            Parent root = stage.getOwner().getScene().getRoot();
            root.setEffect(null);
            stage.close();
        }
    }

    private void limpiarCampos() {
        this.codigoProveedor.setText("");
        this.nombreProveedor.setText("");
        this.telefonoProveedor.setText("");
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


}
