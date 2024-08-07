package Security;

import CrudControllers.EliminarClienteController;
import Dao.Cliente;
import Dao.Conexion;
import Dao.UsuarioDao;
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
import java.sql.SQLException;

public class OlvidasteContrasena {

    @javafx.fxml.FXML
    private TextField usuario;
    @javafx.fxml.FXML
    private Button btnRestablecerContrasena;
    @javafx.fxml.FXML
    private PasswordField Correo;

    private UsuarioDao usuarioDao = new UsuarioDao(); // Asumiendo que UsuarioDao toma la conexión en el constructor

    public OlvidasteContrasena() throws SQLException {
        this.usuarioDao = new UsuarioDao();
    }


    @javafx.fxml.FXML
    public void RestablecerContrasena(ActionEvent actionEvent) throws IOException {
        String usuarioIngresado = usuario.getText();
        String correoIngresado = Correo.getText();

        if (usuarioIngresado.isEmpty() || correoIngresado.isEmpty()) {
            mostrarAlerta("error", "Por favor, ingrese su usuario y correo.");
            return;
        }

        try {
            usuarioDao.recuperarContrasena(usuarioIngresado, correoIngresado);
            mostrarAlerta("info", "Se ha enviado un código de verificación a su correo.");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/la_perla/RestablecerContrasena.fxml")); // Ruta correcta de tu archivo FXML
            Parent root = loader.load();

            Scene scene = root.getScene();
            if (scene == null) {
                scene = new Scene(root);
            }

            BoxBlur blurEffect = new BoxBlur(5, 5, 3);
            ((Node) actionEvent.getSource()).getScene().getRoot().setEffect(blurEffect);

            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(scene);
            nuevaVentana.setTitle("Verificar Código");
            nuevaVentana.initStyle(StageStyle.UNDECORATED);
            nuevaVentana.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            nuevaVentana.setOnCloseRequest(e -> ((Node) actionEvent.getSource()).getScene().getRoot().setEffect(null));
            nuevaVentana.show();

        } catch (SQLException e) {
            mostrarAlerta("error", "Error al procesar la solicitud: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String error, String s) {

    }


    @javafx.fxml.FXML
    public void RegresarLobby(ActionEvent actionEvent) throws IOException {
        App.setRoot("Login");
    }
}
