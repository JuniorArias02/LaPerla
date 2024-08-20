package Security;

import Dao.UsuarioDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class RestablecerContrasena {
    @javafx.fxml.FXML
    private Button btnRestablecerContrasena;
    @javafx.fxml.FXML
    private TextField codigo;
    @javafx.fxml.FXML
    private PasswordField ConfirmarNuevaContrasena;
    @javafx.fxml.FXML
    private PasswordField NuevaContrasena;

    OlvidasteContrasena olvidasteContrasena = new OlvidasteContrasena();

    private UsuarioDao usuarioDao;

    public RestablecerContrasena() throws SQLException {
        usuarioDao = new UsuarioDao();
    }


    @javafx.fxml.FXML
    public void CambiarContrasena(ActionEvent actionEvent) {
        String codigoIngresado = codigo.getText();
        String nuevaContrasena = NuevaContrasena.getText();
        String confirmarNuevaContrasena = ConfirmarNuevaContrasena.getText();

        if (codigoIngresado.isEmpty() || nuevaContrasena.isEmpty() || confirmarNuevaContrasena.isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Todos los campos son obligatorios.");
            return;
        }

        if (!nuevaContrasena.equals(confirmarNuevaContrasena)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Las contraseñas no coinciden.");
            return;
        }

        if (!nuevaContrasena.matches("^(?=(.*[A-Za-z]){2})(?=(.*\\d){4}).+$")) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "La contraseña debe tener al menos 2 letras y 4 números.");
            return;
        }


        try {
            // Validar el código y actualizar la contraseña
            boolean codigoValido = usuarioDao.validarCodigo(codigoIngresado);
            if (codigoValido) {
                usuarioDao.actualizarContrasena(codigoIngresado, nuevaContrasena);
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Contraseña actualizada correctamente.");
                olvidasteContrasena.setLlave("s");

                // Cerrar la ventana actual
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Parent root = stage.getOwner().getScene().getRoot();
                root.setEffect(null);
                stage.close();

            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Código inválido.");
            }
        } catch (Exception e) {
//            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al actualizar la contraseña.");
            e.printStackTrace();
        }
    }

    public void limpiarCampos() {

    }


    @javafx.fxml.FXML
    public void CancelarOperacion(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = stage.getOwner().getScene().getRoot();
        root.setEffect(null);
        stage.close();
    }


    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
