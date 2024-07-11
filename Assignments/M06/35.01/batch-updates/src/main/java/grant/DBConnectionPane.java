package grant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class DBConnectionPane extends BorderPane {
    private final StackPane pane = new StackPane();

    private final LabeledTextField jdbcDriveTextField = new LabeledTextField("JDBC Drive");

    private final LabeledTextField dbURLTextField = new LabeledTextField("Database URL");

    private final LabeledTextField usernameField = new LabeledTextField("Username");

    private final LabeledTextField passwordField = new LabeledTextField("Password");

    private final Button connectButton = new Button("Connect to DB");

    private final Text connectionResultText = new Text("");
    
    private final Circle connectionResultCircle = new Circle(5);

    private Connection connection;

    public DBConnectionPane() {
        pane.setPadding(new Insets(10));

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.TOP_RIGHT);

        connectButton.setAlignment(Pos.BOTTOM_RIGHT);
        connectButton.setOnAction(e -> establishConnection());

        connectionResultCircle.setFill(new Color(0, 0, 0, 0));

        HBox connectionResultHBox = new HBox(5);
        connectionResultHBox.getChildren().addAll(connectionResultText, connectionResultCircle);
        connectionResultHBox.setAlignment(Pos.TOP_LEFT);

        vbox.getChildren().addAll(
            connectionResultHBox, 
            jdbcDriveTextField.getHBox(), 
            dbURLTextField.getHBox(), 
            usernameField.getHBox(), 
            passwordField.getHBox(),
            connectButton
        );

        pane.getChildren().add(vbox);

        this.setCenter(pane);
    }

    /** Attempts to establish a connection to a database using textfields */
    private void establishConnection() {
        String driver = jdbcDriveTextField.getText();
        String dbURl = dbURLTextField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            connection = DriverManager.getConnection(dbURl, username, password);
            
            String url = connection.getMetaData().getURL().toString();
            System.out.println("Established connection to database " + url);
            
            connectionResultText.setText("DB Connected to..." + url);
            connectionResultCircle.setFill(Color.GREEN);
        } catch(SQLException e) {
            connectionResultText.setText("Failed to connect to DB...");
            connectionResultCircle.setFill(Color.RED);
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}