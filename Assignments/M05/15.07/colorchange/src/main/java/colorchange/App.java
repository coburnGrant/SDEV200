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

/**
 * Write a program that displays the color of a circle as black when the mouse
 * button is pressed, and as white when the mouse button is released.
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Pane pane = new Pane();
        pane.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        pane.setMinWidth(200);
        pane.setMinHeight(200);

        Circle circle = new Circle(50);

        circle.setOnMousePressed(e -> {
            circle.setFill(Color.WHITE);
        });

        circle.setOnMouseReleased(e -> {
            circle.setFill(Color.BLACK);
        });

        circle.centerXProperty().bind(pane.widthProperty().divide(2));
        circle.centerYProperty().bind(pane.heightProperty().divide(2));

        circle.setFill(Color.BLACK);

        pane.getChildren().add(circle);

        scene = new Scene(pane);
        
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}