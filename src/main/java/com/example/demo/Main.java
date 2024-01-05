package com.example.demo;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class of the app.
 * @author Gabriela Butnaru
 */
public class Main extends Application {
    /**
     * Function that creates a scenery instance and displays the primary stage.
     * @param stage primary stage
     */
    @Override
    public void start(Stage stage) throws IOException {
        Scenery.getInstance().getStage().show();
    }
}
