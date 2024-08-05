package Security;

import CrudControllers.EliminarClienteController;
import Dao.Cliente;
import com.mycompany.la_perla.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class OlvidasteContrasena {

    @javafx.fxml.FXML
    private TextField usuario;
    @javafx.fxml.FXML
    private Button btnRestablecerContrasena;
    @javafx.fxml.FXML
    private PasswordField Correo;

    @javafx.fxml.FXML
    public void RestablecerContrasena(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/la_perla/RestablecerContrasena.fxml")); // Asegúrate de que la ruta es correcta
        Parent root = loader.load();


        Scene scene = root.getScene();
        if (scene == null) {
            scene = new Scene(root);
        }

        BoxBlur blurEffect = new BoxBlur(5, 5, 3);
        ((Node) actionEvent.getSource()).getScene().getRoot().setEffect(blurEffect);

        Stage nuevaVentana = new Stage();
        nuevaVentana.setScene(scene);
        nuevaVentana.setTitle("Restablecer Contraseña");
//        nuevaVentana.initModality(Modality.APPLICATION_MODAL);
        nuevaVentana.initStyle(StageStyle.UNDECORATED);  // Descomentar esta línea si no deseas bordes del sistema operativo
        nuevaVentana.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        nuevaVentana.setOnCloseRequest(e -> ((Node) actionEvent.getSource()).getScene().getRoot().setEffect(null));
        nuevaVentana.show();
    }


    @javafx.fxml.FXML
    public void RegresarLobby(ActionEvent actionEvent) throws IOException {
        App.setRoot("Login");
    }
}
