module dev.esteban.betweenle_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens dev.esteban.betweenle_gui to javafx.fxml;
    exports dev.esteban.betweenle_gui;
}