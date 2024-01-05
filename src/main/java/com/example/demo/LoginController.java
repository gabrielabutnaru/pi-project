package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Data;
import model.Screen;

import java.io.IOException;
import java.sql.SQLException;

/**
 * The controller for the log in screen.
 */
public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessageLabel;

    /**
     * Function that runs when the login button is clicked.
     * Used to log in. When the data introduced does not correspond to an account, an error message is displayed.
     */
    @FXML
    private void onLogInButtonClick() throws IOException, SQLException {
        if (Data.isSuccessfullyLoggedIn(usernameField.getText(), passwordField.getText())) {
            Scenery.getInstance().changeScene(Screen.ACTIVE_ROLES);
        } else {
            this.errorMessageLabel.setText("Incorrect username and password!");
        }
    }

    /**
     * Function that updates the screen state.
     */
    public void redraw() {
        this.errorMessageLabel.setText("");
        this.usernameField.setText("");
        this.passwordField.setText("");
    }
}
