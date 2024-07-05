package Controllers;

import Dao.Proveedor;
import Dao.Ventas;
import Dao.VentasDao;
import com.mycompany.la_perla.PrincipalController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VentasController implements Initializable {
    Ventas ventas;
    VentasDao ventasDao;
    PrincipalController pc;
    private ObservableList<Ventas> ventasList;


    public VentasController(Ventas ventas, VentasDao ventasDao, PrincipalController pc) {
        this.ventas = ventas;
        this.ventasDao = ventasDao;
        this.pc = pc;
        this.pc.buscadorVentas.setOnKeyReleased(this::buscarVentas);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ventasDao = new VentasDao();
        ventasList = FXCollections.observableArrayList();
        pc.tablaVentas.setItems(ventasList);
    }


    private void buscarVentas(javafx.scene.input.KeyEvent keyEvent) {
        String valor = pc.buscadorVentas.getText();
        cargarDatosEnTabla(valor);
    }


    public void cargarDatosEnTabla(String valor) {
        ObservableList<Ventas> datos = ventasDao.ListaVentas(valor);
        Platform.runLater(() -> pc.tablaVentas.setItems(datos));

        for (Ventas ventas : datos) {
            System.out.println("Ventas - Código: " + ventas.getCodigo() +
                    ", cleinte: " + ventas.getCliente() +
                    ", fecha: " + ventas.getFecha() +
                    ", monto: " + ventas.getMonto());
        }
    }


    public void iniciarCargaDatos() {
        cargarDatosEnTabla("");
    }

    public void agregarVentas(Ventas ventas) {
        List<Ventas> ventasList1 = ventasDao.ListaVentas("");
        ventasList1.add(ventas);
        ObservableList<Ventas> datos = FXCollections.observableArrayList(ventasList1);
        javafx.application.Platform.runLater(() -> pc.tablaVentas.setItems(datos));
    }


}
