module grant {
    requires javafx.controls;
    requires javafx.fxml;

    opens grant to javafx.fxml;
    exports grant;
}
