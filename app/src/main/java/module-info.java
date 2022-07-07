module application.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens application.app to javafx.fxml;
    exports application.app;
}