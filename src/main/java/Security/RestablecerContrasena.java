package Security;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RestablecerContrasena {
    @javafx.fxml.FXML
    private Button btnRestablecerContrasena;
    @javafx.fxml.FXML
    private TextField codigo;
    @javafx.fxml.FXML
    private PasswordField ConfirmarNuevaContrasena;
    @javafx.fxml.FXML
    private PasswordField NuevaContrasena;


    @javafx.fxml.FXML
    public void CambiarContrasena(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void CancelarOperacion(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = stage.getOwner().getScene().getRoot();
        root.setEffect(null);
        stage.close();
    }
}
