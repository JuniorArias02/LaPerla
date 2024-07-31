/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package CrudControllers;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

import Controllers.NuevaVentaController;
import Dao.*;
import com.mycompany.la_perla.PrincipalController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
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
    VentasDao ventasDao = new VentasDao();
    PrincipalController principalController;
    @javafx.fxml.FXML
    private TextField nombreCliente;
    @javafx.fxml.FXML
    private TextField cantidadRecibida;
    @javafx.fxml.FXML
    private Label montoTotal;

    private long id_cliente;

    /**
     * Initializes the controller class.
     */
    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

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


    private NuevaVentaController nuevaVentaController;

    public void setNuevaVentaController(NuevaVentaController nuevaVentaController) {
        this.nuevaVentaController = nuevaVentaController;
        System.out.println("se establecio el puente");
    }

    private void buscarClientePorCedula(ActionEvent event) {
        String cedula = nombreCliente.getText();

        try {
            Long codigo = Long.parseLong(cedula);
            Cliente cliente = clienteDao.buscarCliente(codigo);

            if (cliente != null) {
                this.id_cliente = codigo;
                nombreCliente.setText(cliente.getNombre());
            } else {
                nombreCliente.setText("");
            }
        } catch (NumberFormatException e) {
            nombreCliente.setText("");
        }
    }

    @javafx.fxml.FXML
    public void realizarVentaEfectivo(ActionEvent actionEvent) throws IOException, SQLException {
        long cliente = id_cliente;
        double montoDouble = Double.parseDouble(this.montoTotal.getText());
        long monto = (long) montoDouble; // Convertir a long si es necesario

        // Registrar la venta
        Ventas nuevaVenta = ventasDao.nuevaVenta(cliente, monto);

        // Obtener los productos desde NuevaVentaController
        List<Productos> productos = nuevaVentaController.obtenerProductosSeleccionados();

        // Registrar cada detalle de venta
        for (Productos producto : productos) {
            ventasDao.detalle_venta(producto.getCodigoProducto(), producto.getCantidadProducto());
        }

        limpiarTablaYCampos();
}


private void mostrarAlerta(String titulo, String mensaje, String detalles) {
    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    alerta.setTitle(titulo);
    alerta.setHeaderText(mensaje);
    alerta.setContentText(detalles);
    alerta.showAndWait();
}

@javafx.fxml.FXML
public void operacionCancelarVenta(ActionEvent actionEvent) throws IOException {
    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    Parent root = stage.getOwner().getScene().getRoot();
    root.setEffect(null);
    stage.close();
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

    private void limpiarTablaYCampos() {
        // Vaciar la tabla
        nuevaVentaController.getTablaNuevaVentas().getItems().clear();

        // Limpiar los campos
        montoTotal.setText("");
        cantidadRecibida.setText("");
        nombreCliente.setText("");
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
