package grant;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/** Class that contains various GUI helpers to allow for easier GUI creation */
public class UIHelpers {
    // Static app colors
    public static final Color PRIMARY_COLOR = Color.rgb(0, 0, 0);
    public static final Color SECONDARY_COLOR = Color.rgb(22, 22, 24);
    public static final Color TERTIARY_COLOR = Color.rgb(33, 33, 36);
    public static final Color QUATERNARY_COLOR = Color.rgb(129, 129, 129);

    // Backgrounds
    public static final Background PRIMARY_BACKGROUND = new Background(new BackgroundFill(UIHelpers.PRIMARY_COLOR, CornerRadii.EMPTY, Insets.EMPTY));

    // Static text properties
    public static final Color PRIMARY_TEXT_COLOR = Color.WHITE;
    public static final String FONT_FAMILY = "Arial";

    /** Creates a styled button */
    public static Button createStyledButton(String text, double width, FontWeight fontWeight, double fontSize) {
        Button button = new Button(text);

        if (width != 0) {
            button.setPrefWidth(width);
        }

        // Create background fills
        Background normalBackground = new Background(new BackgroundFill(TERTIARY_COLOR, new CornerRadii(5), Insets.EMPTY));
        Background hoverBackground = new Background(new BackgroundFill(QUATERNARY_COLOR, new CornerRadii(5), Insets.EMPTY));
        Background pressedBackground = new Background(new BackgroundFill(QUATERNARY_COLOR.darker(), new CornerRadii(5), Insets.EMPTY));

        // Apply styles
        button.setBackground(normalBackground);
        button.setTextFill(PRIMARY_TEXT_COLOR);

        button.setFont(Font.font(FONT_FAMILY, fontWeight, fontSize));
        button.setPadding(new Insets(10));
        button.setBorder(new Border(new BorderStroke(Color.rgb(80, 80, 80), BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(1))));

        // Handle mouse events
        button.setOnMouseEntered(e -> button.setBackground(hoverBackground));
        button.setOnMouseExited(e -> button.setBackground(normalBackground));
        button.setOnMousePressed(e -> button.setBackground(pressedBackground));
        button.setOnMouseReleased(e -> button.setBackground(hoverBackground));

        return button;
    }

    /** Create a button for the navbar. */
    public static Button createNavButton(String text) {
        return createStyledButton(text, 150, FontWeight.BOLD, 16);
    }

    /** Creates a styled button with default values. */
    public static Button createSimpleButton(String text) {
        return createStyledButton(text, 0, FontWeight.NORMAL, 14);
    }

    /** Create a Text node with the primary text color. */
    public static Text primaryText(String text, double fontSize) {
        Text txt = new Text(text);
        txt.setFont(Font.font(fontSize));
        txt.setFill(PRIMARY_TEXT_COLOR);
        return txt;
    }

    /** Creates a Text node with the primary text color and default size 12 font size. */
    public static Text primaryText(String text) {
        return primaryText(text, 12);
    }

    /** Creates text that is styled for a title */
    public static Text titleText(String text) {
        Text title = new Text(text);
        title.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 24));
        title.setFill(PRIMARY_TEXT_COLOR);

        return title;
    }

    /** Creates text that is styled for a subtitle */
    public static Text subtitleText(String text) {
        Text title = new Text(text);
        title.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 20));
        title.setFill(Color.GRAY);

        return title;
    }

    /** Creates text that is styled for a heading */
    public static Text headingText(String text) {
        Text txt = new Text(text);
        txt.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 18));
        txt.setFill(PRIMARY_TEXT_COLOR);
        return txt;
    }

    /** Helper to create a text node with font weight and size */
    public static Text createText(String text, FontWeight weight, double size) {
        Text newText = new Text(text);
        newText.setFont(Font.font(FONT_FAMILY, weight, size));
        newText.setFill(PRIMARY_TEXT_COLOR);
        return newText;
    }
}