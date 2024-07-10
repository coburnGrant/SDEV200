module com.example.staff {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.example.staff to javafx.fxml;
    exports com.example.staff;
}
