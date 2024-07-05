package Controllers;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class MenuController {

    @FXML
    private Pane menu;

    private boolean menuVisible = true;

    @FXML
    private void toggleMenu() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(300), menu);
        if (menuVisible) {
            transition.setToX(-menu.getPrefWidth());
        } else {
            transition.setToX(0);
        }
        transition.play();
        menuVisible = !menuVisible;
    }
}
