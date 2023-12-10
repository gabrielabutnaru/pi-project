package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.Screen;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button logInButton;

    @FXML
    private void onLogInButtonClick() throws IOException {
        Scenery.getInstance().changeScene(Screen.DASHBOARD);
    }
}
