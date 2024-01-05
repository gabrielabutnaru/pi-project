package com.example.demo;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * The controller for the chip component.
 */
public class ChipController {
    @FXML
    private Label chipLabel;

    @FXML
    private HBox xButton;

    /**
     * Function that draws a given word on the screen based on the chip component.
     * @param s word
     * @param onRemove click event
     */
    public void setData(String s, EventHandler<MouseEvent> onRemove) {
        chipLabel.setText(s);
        xButton.setOnMouseClicked(onRemove);
    }
}
