package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * The controller for the chip without the `x` component.
 */
public class NoXChipController {
    @FXML
    private Label noXChipLabel;

    /**
     * Function that draws a given word on the screen based on the chip component.
     * @param chip word
     */
    public void setData(String chip) {
        this.noXChipLabel.setText(chip);
    }
}
