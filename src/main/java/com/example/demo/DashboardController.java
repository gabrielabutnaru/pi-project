package com.example.demo;

import com.example.demo.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class DashboardController implements Initializable  {
    @FXML
    private Circle myCircle;

    @FXML
    private VBox mainBox;

    @FXML
    private HBox activeRolesButton;

    @FXML
    private HBox archivedRolesButton;

    @FXML
    private HBox newRoleButton;

    @FXML
    public void showContentActivePage() {
        FXMLLoader boxLoader = new FXMLLoader(Main.class.getResource("active-roles.fxml"));
        try {
            VBox activePage = boxLoader.load();
            VBox.setVgrow(activePage, Priority.ALWAYS);

            if (!mainBox.getChildren().isEmpty()) {
                mainBox.getChildren().remove(0);
            }
            mainBox.getChildren().addAll(activePage);
            activeRolesButton.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            activeRolesButton.getChildren().get(1).setStyle("-fx-font-weight: 700");

            archivedRolesButton.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            archivedRolesButton.getChildren().get(1).setStyle("-fx-font-weight: 400");

            newRoleButton.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            newRoleButton.getChildren().get(1).setStyle("-fx-font-weight: 400");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void showContentArchivedPage() {
        FXMLLoader boxLoader = new FXMLLoader(Main.class.getResource("archived-roles.fxml"));
        try {
            VBox activePage = boxLoader.load();
            VBox.setVgrow(activePage, Priority.ALWAYS);

            if (!mainBox.getChildren().isEmpty()) {
                mainBox.getChildren().remove(0);
            }
            mainBox.getChildren().addAll(activePage);

            activeRolesButton.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            activeRolesButton.getChildren().get(1).setStyle("-fx-font-weight: 400");

            archivedRolesButton.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            archivedRolesButton.getChildren().get(1).setStyle("-fx-font-weight: 700");

            newRoleButton.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            newRoleButton.getChildren().get(1).setStyle("-fx-font-weight: 400");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void showContentNewRolePage() {
        FXMLLoader boxLoader = new FXMLLoader(Main.class.getResource("new-role.fxml"));
        try {
            VBox activePage = boxLoader.load();
            VBox.setVgrow(activePage, Priority.ALWAYS);

            if (!mainBox.getChildren().isEmpty()) {
                mainBox.getChildren().remove(0);
            }
            mainBox.getChildren().removeAll();

            mainBox.getChildren().addAll(activePage);
            activeRolesButton.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            activeRolesButton.getChildren().get(1).setStyle("-fx-font-weight: 400");

            archivedRolesButton.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            archivedRolesButton.getChildren().get(1).setStyle("-fx-font-weight: 400");

            newRoleButton.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            newRoleButton.getChildren().get(1).setStyle("-fx-font-weight: 700");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image im = new Image("Avatar.png", false);
        myCircle.setFill(new ImagePattern(im));
        showContentActivePage();
    }
}
