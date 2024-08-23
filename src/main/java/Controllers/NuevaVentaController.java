package Controllers;

import Dao.Productos;
import Dao.ProductosDao;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import jdk.jfr.Event;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class NuevaVentaController {
    private TableView<Productos> tablaNuevaVentas;
    private TableColumn<Productos, Integer> productoIdColumna;
    private TableColumn<Productos, String> productoNombreColumna;
    private TableColumn<Productos, Integer> productoCantidadColumna;
    private TableColumn<Productos, Double> productoPrecioColumna;
    private TableColumn<Productos, Double> productoTotalColumna;
    private TableColumn<Productos, Integer> productoStockVentaColumn;

    private Label TotalVentaPago;

    private ProductosDao productosDao = new ProductosDao();

    public NuevaVentaController(TableView<Productos> tablaNuevaVentas,
                                TableColumn<Productos, Integer> productoIdColumna,
                                TableColumn<Productos, String> productoNombreColumna,
                                TableColumn<Productos, Integer> productoCantidadColumna,
                                TableColumn<Productos, Double> productoPrecioColumna,
                                TableColumn<Productos, Double> productoTotalColumna,
                                Label TotalVentaPago, TableColumn productoStockVentaColumn) {
        this.tablaNuevaVentas = tablaNuevaVentas;
        this.productoIdColumna = productoIdColumna;
        this.productoNombreColumna = productoNombreColumna;
        this.productoCantidadColumna = productoCantidadColumna;
        this.productoPrecioColumna = productoPrecioColumna;
        this.productoTotalColumna = productoTotalColumna;
        this.productoStockVentaColumn = productoStockVentaColumn;
        this.TotalVentaPago = TotalVentaPago;

        initialize();
    }

    public List<Productos> obtenerProductosSeleccionados() {
        return new ArrayList<>(tablaNuevaVentas.getItems());
    }

    private void initialize() {
        productoIdColumna.setCellValueFactory(new PropertyValueFactory<>("codigoProducto"));
        productoNombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        productoCantidadColumna.setCellValueFactory(new PropertyValueFactory<>("cantidadProducto"));
        productoPrecioColumna.setCellValueFactory(new PropertyValueFactory<>("precioProducto"));
        productoTotalColumna.setCellValueFactory(new PropertyValueFactory<>("total"));
        productoStockVentaColumn.setCellValueFactory(new PropertyValueFactory<>("stockProducto"));
    }

    public ObservableList<Productos> getProductosEnVenta() {
        return tablaNuevaVentas.getItems();
    }

    public String getTotalVentaPago() {
        return TotalVentaPago.getText();
    }

    public TableView<Productos> getTablaNuevaVentas() {
        return tablaNuevaVentas;
    }


    public Productos agregarProductoATabla(String identificador) throws SQLException {
        Productos productoDevuelto = null;
        try {
            // Extraer ID y cantidad del comando
            String[] partes = identificador.split("x");
            String idONombre = partes[0];
            int cantidad = (partes.length > 1) ? Integer.parseInt(partes[1]) : 1;

            // Busca el producto por ID o nombre usando el método único
            Productos productoNuevo = productosDao.buscarProducto(idONombre);

            if (productoNuevo != null) {
                // Verifica si el producto ya está en la tabla
                Productos productoExistente = null;
                for (Productos producto : tablaNuevaVentas.getItems()) {
                    if (producto.getCodigoProducto() == productoNuevo.getCodigoProducto()) {
                        productoExistente = producto;
                        break;
                    }
                }

                if (productoExistente != null) {
                    // Si el producto ya existe en la tabla, actualiza la cantidad
                    int nuevaCantidad = productoExistente.getCantidadProducto() + cantidad;
                    int stockDisponible = productoExistente.getStockProducto();

                    if (nuevaCantidad <= stockDisponible) {
                        productoExistente.setCantidadProducto(nuevaCantidad);
                        productoExistente.setTotal(productoExistente.getPrecioProducto() * nuevaCantidad);
                        tablaNuevaVentas.refresh();
                        productoDevuelto = productoExistente; // Devuelve el producto actualizado
                    } else {
                        mostrarAlerta("Stock insuficiente", "No hay suficiente stock disponible para este producto.", "");
                    }
                } else {
                    // Si el producto no está en la tabla, agrégalo
                    if (productoNuevo.getStockProducto() >= cantidad) {
                        productoNuevo.setCantidadProducto(cantidad);
                        productoNuevo.setTotal(productoNuevo.getPrecioProducto() * productoNuevo.getCantidadProducto());
                        tablaNuevaVentas.getItems().add(productoNuevo);
                        productoDevuelto = productoNuevo; // Devuelve el nuevo producto agregado
                    } else {
                        mostrarAlerta("Stock insuficiente", "El producto no tiene suficiente stock disponible.", "");
                    }
                }

                actualizarTotalVenta();

            } else {
                System.out.println("Producto no encontrado");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productoDevuelto;
    }

    private void mostrarAlerta(String titulo, String mensaje, String detalles) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.setContentText(detalles);
        alerta.showAndWait();
    }

    public void borrarProductoSeleccionado() {
        // Obtener el producto seleccionado de la tabla
        Productos productoSeleccionado = tablaNuevaVentas.getSelectionModel().getSelectedItem();

        if (productoSeleccionado != null) {
            // Eliminar el producto de la tabla
            tablaNuevaVentas.getItems().remove(productoSeleccionado);
            tablaNuevaVentas.refresh();
            actualizarTotalVenta(); // Actualizar el total de la venta
        } else {
            System.out.println("No hay producto seleccionado para borrar.");
        }
    }

    public void eliminarProductoSeleccionado() {
        Productos productoSeleccionado = tablaNuevaVentas.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null) {
            if (productoSeleccionado.getCantidadProducto() > 1) {
                productoSeleccionado.setCantidadProducto(productoSeleccionado.getCantidadProducto() - 1);
                productoSeleccionado.setTotal(productoSeleccionado.getPrecioProducto() * productoSeleccionado.getCantidadProducto());
            } else {
                tablaNuevaVentas.getItems().remove(productoSeleccionado);
            }
            tablaNuevaVentas.refresh();
            actualizarTotalVenta();
        } else {

            System.out.println("No hay producto seleccionado para eliminar.");
        }
    }
    private void actualizarTotalVenta() {
        double total = 0;
        for (Productos producto : tablaNuevaVentas.getItems()) {
            total += producto.getTotal();
        }
        TotalVentaPago.setText(String.valueOf(total));
    }


}
