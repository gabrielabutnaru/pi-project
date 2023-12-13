package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Data;
import model.Screen;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void onLogInButtonClick() throws IOException, SQLException {
        if (Data.isUserValid(usernameField.getText(), passwordField.getText())) {
            Scenery.getInstance().changeScene(Screen.ACTIVE_ROLES);
            usernameField.setText("");
            passwordField.setText("");
        }
    }
}
