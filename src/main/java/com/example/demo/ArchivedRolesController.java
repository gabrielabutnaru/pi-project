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
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;

public class ArchivedRolesController implements Initializable {
    @FXML
    private VBox archivedCardLayout;

    @FXML
    private Circle myCircle;

    @FXML
    private Label userFullName;


    @FXML
    public void onActiveRolesButtonClick() throws IOException {
        Scenery.getInstance().changeScene(Screen.ACTIVE_ROLES);
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

    public void drawUserData(User user) {
        //        myCircle.setFill(new ImagePattern(new Image("Avatar.png", false)));
        this.userFullName.setText(user.getFirstName() + " " + user.getLastName());
    }
}
