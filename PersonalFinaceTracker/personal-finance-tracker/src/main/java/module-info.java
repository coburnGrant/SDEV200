module grant {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    opens grant to javafx.fxml;
    exports grant;
}
