module colorchange {
    requires javafx.controls;
    requires javafx.fxml;

    opens colorchange to javafx.fxml;
    exports colorchange;
}
