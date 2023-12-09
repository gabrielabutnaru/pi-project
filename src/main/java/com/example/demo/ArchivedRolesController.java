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
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;

public class ArchivedRolesController implements Initializable {
    @FXML
    private VBox archivedCardLayout;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
