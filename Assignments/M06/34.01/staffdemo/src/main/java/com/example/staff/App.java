package com.example.staff;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {
    static final String HOST = "localhost";
    static final int PORT = 3306;
    static final String DB_NAME = "Staff";

    private static Scene scene;

    StackPane pane;

    Text connectionStatusText;
    Circle connectionStatusCircle;

    Text resultText;

    LabeledTextField idField;
    LabeledTextField firstNameField;
    LabeledTextField lastNameField;
    LabeledTextField miField;
    LabeledTextField addressField;
    LabeledTextField cityField;
    LabeledTextField stateField;
    LabeledTextField telephoneField;
    LabeledTextField emailField;

    Connection connection;

    @Override
    public void start(Stage stage) throws IOException {
        // Establish connection to data base
        establishConnection();

        // Create pane with views
        createPane();

        scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Staff Management System");
        stage.show();
    }

    // MARK: DB Connection

    /** Attempts to establish a connection to the mysql database */
    private void establishConnection() {
        try {
            String dbURL = getSqlUrl(HOST, PORT, DB_NAME);

            String username = "root";
            String password = "";

            connection = DriverManager.getConnection(dbURL, username, password);

            System.out.println("Successfully connected to " + dbURL);
        } catch (SQLException e) {
            System.out.println("Error connecting to database");
            e.printStackTrace();
        }
    }

    /** Helper function to format a sql url string */
    public static String getSqlUrl(String host, int port, String dbName) {
        return String.format("jdbc:mysql://%s:%d/%s", host, port, dbName);
    }

    /** Creates the main stack pane */
    private void createPane() {
        pane = new StackPane();
        pane.setPadding(new Insets(20));

        initializeViews();

        VBox form = createForm();

        pane.getChildren().add(form);

        pane.autosize();
    }

    /** Initializes all nodes */
    private void initializeViews() {
        // Create connection text based on connection status
        String connectionResultString = connection == null ? "Failed to connect to database" : "Connected to database";
        connectionStatusText = new Text(connectionResultString);

        // Make connection circle based on connection status
        connectionStatusCircle = new Circle(5);
        connectionStatusCircle.setFill(connection == null ? Color.RED : Color.LIME);

        // Make empty text for result
        resultText = new Text("");

        // Make text fields
        idField = new LabeledTextField("ID");
        firstNameField = new LabeledTextField("First Name");
        lastNameField = new LabeledTextField("Last Name");
        miField = new LabeledTextField("MI");
        addressField = new LabeledTextField("Address");
        cityField = new LabeledTextField("City");
        stateField = new LabeledTextField("State");
        telephoneField = new LabeledTextField("Telephone");
        emailField = new LabeledTextField("Email");
    }

    /** Adds all text fields to a VBox */
    private VBox createForm() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setPadding(new Insets(20));

        HBox connectionHBox = new HBox(5);
        connectionHBox.setAlignment(Pos.CENTER_LEFT);
        connectionHBox.getChildren().addAll(connectionStatusText, connectionStatusCircle);

        HBox staffNameHBox = new HBox(10);
        staffNameHBox.setAlignment(Pos.CENTER_LEFT);
        staffNameHBox.getChildren().addAll(firstNameField.getHBox(), lastNameField.getHBox(), miField.getHBox());

        HBox cityAndStateHBox = new HBox(10);
        cityAndStateHBox.setAlignment(Pos.CENTER_LEFT);
        cityAndStateHBox.getChildren().addAll(cityField.getHBox(), stateField.getHBox());

        HBox userContactHBox = new HBox(10);
        userContactHBox.setAlignment(Pos.CENTER_LEFT);
        userContactHBox.getChildren().addAll(telephoneField.getHBox(), emailField.getHBox());

        // Make buttons
        Button viewButton = new Button("View");
        viewButton.setOnAction(e -> viewClicked());

        Button insertButton = new Button("Insert");
        insertButton.setOnAction(e -> insertClicked());

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> updateClicked());

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> clearClicked());

        HBox buttonsHBox = new HBox(10);
        buttonsHBox.setAlignment(Pos.CENTER);
        buttonsHBox.getChildren().addAll(viewButton, insertButton, updateButton, clearButton);

        vbox.getChildren().addAll(
                connectionHBox,
                resultText,
                idField.getHBox(),
                staffNameHBox,
                addressField.getHBox(),
                cityAndStateHBox,
                userContactHBox,
                buttonsHBox);

        return vbox;
    }

    // MARK: Button Event handlers

    /** Handles view button clicked event */
    private void viewClicked() {
        System.out.println("View Clicked!");

        String id = idField.getText();

        String sql = "SELECT * FROM Staff WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                String mi = result.getString("mi");
                String address = result.getString("address");
                String city = result.getString("city");
                String state = result.getString("state");
                String telephone = result.getString("telephone");
                String email = result.getString("email");

                resultText.setText("Found staff member with ID: " + id);

                firstNameField.setText(firstName);
                lastNameField.setText(lastName);
                miField.setText(mi);
                addressField.setText(address);
                cityField.setText(city);
                stateField.setText(state);
                telephoneField.setText(telephone);
                emailField.setText(email);
            } else {
                resultText.setText("No staff member found with ID: " + id);
            }
        } catch (Exception e) {
            resultText.setText("Could not view staff member with ID: " + id);

            e.printStackTrace();
        }
    }

    /** Handles insert button clicked event */
    private void insertClicked() {
        System.out.println("Insert Clicked!");
        // Get values from text fields
        String id = idField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String mi = miField.getText();
        String address = addressField.getText();
        String city = cityField.getText();
        String state = stateField.getText();
        String telephone = telephoneField.getText();
        String email = emailField.getText();

        String sql = "INSERT INTO Staff (id, lastName, firstName, mi, address, city, state, telephone, email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            statement.setString(2, lastName);
            statement.setString(3, firstName);
            statement.setString(4, mi);
            statement.setString(5, address);
            statement.setString(6, city);
            statement.setString(7, state);
            statement.setString(8, telephone);
            statement.setString(9, email);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                resultText.setText("A new staff member was inserted successfully!");
                System.out.println("A new staff member was inserted successfully!");

                clearFields();
            }
        } catch (Exception e) {
            resultText.setText("Error inserting staff member!");
            System.out.println("Error inserting staff member!");
            e.printStackTrace();
        }
    }

    /** Handles update button clicked event */
    private void updateClicked() {
        System.out.println("Update Clicked!");

        String id = idField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String mi = miField.getText();
        String address = addressField.getText();
        String city = cityField.getText();
        String state = stateField.getText();
        String telephone = telephoneField.getText();
        String email = emailField.getText();

        String sql = "UPDATE Staff " +
                "SET firstName = ?, lastName = ?, mi = ?, address = ?, city = ?, state = ?, telephone = ?, email = ?" +
                "WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, mi);
            statement.setString(4, address);
            statement.setString(5, city);
            statement.setString(6, state);
            statement.setString(7, telephone);
            statement.setString(8, email);
            statement.setString(9, id);

            int rowUpdatedCount = statement.executeUpdate();
            if (rowUpdatedCount > 0) {
                resultText.setText("Successfully updated staff member with id " + id + "'s info");

                clearFields();
            } else {
                resultText.setText("Failed to update staff member with id " + id + "'s info");
            }
        } catch (Exception e) {
            resultText.setText("Failed to update staff member with id " + id + "'s info");

            e.printStackTrace();
        }
    }

    /** Handles clear button clicked event */
    private void clearClicked() {
        System.out.println("Clear Clicked!");
        resultText.setText("");
        clearFields();
    }

    /** Returns list of all text fields */
    private LabeledTextField[] getFields() {
        return new LabeledTextField[] { idField, lastNameField, firstNameField, miField, addressField, cityField,
                stateField, telephoneField, emailField };
    }

    /** Clears all of the text in the text fields */
    private void clearFields() {
        LabeledTextField[] fields = getFields();

        for (LabeledTextField field : fields) {
            field.clearText();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}