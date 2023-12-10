package com.example.demo;

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
import model.Screen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

enum Section {
    ACTIVE_ROLES,
    ARCHIVED_ROLES,
    NEW_ROLE
}

public class DashboardController implements Initializable {
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
    public void onActiveRolesButtonClick() {
        loadSection(Section.ACTIVE_ROLES);
    }

    @FXML
    public void onArchivedRolesButtonClick() {
        loadSection(Section.ARCHIVED_ROLES);
    }

    @FXML
    public void onNewRoleButtonClick() {
        loadSection(Section.NEW_ROLE);
    }

    @FXML
    private void onLogOutButtonClick() throws IOException {
        Scenery.getInstance().changeScene(Screen.LOGIN);
    }

    private void loadSection(Section section) {
        String resource = "new-role.fxml";
        if (section == Section.ACTIVE_ROLES) {
            resource = "active-roles.fxml";
        } else if (section == Section.ARCHIVED_ROLES) {
            resource = "archived-roles.fxml";
        }

        FXMLLoader boxLoader = new FXMLLoader(getClass().getResource(resource));
        VBox activePage;
        try {
            activePage = boxLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        VBox.setVgrow(activePage, Priority.ALWAYS);

        mainBox.getChildren().clear();
        mainBox.getChildren().addAll(activePage);

        activeRolesButton.setNodeOrientation(section == Section.ACTIVE_ROLES ? NodeOrientation.RIGHT_TO_LEFT : NodeOrientation.LEFT_TO_RIGHT);
        activeRolesButton.getChildren().get(1).setStyle("-fx-font-weight: " + (section == Section.ACTIVE_ROLES ? "700" : "400"));

        archivedRolesButton.setNodeOrientation(section == Section.ARCHIVED_ROLES ? NodeOrientation.RIGHT_TO_LEFT : NodeOrientation.LEFT_TO_RIGHT);
        archivedRolesButton.getChildren().get(1).setStyle("-fx-font-weight: " + (section == Section.ARCHIVED_ROLES ? "700" : "400"));

        newRoleButton.setNodeOrientation(section == Section.NEW_ROLE ? NodeOrientation.RIGHT_TO_LEFT : NodeOrientation.LEFT_TO_RIGHT);
        newRoleButton.getChildren().get(1).setStyle("-fx-font-weight: " + (section == Section.NEW_ROLE ? "700" : "400"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image im = new Image("Avatar.png", false);
        myCircle.setFill(new ImagePattern(im));
        onActiveRolesButtonClick();
    }
}
