package colorchange;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Pane pane = new Pane();
        
        // Set grey background color
        pane.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        
        // Set minimum pane size
        pane.setMinWidth(200);
        pane.setMinHeight(200);

        Circle circle = new Circle(50);

        // Center circle in middle of pane
        circle.centerXProperty().bind(pane.widthProperty().divide(2));
        circle.centerYProperty().bind(pane.heightProperty().divide(2));

        // Fill initial color black
        circle.setFill(Color.BLACK);

        // Change color to white on mouse press
        circle.setOnMousePressed(e -> {
            circle.setFill(Color.WHITE);
        });

        // Change color back to black on mouse release
        circle.setOnMouseReleased(e -> {
            circle.setFill(Color.BLACK);
        });

        scene = new Scene(pane);

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}