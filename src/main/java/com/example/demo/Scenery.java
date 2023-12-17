package com.example.demo;

import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import model.Data;
import model.Screen;

import java.io.IOException;
import java.sql.SQLException;

public class Scenery {
    private static Scenery instance;

    private static double xOffset;
    private static double yOffset;

    private final Stage stage;

    private final Scene login;
    private final LoginController loginController;

    private final Scene activeRoles;
    private final ActiveRolesController activeRolesController;

    private final Scene archivedRoles;
    private final ArchivedRolesController archivedRolesController;

    private final Scene newRole;
    private final NewRoleController newRoleController;

    private final Scene roleDetails;
    private final RoleDetailsController roleDetailsController;

    private final Scene candidateDetails;
    private final CandidateDetailsController candidateDetailsController;

    private final Scene shareWith;
    private final ShareWithController shareWithController;

    private Scenery() throws IOException {
        this.stage = new Stage();

        Pair<Scene, FXMLLoader> loginPair = createScene("login-view.fxml");
        this.login = loginPair.getKey();
        this.loginController = loginPair.getValue().getController();

        Pair<Scene, FXMLLoader> activeRolesPair = createScene("active-roles.fxml");
        this.activeRoles = activeRolesPair.getKey();
        this.activeRolesController = activeRolesPair.getValue().getController();

        Pair<Scene, FXMLLoader> archivedRolesPair = createScene("archived-roles.fxml");
        this.archivedRoles = archivedRolesPair.getKey();
        this.archivedRolesController = archivedRolesPair.getValue().getController();

        Pair<Scene, FXMLLoader> newRolePair = createScene("new-role.fxml");
        this.newRole = newRolePair.getKey();
        this.newRoleController = newRolePair.getValue().getController();

        Pair<Scene, FXMLLoader> roleDetailsPair = createScene("role-details.fxml");
        this.roleDetails = roleDetailsPair.getKey();
        this.roleDetailsController = roleDetailsPair.getValue().getController();

        Pair<Scene, FXMLLoader> candidateDetailsPair = createScene("candidate-details.fxml");
        this.candidateDetails = candidateDetailsPair.getKey();
        this.candidateDetailsController = candidateDetailsPair.getValue().getController();

        Pair<Scene, FXMLLoader> shareWithPair = createScene("share-with.fxml");
        this.shareWith = shareWithPair.getKey();
        this.shareWithController = shareWithPair.getValue().getController();

        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("HR Desktop App");
        stage.setScene(this.login);
    }

    private Pair<Scene, FXMLLoader> createScene(String resourceUrl) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resourceUrl));
        ToolBar toolBar = createToolbar();
        BorderPane borderPane = createBorderPane(fxmlLoader, toolBar);
        borderPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        borderPane.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        Scene scene = new Scene(borderPane, 1024, 575);
        scene.setFill(Color.TRANSPARENT);
        return new Pair<>(scene, fxmlLoader);
    }

    private ToolBar createToolbar() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("toolbar.fxml"));
        return fxmlLoader.load();
    }

    private BorderPane createBorderPane(FXMLLoader fxmlLoader, ToolBar toolBar) {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(toolBar);
        try {
            borderPane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        borderPane.setStyle("-fx-background-color: #B4C0F0; -fx-background-radius: 20;");
        borderPane.getStylesheets().clear();
        return borderPane;
    }

    public static Scenery getInstance() throws IOException {
        if (instance == null) instance = new Scenery();
        return instance;
    }

    public Stage getStage() {
        return this.stage;
    }

    public void changeScene(Screen screen) {
        if (screen == Screen.LOGIN) {
            this.loginController.redraw();
            this.stage.setScene(this.login);
        } else if (screen == Screen.ACTIVE_ROLES) {
            try {
                Data.loadRoles();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            this.activeRolesController.redraw();
            this.stage.setScene(this.activeRoles);
        } else if (screen == Screen.ARCHIVED_ROLES) {
            try {
                Data.loadRoles();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            this.archivedRolesController.redraw();
            this.stage.setScene(this.archivedRoles);
        } else if (screen == Screen.NEW_ROLE) {
            this.newRoleController.redraw();
            this.stage.setScene(this.newRole);
        } else if (screen == Screen.ROLE_DETAILS) {
            try {
                Data.loadRoles();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            this.roleDetailsController.redraw();
            this.stage.setScene(this.roleDetails);
        } else if (screen == Screen.CANDIDATE_DETAILS) {
            this.candidateDetailsController.redraw();
            this.stage.setScene(this.candidateDetails);
        } else if (screen == Screen.SHARE_WITH) {
            //this.shareWithController.redraw();
            this.stage.setScene(this.shareWith);
        }
    }

    public LoginController getLoginController() {
        return this.loginController;
    }
    public ActiveRolesController getActiveRolesController() {
        return this.activeRolesController;
    }
    public ArchivedRolesController getArchivedRolesController() {
        return this.archivedRolesController;
    }
    public NewRoleController getNewRoleController() {
        return this.newRoleController;
    }

    public RoleDetailsController getRoleDetailsController() {
        return this.roleDetailsController;
    }
    public CandidateDetailsController getCandidateDetailsController() {
        return this.candidateDetailsController;
    }
}
