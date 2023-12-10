package com.example.demo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ToolbarController {
    @FXML
    private Button exitButton;

    @FXML
    private Button minimizeButton;

    @FXML
    private void onExitButtonClick() {
        Platform.exit();
    }

    @FXML
    private void onMinimizeButtonClick() throws IOException {
        Scenery.getInstance().getStage().setIconified(true);
    }
}
