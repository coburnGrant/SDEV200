package com.example.staff;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class LabeledTextField {
    String textFieldLabel;
    TextField textField;

    public LabeledTextField(String label) {
        this.textFieldLabel = label;
        this.textField = new TextField();
    }

    public HBox getHBox() {
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER_LEFT);

        Text text = new Text(textFieldLabel);
        hBox.getChildren().addAll(text, textField);

        return hBox;
    }

    public String getText() {
        return textField.getText();
    }

    public void clearText() {
        textField.setText("");
    }
}
