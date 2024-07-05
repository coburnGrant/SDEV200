module color {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens color to javafx.fxml;
    exports color;
}
