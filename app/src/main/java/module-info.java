module application.app {
    requires javafx.controls;
    requires javafx.fxml;


    opens application.app to javafx.fxml;
    exports application.app;
}