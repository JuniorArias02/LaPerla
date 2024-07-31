/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.la_perla;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Controllers.*;
import CrudControllers.*;
import Dao.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import jdk.jfr.Event;
import javafx.util.Callback;


/**
 * FXML Controller class
 *
 * @author Junior
 */
public class PrincipalController implements Initializable {

    Usuario us;
    UsuarioDao usDao;

    Proveedor prov = new Proveedor();
    ProveedorDao provDao = new ProveedorDao();

    Productos pro = new Productos();
    ProductosDao proDao = new ProductosDao();

    Cliente cli = new Cliente();
    ClienteDao cliDao = new ClienteDao();

    Ventas vent = new Ventas();
    VentasDao ventDao = new VentasDao();

    public ProveedorController proveedorController;
    public ProductosController productosController;
    public ClienteController clienteController;
    public VentasController ventasController;
    public NuevaVentaController nuevaVentaController;


    //    private ProveedorDao proveedorDao;
    private static UsuarioSesion usuarioSesion;

    @FXML
    private Pane PanelInicio;
    @FXML
    private Pane PanelProducto;
    @FXML
    private Pane PanelVenta;
    @FXML
    private Pane PanelCliente;
    @FXML
    private Pane PanelProveedor;
    @FXML
    private Pane PanelSalir;

    private Scene scene;

    private BoxBlur blurEffect;
    @FXML
    private Pane PanelPrincipal;
    @FXML
    private Pane inventario;
    @FXML
    private Pane clientes;
    @FXML
    private Pane ventas;
    @FXML
    private Pane proveedor;
    @FXML
    private TabPane ventana;
    @FXML
    private ImageView NuevaVenta;
    @FXML
    private Pane PanelPerfil;
    @FXML
    private Pane perfil;
    @FXML
    public TableView tablaNuevaVentas;
    @FXML
    public TableView tablaClientes;
    @FXML
    public TextField usuarioNombre;
    @FXML
    public TextField usuarioClave;
    @FXML
    public TableView tablaProductos;
    @FXML
    public TextField usuarioUsuario;
    @FXML
    public TextField usuarioConfirmarClave;
    @FXML
    public TextField usuarioGmail;
    @FXML
    public TableView tablaVentas;
    @FXML
    public TableView tablaProveedor;
    @FXML
    public Label txtNombreUsuario;
    @FXML
    public TableColumn<Proveedor, Integer> ProveedorCodigoColumna;
    @FXML
    public TableColumn<Proveedor, String> ProveedorNombreColumna;
    @FXML
    public TableColumn<Proveedor, String> ProveedorTelefonoColumna;
    @FXML
    public TableColumn<Proveedor, String> totalColumn;
    @FXML
    public TextField buscadorProveedor;
    @FXML
    public TextField buscadorProductos;
    @FXML
    public TableColumn<Productos, Integer> ProductoCodigoColumna;
    @FXML
    public TableColumn<Productos, String> ProductoNombreColumna;
    @FXML
    private TableColumn<Productos, String> ProductoCategoriaColumna;
    @FXML
    public TableColumn<Productos, Integer> ProductoPrecioColumna;
    @FXML
    public TableColumn<Productos, Integer> ProductoStockColumna;
    @FXML
    private TableColumn ProductoProveedorColumna;
    @FXML
    private Pane menu;
    @FXML
    private TableColumn clienteNombreColumna;
    @FXML
    private TableColumn clienteTelefonoColumna;
    @FXML
    private TableColumn clienteCodigoColumna;
    @FXML
    public TextField buscadorCliente;
    @FXML
    public TextField buscadorVentas;
    @FXML
    private TableColumn ventaMontoColumna;
    @FXML
    private TableColumn ventaFechaColumna;
    @FXML
    private TableColumn ventaClienteColumna;
    @FXML
    private TableColumn ventaCodigoColumna;
    @FXML
    public TextField agregarProductoVenta;
    @FXML
    public TableColumn<Productos, Integer> productoCantidadColumna;
    @FXML
    public TableColumn productoPrecioColumna;
    @FXML
    public TableColumn productoNombreColumna;
    @FXML
    public TableColumn productoIdColumna;
    @FXML
    public TableColumn productoTotalColumna;
    @FXML
    public Label TotalVentaPago;


    /**
     * Initializes the controller class.
     */


    public static void setUsuarioSesion(UsuarioSesion sesion) {
        usuarioSesion = sesion;
    }



    public List<DetalleVenta> obtenerDetallesVenta() {
        return new ArrayList<>(tablaNuevaVentas.getItems());
    }

    public TableView<Productos> getTablaNuevaVentas() {
        return tablaNuevaVentas;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usDao = new UsuarioDao();
        mostrarNombreUsuario(usuarioSesion.getIdUsuario());
        mostrarInformacionUsuario(usuarioSesion.getIdUsuario());
        proveedorController = new ProveedorController(prov, provDao, this);
        productosController = new ProductosController(pro, proDao, this);
        clienteController = new ClienteController(cli, cliDao, this);
        ventasController = new VentasController(vent, ventDao, this);
        nuevaVentaController = new NuevaVentaController(tablaNuevaVentas, productoIdColumna, productoNombreColumna, productoCantidadColumna, productoPrecioColumna, productoTotalColumna, TotalVentaPago);

        agregarProductoVenta.setOnAction(event -> {
            String idText = agregarProductoVenta.getText();
            if (!idText.isEmpty()) {
                int id = Integer.parseInt(idText);
                try {
                    nuevaVentaController.agregarProductoATabla(id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        inicializarColumnasProveedor();
        inicializarColumnasProductos();
        inicializarColumnasCliente();
        inicializarColumnasVentas();
        proveedorController.iniciarCargaDatos();
        productosController.iniciarCargaDatos();
        clienteController.iniciarCargaDatos();
        ventasController.iniciarCargaDatos();

    }

    public void inicializarColumnasNuevaVenta(){
        productoIdColumna.setCellValueFactory(new PropertyValueFactory<>("codigoProducto"));
        productoNombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        productoCantidadColumna.setCellValueFactory(new PropertyValueFactory<>("cantidadProducto"));
        productoPrecioColumna.setCellValueFactory(new PropertyValueFactory<>("precioProducto"));
        productoTotalColumna.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    public void inicializarColumnasProveedor() {
        ProveedorCodigoColumna.setCellValueFactory(new PropertyValueFactory<>("codigoProveedor"));
        ProveedorNombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));
        ProveedorTelefonoColumna.setCellValueFactory(new PropertyValueFactory<>("telefonoProveedor"));
    }

    public void inicializarColumnasVentas() {
        ventaCodigoColumna.setCellValueFactory(new PropertyValueFactory<>("codigoVenta"));
        ventaFechaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        ventaClienteColumna.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        ventaMontoColumna.setCellValueFactory(new PropertyValueFactory<>("monto"));
    }

    public void inicializarColumnasCliente() {
        clienteCodigoColumna.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        clienteNombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clienteTelefonoColumna.setCellValueFactory(new PropertyValueFactory<>("telefono"));
    }

    public void inicializarColumnasProductos() {
        ProductoCodigoColumna.setCellValueFactory(new PropertyValueFactory<>("codigoProducto"));
        ProductoNombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        ProductoCategoriaColumna.setCellValueFactory(new PropertyValueFactory<>("categoriaProducto"));
        ProductoPrecioColumna.setCellValueFactory(new PropertyValueFactory<>("precioProducto"));
        ProductoStockColumna.setCellValueFactory(new PropertyValueFactory<>("stockProducto"));
        ProductoProveedorColumna.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
    }

    private void mostrarNombreUsuario(int id) {
        us = usDao.mostrarNombre(id);
        if (us != null) {
            txtNombreUsuario.setText(us.getNombre());
        } else {
            mostrarAlerta("Error", "No se encontró información para el usuario con ID: " + id, "");
        }

    }

    public String getTotalVentaPago() {
        return TotalVentaPago.getText();
    }

    private void mostrarInformacionUsuario(int id_usuario) {
        us = usDao.informacion(id_usuario);
        if (us != null) {
            usuarioUsuario.setText(us.getUsuario());
            usuarioNombre.setText(us.getNombre());
            usuarioGmail.setText(us.getGmail());
            usuarioClave.setText(us.getClave());
            usuarioConfirmarClave.setText(us.getClave());
        } else {
            mostrarAlerta("Error", "No se encontró información para el usuario con ID: " + id_usuario, "");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, String detalles) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.setContentText(detalles);
        alerta.showAndWait();
    }

    @FXML
    public void eliminarProductoHandler(MouseEvent mouseEvent) {
        if (nuevaVentaController != null) {
            nuevaVentaController.eliminarProductoSeleccionado();
        } else {
            System.out.println("NuevaVentaController no inicializado.");
        }
    }

    @FXML
    private void cambiarPestaña(MouseEvent event) throws IOException {
        if (event.getSource() == this.PanelInicio) {
            ventana.getSelectionModel().select(0);
        } else if (event.getSource() == this.inventario || event.getSource() == this.PanelProducto) {
            ventana.getSelectionModel().select(1);
        } else if (event.getSource() == this.ventas || event.getSource() == this.PanelVenta) {
            ventana.getSelectionModel().select(2);
        } else if (event.getSource() == this.clientes || event.getSource() == this.PanelCliente) {
            ventana.getSelectionModel().select(3);
        } else if (event.getSource() == this.proveedor || event.getSource() == this.PanelProveedor) {
            ventana.getSelectionModel().select(4);
        } else if (event.getSource() == this.NuevaVenta) {
            ventana.getSelectionModel().select(5);
        } else if (event.getSource() == this.perfil || event.getSource() == this.PanelPerfil) {
            ventana.getSelectionModel().select(6);
            mostrarInformacionUsuario(usuarioSesion.getIdUsuario());
        } else if (event.getSource() == this.PanelSalir) {
            UsuarioSesion.limpiarInstancia();
            App.setRoot("Login");
        }
    }


    //    sub-ventanas para productos
    @FXML
    private void abrirVentanaNuevoProducto(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Crud/NuevoProducto.fxml"));
        Parent root = loader.load();

        NuevoProductoController nuevoProductoController = loader.getController();
        nuevoProductoController.setProductoController(productosController);

        Scene scene = PanelPrincipal.getScene();


        BoxBlur blurEffect = new BoxBlur(5, 5, 3);
        scene.getRoot().setEffect(blurEffect);


        Stage nuevaVentana = new Stage();
        nuevaVentana.setScene(new Scene(root));
        nuevaVentana.setTitle("Nuevo Producto");
        nuevaVentana.initModality(Modality.APPLICATION_MODAL);
        nuevaVentana.initStyle(StageStyle.UNDECORATED);
        nuevaVentana.initOwner(((Node) event.getSource()).getScene().getWindow());
        nuevaVentana.setOnCloseRequest(e -> scene.getRoot().setEffect(null));
        nuevaVentana.show();
    }

    @FXML
    private void abrirVentanaModificarProducto(MouseEvent event) throws IOException {
        Productos selectedProducto = (Productos) tablaProductos.getSelectionModel().getSelectedItem();
        if (selectedProducto != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Crud/ModificarProducto.fxml"));
            Parent root = loader.load();


            ModificarProductoController modificarProductoController = loader.getController();
            modificarProductoController.setProducto(selectedProducto);
            modificarProductoController.setProductosController(productosController);
//        modificarProductoController.setProductoController(productosController);

            Scene scene = PanelPrincipal.getScene();


            BoxBlur blurEffect = new BoxBlur(5, 5, 3);
            scene.getRoot().setEffect(blurEffect);


            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(new Scene(root));
            nuevaVentana.setTitle("Modificar Producto");
            nuevaVentana.initModality(Modality.APPLICATION_MODAL);
            nuevaVentana.initStyle(StageStyle.UNDECORATED);
            nuevaVentana.initOwner(((Node) event.getSource()).getScene().getWindow());
            nuevaVentana.setOnCloseRequest(e -> scene.getRoot().setEffect(null));
            nuevaVentana.show();
        } else {
            mostrarAlerta("Selección requerida", "Por favor selecciona un producto para modificar.", String.valueOf(Alert.AlertType.WARNING));
        }

    }

    @FXML
    private void abrirVentanaEliminarProducto(MouseEvent event) throws IOException {
        Productos selectedProducto = (Productos) tablaProductos.getSelectionModel().getSelectedItem();
        if (selectedProducto != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Crud/EliminarProducto.fxml"));
            Parent root = loader.load();

            EliminarProductoController eliminarProductoController = loader.getController();
            eliminarProductoController.setProducto(selectedProducto);
            eliminarProductoController.setProductosController(productosController);

            Scene scene = PanelPrincipal.getScene();


            BoxBlur blurEffect = new BoxBlur(5, 5, 3);
            scene.getRoot().setEffect(blurEffect);


            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(new Scene(root));
            nuevaVentana.setTitle("Eliminar Producto");
            nuevaVentana.initModality(Modality.APPLICATION_MODAL);
            nuevaVentana.initStyle(StageStyle.UNDECORATED);
            nuevaVentana.initOwner(((Node) event.getSource()).getScene().getWindow());
            nuevaVentana.setOnCloseRequest(e -> scene.getRoot().setEffect(null));
            nuevaVentana.show();
        }

    }

    //    sub-ventanas para proveedor
    @FXML
    private void abrirVentanaNuevoProveedor(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Crud/NuevoProveedor.fxml"));
        Parent root = loader.load();

        NuevoProveedorController nuevoProveedorController = loader.getController();
        nuevoProveedorController.setProveedorController(proveedorController);

        Scene scene = PanelPrincipal.getScene();

        BoxBlur blurEffect = new BoxBlur(5, 5, 3);
        scene.getRoot().setEffect(blurEffect);

        Stage nuevaVentana = new Stage();
        nuevaVentana.setScene(new Scene(root));
        nuevaVentana.setTitle("Nuevo Proveedor");
        nuevaVentana.initModality(Modality.APPLICATION_MODAL);
        nuevaVentana.initStyle(StageStyle.UNDECORATED);
        nuevaVentana.initOwner(((Node) event.getSource()).getScene().getWindow());
        nuevaVentana.setOnCloseRequest(e -> scene.getRoot().setEffect(null));
        nuevaVentana.show();
    }

    @FXML
    private void abrirVentanaModificarProveedor(MouseEvent event) throws IOException {
        Proveedor selectedProveedor = (Proveedor) tablaProveedor.getSelectionModel().getSelectedItem();
        if (selectedProveedor != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Crud/ModificarProveedor.fxml"));
            Parent root = loader.load();

            ModificarProveedorController modificarProveedorController = loader.getController();
            modificarProveedorController.setProveedor(selectedProveedor);
            modificarProveedorController.setProveedorController(proveedorController);

            Scene scene = PanelPrincipal.getScene();

            BoxBlur blurEffect = new BoxBlur(5, 5, 3);
            scene.getRoot().setEffect(blurEffect);

            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(new Scene(root));
            nuevaVentana.setTitle("Modificar Proveedor");
            nuevaVentana.initModality(Modality.APPLICATION_MODAL);
            nuevaVentana.initStyle(StageStyle.UNDECORATED);
            nuevaVentana.initOwner(((Node) event.getSource()).getScene().getWindow());
            nuevaVentana.setOnCloseRequest(e -> scene.getRoot().setEffect(null));
            nuevaVentana.show();
        } else {
            mostrarAlerta("Selección requerida", "Por favor selecciona un proveedor para modificar.", String.valueOf(Alert.AlertType.WARNING));
        }
    }

    @FXML
    private void abrirVentanaEliminarProveedor(MouseEvent event) throws IOException {
        Proveedor selectedProveedor = (Proveedor) tablaProveedor.getSelectionModel().getSelectedItem();
        if (selectedProveedor != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Crud/EliminarProveedor.fxml"));
            Parent root = loader.load();

            EliminarProveedorController eliminarProveedorController = loader.getController();
            eliminarProveedorController.setProveedor(selectedProveedor);
            eliminarProveedorController.setProveedorController(proveedorController);

            Scene scene = PanelPrincipal.getScene();

            BoxBlur blurEffect = new BoxBlur(5, 5, 3);
            scene.getRoot().setEffect(blurEffect);

            Stage nuevaVentana = new Stage();
            nuevaVentana.setUserData(this);
            nuevaVentana.setScene(new Scene(root));
            nuevaVentana.setTitle("Eliminar Proveedor");
            nuevaVentana.initModality(Modality.APPLICATION_MODAL);
            nuevaVentana.initStyle(StageStyle.UNDECORATED);
            nuevaVentana.initOwner(((Node) event.getSource()).getScene().getWindow());
            nuevaVentana.setOnCloseRequest(e -> scene.getRoot().setEffect(null));
            nuevaVentana.show();
        } else {
            mostrarAlerta("Selección requerida", "Por favor selecciona un proveedor para modificar.", String.valueOf(Alert.AlertType.WARNING));
        }
    }

    //    sub-ventanas para clientes
    @FXML
    private void abrirVentanaNuevoCliente(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Crud/NuevoCliente.fxml"));
        Parent root = loader.load();

        NuevoClienteController nuevoClienteController = loader.getController();
        nuevoClienteController.setClienteController(clienteController);

        Scene scene = PanelPrincipal.getScene();


        BoxBlur blurEffect = new BoxBlur(5, 5, 3);
        scene.getRoot().setEffect(blurEffect);


        Stage nuevaVentana = new Stage();
        nuevaVentana.setScene(new Scene(root));
        nuevaVentana.setTitle("Nuevo Cliente");
        nuevaVentana.initModality(Modality.APPLICATION_MODAL);
        nuevaVentana.initStyle(StageStyle.UNDECORATED);
        nuevaVentana.initOwner(((Node) event.getSource()).getScene().getWindow());
        nuevaVentana.setOnCloseRequest(e -> scene.getRoot().setEffect(null));
        nuevaVentana.show();
    }

    @FXML
    private void abrirVentanaModificarCliente(MouseEvent event) throws IOException {
        Cliente selectedCliente = (Cliente) tablaClientes.getSelectionModel().getSelectedItem();
        if (selectedCliente != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Crud/ModificarCliente.fxml"));
            Parent root = loader.load();

            ModificarClienteController modificarClienteController = loader.getController();
            modificarClienteController.setCliente(selectedCliente);
            modificarClienteController.setClienteController(clienteController);
            Scene scene = PanelPrincipal.getScene();

            BoxBlur blurEffect = new BoxBlur(5, 5, 3);
            scene.getRoot().setEffect(blurEffect);

            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(new Scene(root));
            nuevaVentana.setTitle("Modificar Cliente");
            nuevaVentana.initModality(Modality.APPLICATION_MODAL);
            nuevaVentana.initStyle(StageStyle.UNDECORATED);
            nuevaVentana.initOwner(((Node) event.getSource()).getScene().getWindow());
            nuevaVentana.setOnCloseRequest(e -> scene.getRoot().setEffect(null));
            nuevaVentana.show();
        } else {
            mostrarAlerta("Selección requerida", "Por favor selecciona un cliente para modificar.", String.valueOf(Alert.AlertType.WARNING));
        }

    }

    @FXML
    private void abrirVentanaEliminarCliente(MouseEvent event) throws IOException {
        Cliente selectedCliente = (Cliente) this.tablaClientes.getSelectionModel().getSelectedItem();
        if (selectedCliente != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Crud/EliminarCliente.fxml"));
            Parent root = loader.load();

            EliminarClienteController eliminarClienteController = loader.getController();
            eliminarClienteController.setCliente(selectedCliente);
            eliminarClienteController.setClienteController(clienteController);
            Scene scene = PanelPrincipal.getScene();

            BoxBlur blurEffect = new BoxBlur(5, 5, 3);
            scene.getRoot().setEffect(blurEffect);

            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(new Scene(root));
            nuevaVentana.setTitle("Eliminar Cliente");
            nuevaVentana.initModality(Modality.APPLICATION_MODAL);
            nuevaVentana.initStyle(StageStyle.UNDECORATED);
            nuevaVentana.initOwner(((Node) event.getSource()).getScene().getWindow());
            nuevaVentana.setOnCloseRequest(e -> scene.getRoot().setEffect(null));
            nuevaVentana.show();
        }
    }

    //    sub-ventanas  para pagar
    @FXML
    private void abrirVentaPago(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Crud/vistaPagoVenta.fxml"));
        Parent root = loader.load();

        VistaPagoVentaController vistaPagoVentaController = loader.getController();
        vistaPagoVentaController.setPrincipalController(this);

        // Pasar la referencia de NuevaVentaController a VistaPagoVentaController
        vistaPagoVentaController.setNuevaVentaController(nuevaVentaController);
        vistaPagoVentaController.setMontoTotal(TotalVentaPago.getText());
        Scene scene = PanelPrincipal.getScene();


        BoxBlur blurEffect = new BoxBlur(5, 5, 3);
        scene.getRoot().setEffect(blurEffect);


        Stage nuevaVentana = new Stage();
        nuevaVentana.setScene(new Scene(root));
        nuevaVentana.setTitle("Venta Pago");
        nuevaVentana.initModality(Modality.APPLICATION_MODAL);
        nuevaVentana.initStyle(StageStyle.UNDECORATED);
        nuevaVentana.initOwner(((Node) event.getSource()).getScene().getWindow());
        nuevaVentana.setOnCloseRequest(e -> scene.getRoot().setEffect(null));
        nuevaVentana.show();
    }




    //modificar usuario
    @FXML
    public void modificarUsuario(MouseEvent event) throws IOException {
        String usuario = usuarioUsuario.getText();
        String nombre = usuarioNombre.getText();
        String gmail = usuarioGmail.getText();
        String clave = usuarioClave.getText();
        String confirmarClave = usuarioConfirmarClave.getText();
        System.out.printf(clave);
        System.out.println(confirmarClave);
        if (clave.equals(confirmarClave)) {
            us = usDao.modificarUsuario(1, usuario, nombre, gmail, clave);
            mostrarUsuarioModificadoAlert();
            mostrarNombreUsuario(usuarioSesion.getIdUsuario());
        } else {
            mostrarClaveDiferenteAlert();
        }


    }

    //    ventanas emergentes

    private void mostrarClaveDiferenteAlert() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/alertas/ErrorClaveDiferente.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> stage.close()));
        timeline.play();
    }

    private void mostrarUsuarioModificadoAlert() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/alertas/usuarioModificado.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> stage.close()));
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


}
