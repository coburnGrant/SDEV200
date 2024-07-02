package grant;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Image flagImage1 = new Image("http://liveexample.pearsoncmg.com/book/image/us.gif");
        Image flagImage2 = new Image("/Users/grantcoburn/Development/SDEV200/Assignments/M05/14.01/imagedisplay/src/main/java/grant/Images/flag1.gif");
        Image flagImage3 = new Image("http://liveexample.pearsoncmg.com/book/image/us.gif");
        Image flagImage4 = new Image("http://liveexample.pearsoncmg.com/book/image/us.gif");

        ImageView flagImageView1 = new ImageView(flagImage1);
        ImageView flagImageView2 = new ImageView(flagImage2);
        ImageView flagImageView3 = new ImageView(flagImage3);
        ImageView flagImageView4 = new ImageView(flagImage4);

        GridPane pane = new GridPane();

        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(10);
        pane.setVgap(10);

        pane.add(flagImageView1, 0, 0);
        pane.add(flagImageView2, 1, 0);
        pane.add(flagImageView3, 0, 1);
        pane.add(flagImageView4, 1, 1);

        scene = new Scene(pane);

        stage.setTitle("Flags");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}