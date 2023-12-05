package com.example.demo;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ArchivedRolesController implements Initializable {
    @FXML
    private VBox archivedCardLayout;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        archivedCardLayout.setOnMousePressed(Event::consume);
        List<Role> roles = new ArrayList<>(ActiveRolesController.getPosts());
        try {
            for (int i = 0; i < roles.size(); i++) {
                if (!roles.get(i).getActive()) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("archived-card.fxml"));
                    VBox cardBox = fxmlLoader.load();
                    ArchivedCardController cardController = fxmlLoader.getController();
                    cardController.setData(roles.get(i));
                    archivedCardLayout.getChildren().add(cardBox);
                    archivedCardLayout.getChildren().add(new Separator());
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
