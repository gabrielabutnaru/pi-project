package com.example.demo;

import javafx.application.Platform;
import javafx.fxml.FXML;

import java.io.IOException;

/**
 * The controller for the toolbar component.
 */
public class ToolbarController {

    /**
     * Function that runs when the exit button is clicked.
     * Used to close the app.
     */
    @FXML
    private void onExitButtonClick() {
        Platform.exit();
    }

    /**
     * Function that runs when the minimize button is clicked.
     * Used to minimize the app.
     */
    @FXML
    private void onMinimizeButtonClick() throws IOException {
        Scenery.getInstance().getStage().setIconified(true);
    }
}
