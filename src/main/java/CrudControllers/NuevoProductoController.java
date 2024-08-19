/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package CrudControllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import Controllers.ProductosController;
import Dao.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javax.swing.*;

/**
 * FXML Controller class
 *
 * @author Junior
 */
public class NuevoProductoController implements Initializable {

    private Productos pro;
    private ProductosDao proDao;
    private ProveedorDao proveedorDao;
    private Map<String, Proveedor> proveedoresMap;
    private ProductosController productosController;
    public void setProductoController(ProductosController productosController) {
        this.productosController = productosController;
    }

    @javafx.fxml.FXML
    private TextField codigoProducto;
    @javafx.fxml.FXML
    private TextField precioProducto;
    @javafx.fxml.FXML
    private TextField stockProducto;
    @javafx.fxml.FXML
    private TextField nombreProducto;
    @javafx.fxml.FXML
    private TextField categoriaProducto;
    @javafx.fxml.FXML
    private ComboBox<String> proveedorProducto;

    public NuevoProductoController() {
        this.proveedorDao = new ProveedorDao();
        this.proDao = new ProductosDao();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            this.proveedoresMap = proveedorDao.obtenerProveedores();

            // Obtener los nombres de los proveedores para llenar el ComboBox
            ObservableList<String> nombresProveedores = FXCollections.observableArrayList(proveedoresMap.keySet());
            proveedorProducto.setItems(nombresProveedores);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // TODO
        proDao = new ProductosDao();

        // Filtro para permitir solo números en el campo código del cliente
        codigoProducto.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                codigoProducto.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Filtro para permitir solo números en el campo teléfono del cliente
        precioProducto.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                precioProducto.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Filtro para permitir solo números en el campo teléfono del cliente
        stockProducto.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                stockProducto.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

    }

    @FXML
    public void confirmarAgregarProducto(ActionEvent actionEvent) throws IOException {
        if (this.codigoProducto.getText().isEmpty() ||
                this.nombreProducto.getText().isEmpty() ||
                this.precioProducto.getText().isEmpty() ||
                this.categoriaProducto.getText().isEmpty() ||
                this.proveedorProducto.getSelectionModel().isEmpty() ||
                this.stockProducto.getText().isEmpty()) {
            mostrarErrorCamposVacios();
        } else {
            try {
                int codigo = Integer.parseInt(this.codigoProducto.getText());
                String nombre = this.nombreProducto.getText();
                int precio = Integer.parseInt(this.precioProducto.getText());
                String categoria = this.categoriaProducto.getText();
                String nombreProveedor = proveedorProducto.getSelectionModel().getSelectedItem();
                Proveedor proveedorSeleccionado = proveedoresMap.get(nombreProveedor);
                if (proveedorSeleccionado == null) {
                    mostrarAlerta("Error", "Debe seleccionar un proveedor.", String.valueOf(Alert.AlertType.ERROR));
                    return;
                }
// apartir de aqui dejara ingresar numeros al nombre producto
//                if (!esTextoValido(nombre)) {
//                    mostrarAlerta("Error", "El nombre del producto solo puede contener letras y espacios.", String.valueOf(Alert.AlertType.ERROR));
//                    return;
//                }

                if (!esTextoValido(categoria)) {
                    mostrarAlerta("Error", "La categoría del producto solo puede contener letras y espacios.", String.valueOf(Alert.AlertType.ERROR));
                    return;
                }

                int proveedorId = proveedorSeleccionado.getCodigoProveedor();
                int stock = Integer.parseInt(this.stockProducto.getText());

                pro = proDao.agregarProductos(codigo, nombre, precio, categoria, proveedorId, stock);
                if(pro != null){
                    productosController.iniciarCargaDatos();
                    mostrarOperacionExitosa();
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Parent root = stage.getOwner().getScene().getRoot();
                    root.setEffect(null);
                    stage.close();
                }

            } catch (NumberFormatException num) {
                mostrarAlerta("Error", "No se pudo agregar el producto: " + num.getMessage(), String.valueOf(Alert.AlertType.ERROR));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @javafx.fxml.FXML
    public void cancelarAgregarProducto(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = stage.getOwner().getScene().getRoot();
        root.setEffect(null);
        stage.close();
    }

    private boolean esTextoValido(String texto) {
        return texto.matches("[a-zA-Z\\s]+"); // Solo permite letras y espacios
    }

    private void mostrarAlerta(String titulo, String mensaje, String detalles) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.setContentText(detalles);
        alerta.showAndWait();
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
