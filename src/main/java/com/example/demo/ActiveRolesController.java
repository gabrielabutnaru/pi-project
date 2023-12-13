package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.Data;
import model.Role;
import model.Screen;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ActiveRolesController implements Initializable {
    @FXML
    private Circle myCircle;

    @FXML
    private VBox activeCardLayout;

    @FXML
    private Label userFullName;

    @FXML
    public void onArchivedRolesButtonClick() throws IOException {
        Scenery.getInstance().changeScene(Screen.ARCHIVED_ROLES);
    }

    @FXML
    public void onNewRoleButtonClick() throws IOException {
        Scenery.getInstance().changeScene(Screen.NEW_ROLE);
    }

    @FXML
    private void onLogOutButtonClick() throws IOException {
        Scenery.getInstance().changeScene(Screen.LOGIN);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myCircle.setFill(new ImagePattern(new Image("Avatar.png", false)));
        try {
            List<Role> activeRoles = Data.roles.stream().filter(Role::isActive).toList();
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
        }
    }

    public void drawUserData(User user) {
        //        myCircle.setFill(new ImagePattern(new Image("Avatar.png", false)));
        this.userFullName.setText(user.getFirstName() + " " + user.getLastName());
    }
}
