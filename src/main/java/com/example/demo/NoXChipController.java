package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class NoXChipController {
    @FXML
    private Label noXChipLabel;

    public void setData(Chip chip) {
        this.noXChipLabel.setText(chip.getLabel());
    }
}
