package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import model.Screen;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShareWithController implements Initializable {
    @FXML
    private VBox userCardsLayout;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void onBackButtonClick() throws IOException {
        Scenery.getInstance().changeScene(Screen.ROLE_DETAILS);
    }

    @FXML
    private void onShareButtonClick() {
        
    }
}
