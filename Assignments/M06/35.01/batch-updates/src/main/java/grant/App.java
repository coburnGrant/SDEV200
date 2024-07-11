package grant;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;

    private final DBConnectionPane dbPane = new DBConnectionPane();

    private final TextArea resultTextArea = new TextArea();
    
    /** Connection established in database connection panel */
    private Connection connection;

    /** Name of database table to insert rows */
    private static final String dbTableName = "Temp";

    /** Number of rows to insert when testing */ 
    private static final int numRecordsToInsert = 1000;

    @Override
    public void start(Stage stage) throws IOException {
        StackPane pane = new StackPane();

        // Add button to show db connection pane
        Button connectToDBButton = new Button("Connect to database");
        connectToDBButton.setAlignment(Pos.CENTER_RIGHT);
        connectToDBButton.setOnAction(e -> showDBPane());

        resultTextArea.setEditable(false);
        resultTextArea.setWrapText(true);

        // Init update buttons
        Button sendBatchUpdateButton = new Button("Batch Update");
        sendBatchUpdateButton.setOnAction(e -> sendBatchUpdate());

        Button sendNonBatchUpdateButton = new Button("Non-Batch Update");
        sendNonBatchUpdateButton.setOnAction(e -> sendNonBatchUpdate());

        Button removeAllButton = new Button("Remove all");
        removeAllButton.setOnAction(e -> deleteAllValues());

        HBox updateButtonsHBox = new HBox(10);
        updateButtonsHBox.setAlignment(Pos.CENTER);
        updateButtonsHBox.getChildren().addAll(sendBatchUpdateButton, sendNonBatchUpdateButton, removeAllButton);

        // Add nodes to VBox
        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(connectToDBButton, resultTextArea, updateButtonsHBox);
        vBox.setPadding(new Insets(10));

        pane.getChildren().addAll(vBox);
        pane.autosize();

        scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Batch Update Tester");
        stage.show();
    }

    /** Displays database connection panel */
    private void showDBPane() {
        Scene dbScene = new Scene(dbPane);
        Stage dbStage = new Stage();
        dbStage.setTitle("Connect to DB");
        dbStage.setScene(dbScene);
        dbStage.show();
    }

    /**
     * Ensures that a connection is established to the database.
     * @return Boolean value indicating if a connection is established.
     */
    private boolean ensureConnection() {
        if(connection == null) {
            connection = dbPane.getConnection();

            if (connection == null) {
                resultUpdate("Add a connection to a database to test updates.");

                return false;
            } else {
                try {
                    resultUpdate("Connected to database..." + connection.getMetaData().getURL());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return ensureTableExists();
            }
        } else {
            return ensureTableExists();
        }
    }

    /**
     * Ensures a table exists within a data base. If it doesn't, attempts to create table
     * @return Boolean value indicating if the table exists
     */
    public boolean ensureTableExists() {
        if (tableExists()) {
            System.out.println("Table " + dbTableName + " already exists.");
            return true;
        } else {
            resultUpdate("Table '" + dbTableName + "' does not exist in database...creating table.");
            return createTable();
        }
    }

    /**
     * Checks if table exists within database.
     * @return Boolean value indicating if the table exists
     */
    public boolean tableExists() {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, dbTableName, new String[] { "TABLE" });

            return resultSet.next();
        } catch (SQLException e) {
            resultUpdate("Could not determine if table '" + dbTableName + "' exists within database.");

            return false;
        }

    }

    /**
     * Attempts to create the table in the database
     * @return Boolean value indicating if the table was successfully created
     */
    private boolean createTable() {
        String createTableSQL = "CREATE TABLE Temp(num1 double, num2 double, num3 double)";

        try {
            Statement statement = connection.createStatement();
            statement.execute(createTableSQL);
            resultUpdate("Table '" + dbTableName + "'' created successfully.");

            return true;
        } catch (SQLException e) {
            resultUpdate("Could not add table '" + dbTableName + "' to database: " + e.getMessage());

            return false;
        }
    }

    /** Attempts to send a batch update to database */
    private void sendBatchUpdate() {
        if (!ensureConnection()) {
            return;
        }

        // New line
        resultUpdate("");

        try {
            if (connection.getMetaData().supportsBatchUpdates()) {
                
                String sqlInsertString = "INSERT INTO Temp VALUES(?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertString);

                Random random = new Random();

                for (int i = 0; i < numRecordsToInsert; i++) {
                    int randomValue1 = random.nextInt(100);
                    int randomValue2 = random.nextInt(100);
                    int randomValue3 = random.nextInt(100);

                    preparedStatement.setInt(1, randomValue1);
                    preparedStatement.setInt(2, randomValue2);
                    preparedStatement.setInt(3, randomValue3);

                    preparedStatement.addBatch();
                }

                long startTime = System.currentTimeMillis();
                int[] rowsUpdated = preparedStatement.executeBatch();
                long endTime = System.currentTimeMillis();

                long elapsedTime = endTime - startTime;

                resultUpdate("Batch update complete \nInserted " + rowsUpdated.length + " rows in " + elapsedTime + " ms");
            } else {
                // batch updates not supported
                resultUpdate("Batch updates not supported for current database.");
            }

        } catch (SQLException e) {
            resultUpdate("Could not send batch update to database: \n" + e.getMessage());
        }
    }

    /** Attempts to send non-batch update to database */
    private void sendNonBatchUpdate() {
        if (!ensureConnection()) {
            return;
        }

        // New line
        resultUpdate("");

        try {
            int totalRows = 0;

            Random random = new Random();

            long totalTime = 0;

            for (int i = 0; i < numRecordsToInsert; i++) {
                String sqlInsertString = "INSERT INTO Temp VALUES(?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertString);

                int randomValue1 = random.nextInt(100);
                int randomValue2 = random.nextInt(100);
                int randomValue3 = random.nextInt(100);

                preparedStatement.setInt(1, randomValue1);
                preparedStatement.setInt(2, randomValue2);
                preparedStatement.setInt(3, randomValue3);

                long startTime = System.currentTimeMillis();
                int rowsUpdated = preparedStatement.executeUpdate();
                long endTime = System.currentTimeMillis();

                long elapsedTime = endTime - startTime;
                totalTime += elapsedTime;
                totalRows += rowsUpdated;
            }

            resultUpdate("Non-Batch update complete \nInserted " + totalRows + " rows in " + totalTime + " ms");

        } catch (SQLException e) {
            resultUpdate("Could not send non-batch update: " + e.getMessage());
        }
    }

    /** Attempts to remove all entries in database */
    private void deleteAllValues() {
        if (!ensureConnection()) {
            return;
        }

        // New line
        resultUpdate("");

        try {
            String sqlDeleteString = "DELETE FROM Temp";
            Statement statement = connection.createStatement();

            long startTime = System.currentTimeMillis();
            int rowsDeleted = statement.executeUpdate(sqlDeleteString);
            long endTime = System.currentTimeMillis();

            long elapsedTime = endTime - startTime;
            resultUpdate("All values deleted from database in " + elapsedTime + " ms. Rows affected: " + rowsDeleted);
        } catch (SQLException e) {
            resultUpdate("Error deleting values from database: " + e.getMessage());
        }
    }

    /** Appends text to result text area on a new line */
    private void resultUpdate(String result) {
        if (resultTextArea.getText().isEmpty()) {
            resultTextArea.appendText(result);
        } else {
            resultTextArea.appendText("\n" + result);
        }
    }

    public static void main(String[] args) {
        launch();
    }

}