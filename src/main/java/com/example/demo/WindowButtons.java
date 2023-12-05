package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class WindowButtons extends HBox {

    /**
     * Maybe could've used an interface?
     */
    private final Button closeBtn;
    private final Button minBtn;

    public WindowButtons() {
        this.closeBtn = new Button();
        this.minBtn = new Button();
        closeBtn.setStyle("-fx-pref-width: 24; -fx-pref-height: 24; -fx-background-color: #FFFFFF; -fx-shape: 'M256 48a208 208 0 1 1 0 416 208 208 0 1 1 0-416zm0 464A256 256 0 1 0 256 0a256 256 0 1 0 0 512zM175 175c-9.4 9.4-9.4 24.6 0 33.9l47 47-47 47c-9.4 9.4-9.4 24.6 0 33.9s24.6 9.4 33.9 0l47-47 47 47c9.4 9.4 24.6 9.4 33.9 0s9.4-24.6 0-33.9l-47-47 47-47c9.4-9.4 9.4-24.6 0-33.9s-24.6-9.4-33.9 0l-47 47-47-47c-9.4-9.4-24.6-9.4-33.9 0z'");
        minBtn.setStyle("-fx-pref-width: 24; -fx-pref-height: 24; -fx-background-color: #FFFFFF; -fx-shape: 'M256 512A256 256 0 1 0 256 0a256 256 0 1 0 0 512zM184 232H328c13.3 0 24 10.7 24 24s-10.7 24-24 24H184c-13.3 0-24-10.7-24-24s10.7-24 24-24z'");
        closeBtn.setCursor(Cursor.HAND);
        minBtn.setCursor(Cursor.HAND);

        HBox buttonsContainer = new HBox();
        buttonsContainer.getChildren().add(closeBtn);
        buttonsContainer.getChildren().add(minBtn);
        buttonsContainer.setSpacing(8);
        this.getChildren().add(buttonsContainer);

        closeBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });

        minBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) minBtn.getScene().getWindow();
                stage.setIconified(true);
            }
        });
    }
}