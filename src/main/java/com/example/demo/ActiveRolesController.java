package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import model.Data;
import model.Role;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ActiveRolesController implements Initializable {
    @FXML
    private VBox activeCardLayout;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            List<Role> activeRoles = Data.roles.stream().filter(Role::isActive).toList();
            for (int i = 0; i < activeRoles.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("active-card.fxml"));
                VBox cardBox = fxmlLoader.load();
                ActiveCardController cardController = fxmlLoader.getController();
                cardController.setData(activeRoles.get(i));
                activeCardLayout.getChildren().add(cardBox);
                if (i != activeRoles.size() - 1) {
                    activeCardLayout.getChildren().add(new Separator());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
