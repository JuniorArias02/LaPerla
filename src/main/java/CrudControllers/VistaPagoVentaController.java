/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package CrudControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Junior
 */
public class VistaPagoVentaController implements Initializable {

    @javafx.fxml.FXML
    private TextField nombreCliente;
    @javafx.fxml.FXML
    private TextField cantidadRecibida;
    @javafx.fxml.FXML
    private Label montoTotal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @javafx.fxml.FXML
    public void realizarVentaEfectivo(ActionEvent actionEvent) throws IOException {
        mostrarOperacionExitosa();
    }

    @javafx.fxml.FXML
    public void operacionCancelarVenta(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = stage.getOwner().getScene().getRoot();
        root.setEffect(null);
        stage.close();
    }

    @javafx.fxml.FXML
    public void realizarVentaFiado(ActionEvent actionEvent) throws IOException {
        mostrarOperacionExitosa();
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
