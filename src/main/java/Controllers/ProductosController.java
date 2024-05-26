package Controllers;

import Dao.Productos;
import Dao.ProductosDao;
import com.mycompany.la_perla.PrincipalController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductosController implements Initializable {
    private Productos pro;
    private ProductosDao proDao;
    private PrincipalController pc;
    private ObservableList<Productos> productosList;


    public ProductosController(Productos pro, ProductosDao proDao, PrincipalController pc) {
        this.pro = pro;
        this.proDao = proDao;
        this.pc = pc;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        proDao = new ProductosDao();
        productosList = FXCollections.observableArrayList();
        pc.tablaProductos.setItems(productosList);
        cargarDatosEnTabla("");
    }

    private void buscarProductos(javafx.scene.input.KeyEvent keyEvent) {
        String valor = pc.buscadorProductos.getText();
        cargarDatosEnTabla(valor);
    }

    public void cargarDatosEnTabla(String valor) {
        ObservableList<Productos> datos = proDao.ListaProductos(valor);
        Platform.runLater(() -> pc.tablaProductos.setItems(datos));

        for (Productos productos : datos) {
            System.out.println("Productos - Código: " + productos.getCodigoProducto() +
                    ", Nombre: " + productos.getNombreProducto() +
                    ", Categoria: " + productos.getCategoriaProducto()+
                    ", Precio: "+ productos.getPrecioProducto()+
                    ", Stock: "+ productos.getStockProducto());
        }
    }

    public void iniciarCargaDatos() {
        cargarDatosEnTabla("");
    }

    public void agregarProveedor(Productos producto) {
        List<Productos> productos = proDao.ListaProductos("");
        productos.add(producto);
        ObservableList<Productos> datos = FXCollections.observableArrayList(productos);
        javafx.application.Platform.runLater(() -> pc.tablaProductos.setItems(datos));
    }
}
