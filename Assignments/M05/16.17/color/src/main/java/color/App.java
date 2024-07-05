package color;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        root.setPadding(new Insets(20));

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        Text colorText = new Text("Show Colors");
        colorText.setFill(Color.BLACK);

        vbox.getChildren().add(colorText);

        // Create sliders for red, green, blue, and opacity
        ColorSlider redSlider = new ColorSlider("Red", createColorSlider());
        ColorSlider blueSlider = new ColorSlider("Blue", createColorSlider());
        ColorSlider greenSlider = new ColorSlider("Green", createColorSlider());
        ColorSlider opacitySlider = new ColorSlider("Opacity", createOpacitySlider());
 
        ColorSlider[] sliders = { redSlider, greenSlider, blueSlider, opacitySlider };

        // Add all sliders to vBox and add event listener
        for(ColorSlider slider: sliders) {
            // Add sliders to the vbox with corresponding labels
            addSliderToVBox(vbox, slider);

            // Add event listener for slider
            slider.slider.valueProperty().addListener(e -> {
                updateTextColor(colorText, redSlider.slider, greenSlider.slider, blueSlider.slider, opacitySlider.slider);
            });
        }

        root.getChildren().add(vbox);
        StackPane.setAlignment(vbox, Pos.CENTER);

        scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Color Picker");
        stage.show();
    }

    /** Creates a Slider for a color */
    private Slider createColorSlider() {
        Slider slider = new Slider(0, 1, 0);

        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        return slider;
    }

    /** Creates slider for opacity */
    private Slider createOpacitySlider() {
        Slider slider = new Slider(0, 1, 1);

        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        return slider;
    }

    /** Adds slider name and text in a horizontal fashion to VBox */
    private void addSliderToVBox(VBox vbox, ColorSlider colorSlider) {
        Text label = new Text(colorSlider.sliderLabel);

        HBox hBox = new HBox(10, label, colorSlider.slider);

        hBox.setAlignment(Pos.CENTER);

        vbox.getChildren().add(hBox);
    }

    /** Updates the color of the color text */
    private void updateTextColor(Text colorText, Slider redSlider, Slider greenSlider, Slider blueSlider, Slider opacitySlider) {
        double red = redSlider.getValue();
        double green = greenSlider.getValue();
        double blue = blueSlider.getValue();
        double opacity = opacitySlider.getValue();

        colorText.setFill(new Color(red, green, blue, opacity));
    }

    public static void main(String[] args) {
        launch();
    }
}