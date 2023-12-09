package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.Screen;

public class LoginController {
    @FXML
    private Button logInButton;

    @FXML
    private void onLogInButtonClick() {
        Scenery.getInstance().changeScene(Screen.DASHBOARD);
    }
}
