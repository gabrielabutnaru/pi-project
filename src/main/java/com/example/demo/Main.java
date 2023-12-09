package com.example.demo;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Scenery.getInstance().getStage().show();
    }
}
