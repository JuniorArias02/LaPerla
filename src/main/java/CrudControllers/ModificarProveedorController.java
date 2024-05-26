package CrudControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Controllers.ProveedorController;
import Dao.Proveedor;
import Dao.ProveedorDao;
import com.mycompany.la_perla.PrincipalController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import static java.lang.Long.*;

/**
 * FXML Controller class
 *
 * @author Junior
 */
public class ModificarProveedorController implements Initializable {
    Proveedor prov;
    private ProveedorController proveedorController; // Referencia al controlador principal
    private ProveedorDao provDao = new ProveedorDao();
    PrincipalController pc;


    @javafx.fxml.FXML
    private TextField nombreProveedor;

    @javafx.fxml.FXML
    private TextField codigoProveedor;

    @javafx.fxml.FXML
    private TextField telefonoProveedor;

    /**
     * Initializes the controller class.
     */

    public void setProveedorController(ProveedorController proveedorController) {
        this.proveedorController = proveedorController;
    }

    public void setProveedor(Proveedor prov) {
        this.prov = prov;
        this.codigoProveedor.setText(String.valueOf(prov.getCodigoProveedor()));
        this.nombreProveedor.setText(prov.getNombreProveedor());
        this.telefonoProveedor.setText(String.valueOf(prov.getTelefonoProveedor()));
//aqui desabilitamos el campo codigo
        this.codigoProveedor.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @javafx.fxml.FXML
    public void cancelarModificacionProveedor(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = stage.getOwner().getScene().getRoot();
        root.setEffect(null);
        stage.close();
    }


    @FXML
    public void confirmarModificacionProveedor(ActionEvent actionEvent) throws IOException {
        try {
            if (this.nombreProveedor.getText().isEmpty() || this.telefonoProveedor.getText().isEmpty()) {
                mostrarInfoCamposVacios();
            } else {
                int codigo = Integer.parseInt(this.codigoProveedor.getText());
                String nombre = this.nombreProveedor.getText();
                String telefonoTexto = this.telefonoProveedor.getText();
                Long telefono;

                telefono = valueOf(telefonoTexto);

                prov.setCodigoProveedor(codigo);
                prov.setNombreProveedor(nombre);
                prov.setTelefonoProveedor(telefono);

                // Actualizar el proveedor en la base de datos o lista
                provDao.modificarProveedor(codigo, nombre, telefono);
                proveedorController.iniciarCargaDatos();

                mostrarOperacionExitosa();
                limpiarCampos();

                // Cierra la ventana de modificación después de confirmar
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Parent root = stage.getOwner().getScene().getRoot();
                root.setEffect(null);
                stage.close();
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de formato", "el telefono del proveedor debe ser un número.", "");
        }
    }

    private void limpiarCampos() {
        this.codigoProveedor.setText("");
        this.nombreProveedor.setText("");
        this.telefonoProveedor.setText("");
    }

    private void mostrarAlerta(String titulo, String mensaje, String detalles) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.setContentText(detalles);
        alerta.showAndWait();
    }

    private void mostrarInfoCamposVacios() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/alertas/ErrorCamposVacios.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.8), event -> stage.close()));
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
