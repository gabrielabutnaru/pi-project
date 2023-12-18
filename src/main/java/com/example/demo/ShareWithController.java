package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Data;
import model.Screen;
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShareWithController {
    @FXML
    private VBox userCardsLayout;

    @FXML
    private void onBackButtonClick() throws IOException {
        Scenery.getInstance().changeScene(Screen.ROLE_DETAILS);
    }

    public void redraw() {
        userCardsLayout.getChildren().clear();
        try {
            List<User> users = Data.getUsersWithoutCurrent();
            for (int i = 0; i < users.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("user-card.fxml"));
                HBox cardBox = fxmlLoader.load();
                UserCardController cardController = fxmlLoader.getController();
                cardController.setUser(users.get(i));
                cardController.redraw();
                userCardsLayout.getChildren().add(cardBox);
                if (i != users.size() - 1) {
                    userCardsLayout.getChildren().add(new Separator());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
