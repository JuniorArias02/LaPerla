/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package CrudControllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import Controllers.ProductosController;
import Dao.Productos;
import Dao.ProductosDao;
import Dao.Proveedor;
import Dao.ProveedorDao;
import com.mycompany.la_perla.PrincipalController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
public class ModificarProductoController implements Initializable {
    private Productos pro;
    private ProductosDao proDao = new ProductosDao();
    private PrincipalController pc;
    private ProductosController productosController;
    private Map<String, Proveedor> proveedoresMap;
    private ProveedorDao proveedorDao = new ProveedorDao();

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
    private ComboBox proveedorProducto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            this.proveedoresMap = proveedorDao.obtenerProveedores();
            ObservableList<String> nombresProveedores = FXCollections.observableArrayList(proveedoresMap.keySet());
            proveedorProducto.setItems(nombresProveedores);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void setProductosController(ProductosController productosController) {
        this.productosController = productosController;
    }

    public void setProducto(Productos pro) {
        this.pro = pro;
        if (pro != null) {
            codigoProducto.setText(String.valueOf(pro.getCodigoProducto()));
            nombreProducto.setText(pro.getNombreProducto());
            categoriaProducto.setText(pro.getCategoriaProducto());
            precioProducto.setText(String.valueOf(pro.getPrecioProducto()));
            stockProducto.setText(String.valueOf(pro.getStockProducto()));

            this.codigoProducto.setDisable(true);

            // Seleccionar el proveedor correspondiente en el ComboBox
            for (Map.Entry<String, Proveedor> entry : proveedoresMap.entrySet()) {
                if (entry.getValue().getCodigoProveedor() == pro.getIdProveedor()) {
                    proveedorProducto.setValue(entry.getKey());
                    break;
                }
            }
        }
    }

    @javafx.fxml.FXML
    public void cancelarModificacionProducto(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = stage.getOwner().getScene().getRoot();
        root.setEffect(null);
        stage.close();
    }

    @javafx.fxml.FXML
    public void confirmarModificacionProducto(ActionEvent actionEvent) throws IOException {
        try {
            int codigo = Integer.parseInt(this.codigoProducto.getText());
            String nombre = this.nombreProducto.getText();
            String categoria = this.categoriaProducto.getText();
            int stock = Integer.parseInt(this.stockProducto.getText());
            int precio = Integer.parseInt(this.precioProducto.getText());
            String proveedorNombre = (String) this.proveedorProducto.getValue();
//lo mismo de aqui
//            if (!esTextoValido(nombre)) {
//                mostrarMensajeError("El nombre del producto solo puede contener letras y espacios.");
//                return;
//            }

            if (!esTextoValido(categoria)) {
                mostrarMensajeError("La categoría del producto solo puede contener letras y espacios.");
                return;
            }

            if (proveedorNombre != null && proveedoresMap.containsKey(proveedorNombre)) {
                Proveedor proveedor = proveedoresMap.get(proveedorNombre);
                int idProveedor = proveedor.getCodigoProveedor(); // Suponiendo que tienes un método getId() en Proveedor

                // Actualizar el producto
                pro.setCodigoProducto(codigo);
                pro.setNombreProducto(nombre);
                pro.setPrecioProducto(precio);
                pro.setCategoriaProducto(categoria);
                pro.setIdProveedor(idProveedor);
                pro.setStockProducto(stock);

                // Aquí llamas al método para actualizar el producto en la base de datos
                proDao.modificarProducto(codigo, nombre, precio, categoria, idProveedor, stock);
                productosController.iniciarCargaDatos();
                mostrarOperacionExitosa();
            } else {
                mostrarOperacionErronea();
            }
        } catch (NumberFormatException e) {
            mostrarOperacionErronea();
        } catch (Exception e) {
            mostrarOperacionErronea();
        }
    }

    private boolean esTextoValido(String texto) {
        return texto.matches("[a-zA-Z\\s]+"); // Solo permite letras y espacios
    }

    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error de Validación", JOptionPane.ERROR_MESSAGE);
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
