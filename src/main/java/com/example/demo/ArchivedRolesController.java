package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import model.Data;
import model.Role;
import model.Screen;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * The controller for the archived roles screen.
 */
public class ArchivedRolesController {
    @FXML
    private VBox archivedCardLayout;

    @FXML
    private ImageView userAvatar;

    @FXML
    private Label userFullName;

    /**
     * Function that runs when the active roles button is clicked.
     * Used to navigate to the active roles screen.
     */
    @FXML
    public void onActiveRolesButtonClick() throws IOException {
        Scenery.getInstance().changeScene(Screen.ACTIVE_ROLES);
    }
    /**
     * Function that runs when the new role button is clicked.
     * Used to navigate to the new role screen.
     */
    @FXML
    public void onNewRoleButtonClick() throws IOException {
        Scenery.getInstance().changeScene(Screen.NEW_ROLE);
    }
    /**
     * Function that runs when the logout button is clicked.
     * Used to log out the user.
     */
    @FXML
    private void onLogOutButtonClick() throws IOException {
        Scenery.getInstance().changeScene(Screen.LOGIN);
    }

    /**
     * Function that updates the screen state.
     */
    public void redraw() {
        archivedCardLayout.getChildren().clear();
        Circle clip = new Circle(32, 32, 32);
        userAvatar.setImage(new Image(Data.getCurrentUser().getAvatar(), true));
        userAvatar.setClip(clip);
        this.userFullName.setText(Data.getCurrentUser().getFirstName() + " " + Data.getCurrentUser().getLastName());
        try {
            List<Role> archivedRoles = Data.roles.stream().filter(r -> !r.isActive()).toList();
            for (int i = 0; i < archivedRoles.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("archived-card.fxml"));
                VBox cardBox = fxmlLoader.load();
                ArchivedCardController cardController = fxmlLoader.getController();
                cardController.setData(archivedRoles.get(i));
                archivedCardLayout.getChildren().add(cardBox);
                if (i != archivedRoles.size() - 1) {
                    archivedCardLayout.getChildren().add(new Separator());
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
