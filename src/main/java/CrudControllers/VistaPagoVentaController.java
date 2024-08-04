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
import Controllers.VentasController;
import Dao.*;
import com.mycompany.la_perla.PrincipalController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    NuevaVentaController nuevaVentaController;
    VentasController ventasController;

    @javafx.fxml.FXML
    private TextField nombreCliente;

    @FXML
    private TextField cantidadRecibida;

    @javafx.fxml.FXML
    private Label montoTotal;

    private double ventaTotal;

    private long id_cliente;


    /**
     * Initializes the controller class.
     */


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombreCliente.setOnAction(this::buscarClientePorCedula);
        cantidadRecibida.setText("");
        cantidadRecibida.textProperty().addListener((observable, oldValue, newValue) -> {
            try {

                actualizarMontoTotal();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


    }


    @javafx.fxml.FXML
    public void realizarVentaEfectivo(ActionEvent actionEvent) throws IOException, SQLException {
        String nombre = nombreCliente.getText();

        if (nombre.isEmpty()) {
            ErrorCampoClienteVacioVenta();
            System.out.println("Identidad del cliente vacía");
        } else if (esNumero(nombre)) {
            SugerenciaBuscarCliente();
            System.out.println("No olvides presionar enter para buscar el cliente");
        } else {
            long cliente = id_cliente;
            String montoTotalStr = this.montoTotal.getText();
            String cantidadRecibidaStr = cantidadRecibida.getText();

            if (validarPrecioVenta(montoTotalStr, cantidadRecibidaStr) == false) {
                System.out.println("El monto recibido debe ser mayor o igual al monto total.");
                ErrorMontoIncorrecto();
                return;
            }

            double montoTotal = Double.parseDouble(montoTotalStr);
            double montoTotalDao = Double.parseDouble(principalController.TotalVentaPago.getText());
            Ventas nuevaVenta = ventasDao.nuevaVenta(cliente, montoTotalDao);

            // Obtener los productos desde NuevaVentaController
            List<Productos> productos = nuevaVentaController.obtenerProductosSeleccionados();

            if (productos.isEmpty()) {
                ErrorNoHayProductoTabla();
                return;
            }

            // Registrar cada detalle de venta
            for (Productos producto : productos) {
                ventasDao.detalle_venta(producto.getCodigoProducto(), producto.getCantidadProducto());
            }
            mostrarOperacionExitosa();
            ventasController.iniciarCargaDatos();
            limpiarTablaYCampos();

            // Actualizar la tabla después de agregar un nuevo proveedor
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent root = stage.getOwner().getScene().getRoot();
            root.setEffect(null);
            stage.close();
        }
    }

    @javafx.fxml.FXML
    public void operacionCancelarVenta(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = stage.getOwner().getScene().getRoot();
        root.setEffect(null);
        stage.close();
    }


//    metodos de las clases

    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    public void setVentasController(VentasController ventasController) {
        this.ventasController = ventasController;
    }

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
                ErrorClienteNoEncontrado();
            }
        } catch (NumberFormatException | IOException e) {
            nombreCliente.setText("");
        }
    }

    private boolean validarPrecioVenta(String montoTotalStr, String cantidadRecibidaStr) {
        try {
            double montoTotal = Double.parseDouble(montoTotalStr);
            double cantidadRecibida = Double.parseDouble(cantidadRecibidaStr);

            if (montoTotal >= 0) {
                System.out.println("verdadero");
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir el monto o la cantidad recibida: " + e.getMessage());
            return false;
        }
        return false;
    }

    private boolean esNumero(String texto) {
        if (texto == null || texto.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(texto);
            System.out.println("es numero");
//            System.out.println(texto);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("no numero");
//            System.out.println(texto);
            return false;
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, String detalles) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.setContentText(detalles);
        alerta.showAndWait();
    }

    public void setMontoTotal(String total) {
        try {
            double parsedTotal = Double.parseDouble(total);
            ventaTotal = (long) parsedTotal;
            montoTotal.setText(String.valueOf(parsedTotal));
            System.out.println(montoTotal);
        } catch (NumberFormatException e) {
            //montoTotal.setText("0");
            System.out.println("no se pued agregar letras");
        }
    }

    private void actualizarMontoTotal() throws IOException {
        double recibido = 0;
        try {
            String cantidadRecibidaTexto = cantidadRecibida.getText();

            if (cantidadRecibidaTexto == null || cantidadRecibidaTexto.trim().isEmpty()) {
                cantidadRecibidaTexto = "0";
            }

            recibido = Double.parseDouble(cantidadRecibidaTexto);

        } catch (NumberFormatException e) {

            recibido = 0;
            cantidadRecibida.setText("");
            mostrarOperacionErronea();
        }

        double precioTotal = ventaTotal;
        montoTotal.setText(String.valueOf(precioTotal + recibido));
    }

    private void limpiarTablaYCampos() {
        nuevaVentaController.getTablaNuevaVentas().getItems().clear();

        montoTotal.setText("");
        cantidadRecibida.setText("");
        nombreCliente.setText("");
    }

//    alertas personalizadas

    private void mostrarOperacionExitosa() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/alertas/OperacionExitosa.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> stage.close()));
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

    private void ErrorMontoIncorrecto() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/alertas/ErrorMontoIncorrecto.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> stage.close()));
        timeline.play();
    }

    private void ErrorClienteNoEncontrado() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/alertas/ErrorClienteNoEncontrado.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> stage.close()));
        timeline.play();
    }

    private void ErrorCamposVacios() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/alertas/ErrorCamposVacios.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.9), event -> stage.close()));
        timeline.play();
    }

    private void ErrorNoHayProductoTabla() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/alertas/ErrorNoHayProductoTabla.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.9), event -> stage.close()));
        timeline.play();
    }

    private void ErrorCampoClienteVacioVenta() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/alertas/ErrorCampoClienteVacioVenta.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.9), event -> stage.close()));
        timeline.play();
    }

    private void SugerenciaBuscarCliente() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/alertas/SugerenciaBuscarCliente.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.9), event -> stage.close()));
        timeline.play();
    }
}
