package com.example.demo;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class ChipController {
    @FXML
    private Label chipLabel;

    @FXML
    private HBox xButton;
    private EventHandler<MouseEvent> onRemove;

    public void setData(String s, EventHandler<MouseEvent> onRemove) {
        chipLabel.setText(s);
        xButton.setOnMouseClicked(onRemove);
    }
}
