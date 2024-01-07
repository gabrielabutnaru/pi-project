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
import java.sql.SQLException;
import java.util.List;

/**
 * The controller for the active roles screen.
 */
public class ActiveRolesController {
    @FXML
    private ImageView userAvatar;

    public VBox getActiveCardLayout() {
        return activeCardLayout;
    }

    @FXML
    private VBox activeCardLayout;

    @FXML
    private Label userFullName;

    /**
     * Function that runs when the archived roles button is clicked.
     * Used to navigate to the archived roles screen.
     */
    @FXML
    public void onArchivedRolesButtonClick() throws IOException, SQLException {
        Data.loadRoles();
        Scenery.getInstance().changeScene(Screen.ARCHIVED_ROLES);
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
        activeCardLayout.getChildren().clear();
        Circle clip = new Circle(32, 32, 32);
        userAvatar.setImage(new Image(Data.getCurrentUser().getAvatar(), true));
        userAvatar.setClip(clip);
        this.userFullName.setText(Data.getCurrentUser().getFirstName() + " " + Data.getCurrentUser().getLastName());
        try {
            List<Role> activeRoles = Data.roles.stream().filter(Role::isActive).toList();
            if (activeRoles.isEmpty()) {
                if (Data.roles.isEmpty()) {
                    activeCardLayout.getChildren().add(new Label("There are no roles at all!"));
                } else {
                    activeCardLayout.getChildren().add(new Label("There are only archived roles!"));
                }
            }
            for (int i = 0; i < activeRoles.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("active-card.fxml"));
                VBox cardBox = fxmlLoader.load();
                ActiveCardController cardController = fxmlLoader.getController();
                cardController.drawData(activeRoles.get(i));
                activeCardLayout.getChildren().add(cardBox);
                if (i != activeRoles.size() - 1) {
                    activeCardLayout.getChildren().add(new Separator());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
