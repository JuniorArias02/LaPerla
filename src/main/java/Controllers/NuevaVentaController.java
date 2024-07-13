package Controllers;

import Dao.Productos;
import Dao.ProductosDao;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;


public class NuevaVentaController {
    private TableView<Productos> tablaNuevaVentas;
    private TableColumn<Productos, Integer> productoIdColumna;
    private TableColumn<Productos, String> productoNombreColumna;
    private TableColumn<Productos, Integer> productoCantidadColumna;
    private TableColumn<Productos, Double> productoPrecioColumna;
    private TableColumn<Productos, Double> productoTotalColumna;
    private Label TotalVentaPago;

    private ProductosDao productosDao = new ProductosDao();

    public NuevaVentaController(TableView<Productos> tablaNuevaVentas,
                                TableColumn<Productos, Integer> productoIdColumna,
                                TableColumn<Productos, String> productoNombreColumna,
                                TableColumn<Productos, Integer> productoCantidadColumna,
                                TableColumn<Productos, Double> productoPrecioColumna,
                                TableColumn<Productos, Double> productoTotalColumna,
                                Label TotalVentaPago) {
        this.tablaNuevaVentas = tablaNuevaVentas;
        this.productoIdColumna = productoIdColumna;
        this.productoNombreColumna = productoNombreColumna;
        this.productoCantidadColumna = productoCantidadColumna;
        this.productoPrecioColumna = productoPrecioColumna;
        this.productoTotalColumna = productoTotalColumna;
        this.TotalVentaPago = TotalVentaPago;

        initialize();
    }

    private void initialize() {
        productoIdColumna.setCellValueFactory(new PropertyValueFactory<>("codigoProducto"));
        productoNombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        productoCantidadColumna.setCellValueFactory(new PropertyValueFactory<>("cantidadProducto"));
        productoPrecioColumna.setCellValueFactory(new PropertyValueFactory<>("precioProducto"));
        productoTotalColumna.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    public void agregarProductoATabla(int id) throws SQLException {
        try {
            Productos productoExistente = null;
            for (Productos producto : tablaNuevaVentas.getItems()) {
                if (producto.getCodigoProducto() == id) {
                    productoExistente = producto;
                    break;
                }
            }

            if (productoExistente != null) {
                int nuevaCantidad = productoExistente.getCantidadProducto() + 1;
                productoExistente.setCantidadProducto(nuevaCantidad);
                productoExistente.setTotal(productoExistente.getPrecioProducto() * nuevaCantidad);

                tablaNuevaVentas.refresh();
            } else {
                Productos productoNuevo = productosDao.buscarProducto(id);
                if (productoNuevo != null) {
                    productoNuevo.setCantidadProducto(1);
                    productoNuevo.setTotal(productoNuevo.getPrecioProducto() * productoNuevo.getCantidadProducto());
                    tablaNuevaVentas.getItems().add(productoNuevo);
                } else {
                    System.out.println("Producto no encontrado");
                }
            }

            actualizarTotalVenta();

        } catch (SQLException e) {
            e.printStackTrace();
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
