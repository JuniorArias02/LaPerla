module com.mycompany.la_perla {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.la_perla to javafx.fxml;
    opens CrudControllers to javafx.fxml;
//    opens Dao to javafx.fxml;
    opens Security to javafx.fxml;
    opens modelo to javafx.fxml;
    opens AlertController to javafx.fxml;
    opens alertas to javafx.fxml;
    opens Dao to javafx.base;
    opens Controllers to javafx.fxml;


    requires java.sql;
    requires java.desktop;
    requires jdk.jfr;
    requires itextpdf;
    requires java.mail;

    exports com.mycompany.la_perla;
    exports CrudControllers;
    exports AlertController;
    exports Controllers;
    exports Security;
}
