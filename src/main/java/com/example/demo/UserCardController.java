package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.User;

public class UserCardController {
    @FXML
    private CheckBox checkbox;

    @FXML
    private Circle userAvatar;

    @FXML
    private Label userName;

    public void drawData(User user) {
        userAvatar.setFill(new ImagePattern(new Image(user.getAvatar(), false)));
        userName.setText(user.getFirstName() + " " + user.getLastName());
    }
}
