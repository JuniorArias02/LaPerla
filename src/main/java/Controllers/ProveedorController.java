package Controllers;

import Dao.Proveedor;
import Dao.ProveedorDao;
import com.mycompany.la_perla.PrincipalController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProveedorController implements Initializable {

    private Proveedor prov;
    private ProveedorDao provDao;
    private PrincipalController pc;
    private ObservableList<Proveedor> proveedorList;

    public ProveedorController() {

    }


    public ProveedorController(Proveedor prov, ProveedorDao provDao, PrincipalController pc) {
        this.prov = prov;
        this.provDao = provDao;
        this.pc = pc;
        this.pc.buscadorProveedor.setOnKeyReleased(this::buscarProveedor);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        provDao = new ProveedorDao();
        proveedorList = FXCollections.observableArrayList();
        pc.tablaProveedor.setItems(proveedorList);

        cargarDatosEnTabla("");

    }

    private void buscarProveedor(javafx.scene.input.KeyEvent keyEvent) {
        String valor = pc.buscadorProveedor.getText();
        cargarDatosEnTabla(valor);
    }

    public void cargarDatosEnTabla(String valor) {
        ObservableList<Proveedor> datos = provDao.ListaProveedor(valor);
        Platform.runLater(() -> pc.tablaProveedor.setItems(datos));

        for (Proveedor proveedor : datos) {
            System.out.println("Proveedor - Código: " + proveedor.getCodigoProveedor() +
                    ", Nombre: " + proveedor.getNombreProveedor() +
                    ", Teléfono: " + proveedor.getTelefonoProveedor());
        }
    }

    public void iniciarCargaDatos() {
        cargarDatosEnTabla("");
    }

    public void agregarProveedor(Proveedor proveedor) {
        List<Proveedor> proveedores = provDao.ListaProveedor("");
        proveedores.add(proveedor);
        ObservableList<Proveedor> datos = FXCollections.observableArrayList(proveedores);
        javafx.application.Platform.runLater(() -> pc.tablaProveedor.setItems(datos));
    }

}


