package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.Data;
import model.Role;
import model.Screen;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;

public class ArchivedRolesController {
    @FXML
    private VBox archivedCardLayout;

    @FXML
    private ImageView userAvatar;

    @FXML
    private Label userFullName;


    @FXML
    public void onActiveRolesButtonClick() throws IOException, SQLException {
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
