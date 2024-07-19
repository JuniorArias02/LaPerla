/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package CrudControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Junior
 */
public class VistaPagoVentaController implements Initializable {
    ClienteDao clienteDao = new ClienteDao();

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
        nombreCliente.setOnAction(this::buscarClientePorCedula);
        cantidadRecibida.setText("0");

        cantidadRecibida.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                actualizarMontoTotal();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void buscarClientePorCedula(ActionEvent event) {
        String cedula = nombreCliente.getText();

        try {
            Long codigo = Long.parseLong(cedula);
            Cliente cliente = clienteDao.buscarCliente(codigo);

            if (cliente != null) {
                nombreCliente.setText(cliente.getNombre());
            } else {
                nombreCliente.setText("");
            }
        } catch (NumberFormatException e) {
            nombreCliente.setText("");
        }
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

    public void setMontoTotal(String total) {
        try {
            double parsedTotal = Double.parseDouble(total);
            montoTotal.setText(String.valueOf(parsedTotal));
        } catch (NumberFormatException e) {
            montoTotal.setText("0");
        }
    }

    private void actualizarMontoTotal() throws IOException {
        try {
            double total = Double.parseDouble(montoTotal.getText());
            double recibido = Double.parseDouble(cantidadRecibida.getText());

            // Resta el valor recibido del total
            double nuevoTotal = total - recibido;

            // Actualiza el monto total
            montoTotal.setText(String.valueOf(nuevoTotal));
        } catch (NumberFormatException e) {
            // Manejar la excepción y mostrar un mensaje de error
            montoTotal.setText("0");
            mostrarOperacionErronea();
        }
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
