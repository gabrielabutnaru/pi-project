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
import model.Screen;

import java.io.IOException;

public class Scenery {
    private static Scenery instance;

    private static double xOffset;
    private static double yOffset;

    private final Stage stage;

    private final Scene login;
    private final LoginController loginController;

    private final Scene dashboard;
    private final DashboardController dashboardController;

    private final Scene roleDetails;
    private final RoleDetailsController roleDetailsController;

    private final Scene candidateDetails;
    private final CandidateDetailsController candidateDetailsController;

    private Scenery() throws IOException {
        this.stage = new Stage();

        Pair<Scene, FXMLLoader> loginPair = createScene("login-view.fxml");
        this.login = loginPair.getKey();
        this.loginController = loginPair.getValue().getController();

        Pair<Scene, FXMLLoader> dashboardPair = createScene("dashboard.fxml");
        this.dashboard = dashboardPair.getKey();
        this.dashboardController = dashboardPair.getValue().getController();

        Pair<Scene, FXMLLoader> roleDetailsPair = createScene("role-details.fxml");
        this.roleDetails = roleDetailsPair.getKey();
        this.roleDetailsController = roleDetailsPair.getValue().getController();

        Pair<Scene, FXMLLoader> candidateDetailsPair = createScene("candidate-details.fxml");
        this.candidateDetails = candidateDetailsPair.getKey();
        this.candidateDetailsController = candidateDetailsPair.getValue().getController();

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
            this.stage.setScene(this.login);
        } else if (screen == Screen.DASHBOARD) {
            this.stage.setScene(this.dashboard);
        } else if (screen == Screen.ROLE_DETAILS) {
            this.stage.setScene(this.roleDetails);
        } else if (screen == Screen.CANDIDATE_DETAILS) {
            this.stage.setScene(this.candidateDetails);
        }
    }

    public RoleDetailsController getRoleDetailsController() {
        return this.roleDetailsController;
    }
    public CandidateDetailsController getCandidateDetailsController() {
        return this.candidateDetailsController;
    }

}
