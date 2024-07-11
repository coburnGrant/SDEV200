module grant {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens grant to javafx.fxml;
    exports grant;
}
