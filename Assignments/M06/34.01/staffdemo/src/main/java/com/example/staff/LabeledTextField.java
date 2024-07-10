package com.example.staff;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class LabeledTextField {
    private final String textFieldLabel;
    private final TextField textField;

    public LabeledTextField(String label) {
        this.textFieldLabel = label;
        this.textField = new TextField();
    }

    /** Makes an HBox with the label and text field */
    public HBox getHBox() {
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER_LEFT);

        Text text = new Text(textFieldLabel);
        hBox.getChildren().addAll(text, textField);

        return hBox;
    }

    /** Getter for the current value of the textField's text */
    public String getText() {
        return textField.getText();
    }

    /** Setter for the textField's text */
    public void setText(String text) {
        textField.setText(text);
    }

    /** Sets the textField's text an empty string */
    public void clearText() {
        setText("");
    }
}
