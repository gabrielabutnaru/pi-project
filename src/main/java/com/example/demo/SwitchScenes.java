package com.example.demo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SwitchScenes extends Application {
    private Stage stage;
    private Scene login;
    private Scene dashboard;

    @Override
    public void start(Stage stage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("RobotoFlex-Regular.ttf"), 14);
        this.stage = stage;
        this.login = createLoginScene();
        this.dashboard = createDashBoardScene();
        configStage(this.stage, this.login);
        this.stage.show();
    }

    private Scene createLoginScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        BorderPane borderPane = new BorderPane();
        ToolBar toolBar = new ToolBar();
        configToolbar(toolBar, 56);
        configBorderPane(borderPane, fxmlLoader, toolBar);
        Main.makeDraggable(borderPane, this.stage);
        this.login = new Scene(borderPane, 1024, 575);
        this.login.setFill(Color.TRANSPARENT);
        Button newButton = (Button) this.login.lookup("#logInButton");
        newButton.setOnAction(event -> switchScenes(dashboard));
        return this.login;
    }

    private Scene createDashBoardScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("dashboard.fxml"));
        BorderPane borderPane = new BorderPane();
        ToolBar toolBar = new ToolBar();
        configToolbar(toolBar, 56);
        configBorderPane(borderPane, fxmlLoader,toolBar);
        Main.makeDraggable(borderPane, this.stage);
        this.dashboard = new Scene(borderPane, 1024, 575);
        this.dashboard.setFill(Color.TRANSPARENT);
        HBox logOutButton = (HBox) this.dashboard.lookup("#logOutButton");
        logOutButton.setOnMouseClicked(event -> switchScenes(login));
        return this.dashboard;
    }

    public static void configToolbar(ToolBar toolBar, int height) {
        toolBar.setPrefHeight(height);
        toolBar.setMinHeight(height);
        toolBar.setMaxHeight(height);
        toolBar.getItems().add(new WindowButtons());
        toolBar.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        toolBar.setStyle("-fx-background-color: #B4C0F0; -fx-border-radius: 20; -fx-background-radius: 20; -fx-padding: 16");
    }

    public static void configBorderPane(BorderPane borderPane, FXMLLoader fxmlLoader, ToolBar toolBar) throws IOException {
        borderPane.setTop(toolBar);
        borderPane.setCenter(fxmlLoader.load());
        borderPane.setStyle("-fx-background-color: #B4C0F0; -fx-background-radius: 20;");
        borderPane.getStylesheets().clear();
    }
    public void configStage(Stage stage, Scene scene) {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("HR Desktop App");
        stage.setScene(scene);
    }

    public void switchScenes(Scene scene) {
        stage.setScene(scene);
    }
}
