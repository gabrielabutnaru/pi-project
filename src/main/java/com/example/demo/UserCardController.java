package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import model.Data;
import model.Role;
import model.User;

import java.sql.SQLException;

/**
 * The controller for the user card component.
 */
public class UserCardController {

    @FXML
    private Label shareButtonLabel;

    @FXML
    private HBox shareButton;

    @FXML
    private ImageView userAvatar;

    @FXML
    private Label userName;

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Function that runs when the share button is clicked.
     * Used to (un)share the current role to the given user.
     */
    @FXML
    private void onShareButtonClick() throws SQLException {
        Role role = Data.getCurrentRole();
        if (!Data.isRoleSharedWithUser(role.getId(), user.getId())) {
            Data.shareRoleToUser(role.getId(), user.getId());
            redraw();
        } else {
            Data.unshareRoleWithUser(role.getId(), user.getId());
            redraw();
        }
    }

    /**
     * Function that updates the component state.
     */
    public void redraw() throws SQLException {
        Role role = Data.getCurrentRole();
        if (Data.isRoleSharedWithUser(role.getId(), user.getId())) {
            shareButtonLabel.setText("Unshare");
            shareButtonLabel.setStyle("-fx-text-fill: #223ca4");
            shareButton.setStyle("-fx-background-color: #D8E0FF; -fx-background-radius: 12");

        } else {
            shareButtonLabel.setText("Share");
            shareButtonLabel.setStyle("-fx-text-fill: #FFFFFF");
            shareButton.setStyle("-fx-background-color: #213BA2; -fx-background-radius: 12");
        }
        Circle clip = new Circle(20, 20, 20);
        userAvatar.setImage(new Image(user.getAvatar(), true));
        userAvatar.setClip(clip);
        userName.setText(user.getFirstName() + " " + user.getLastName());
    }
}
