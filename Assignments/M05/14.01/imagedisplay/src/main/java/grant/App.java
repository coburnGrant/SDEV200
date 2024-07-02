package grant;

import java.io.IOException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        String[] imageURLs = {
                "https://github.com/coburnGrant/SDEV200/blob/53cf05469658d11aecfc941d14edd83689274ce4/Assignments/M05/14.01/imagedisplay/src/main/java/grant/Images/flag1.gif?raw=true",
                "https://github.com/coburnGrant/SDEV200/blob/53cf05469658d11aecfc941d14edd83689274ce4/Assignments/M05/14.01/imagedisplay/src/main/java/grant/Images/flag2.gif?raw=true",
                "https://github.com/coburnGrant/SDEV200/blob/53cf05469658d11aecfc941d14edd83689274ce4/Assignments/M05/14.01/imagedisplay/src/main/java/grant/Images/flag6.gif?raw=true",
                "https://github.com/coburnGrant/SDEV200/blob/53cf05469658d11aecfc941d14edd83689274ce4/Assignments/M05/14.01/imagedisplay/src/main/java/grant/Images/flag7.gif?raw=true"
        };

        GridPane pane = new GridPane();

        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(10);

        int row = 0;
        int col = 0;
        for(String url : imageURLs) {
            Image image = new Image(url);
            ImageView imageView = new ImageView(image);

            imageView.setFitWidth(200);
            imageView.setFitHeight(100);

            pane.add(imageView, row, col);

            col++;

            if(col > 1) {
                col = 0;
                row++;
            }
        }

        scene = new Scene(pane);

        stage.setTitle("Flags");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}