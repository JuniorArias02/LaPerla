package Controllers;

import Dao.Cliente;
import Dao.ClienteDao;
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

public class ClienteController implements Initializable {
    private Cliente cli;
    private ClienteDao cliDao;
    private PrincipalController pc;
    private ObservableList<Cliente> clienteList;

    public ClienteController() {

    }

    public ClienteController(Cliente cli, ClienteDao cliDao, PrincipalController pc) {
        this.cli = cli;
        this.cliDao = cliDao;
        this.pc = pc;
        this.pc.buscadorCliente.setOnKeyReleased(this::buscarCliente);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cliDao = new ClienteDao();
        clienteList = FXCollections.observableArrayList();
        pc.tablaClientes.setItems(clienteList);
        cargarDatosEnTabla("");
    }

    private void buscarCliente(javafx.scene.input.KeyEvent keyEvent) {
        String valor = pc.buscadorCliente.getText();
        cargarDatosEnTabla(valor);
    }

    public void cargarDatosEnTabla(String valor) {
        ObservableList<Cliente> datos = cliDao.ListaClientes(valor);
        Platform.runLater(() -> pc.tablaClientes.setItems(datos));

        for (Cliente cliente : datos) {
            System.out.println(
                    "Código: " + cliente.getCodigo() +
                            ", Nombre: " + cliente.getNombre() +
                            ", telefono: " + cliente.getTelefono());
        }
    }

    public void iniciarCargaDatos() {
        cargarDatosEnTabla("");
    }

    public void agregarCliente(Cliente cliente) {
        List<Cliente> clientes = cliDao.ListaClientes("");
        clientes.add(cliente);
        ObservableList<Cliente> datos = FXCollections.observableArrayList(clientes);
        javafx.application.Platform.runLater(() -> pc.tablaClientes.setItems(datos));
    }
}
