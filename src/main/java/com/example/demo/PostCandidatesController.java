package com.example.demo;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class PostCandidatesController {
    @FXML
    private VBox candidatesCardLayout;
    @FXML
    private Label cardBudgetRange;
    @FXML
    private Label cardCandidates;
    @FXML
    private Label cardCity;
    @FXML
    private Label cardConfirmed;
    @FXML
    private Label cardDays;
    @FXML
    private Label cardOmitted;
    @FXML
    private Label cardTitle;
    @FXML
    private FlowPane skillsScroll;
    @FXML
    private HBox backButton;

    private Stage stage;
    public void setData(Role role) {
        cardTitle.setText(role.getTitle());
        cardCity.setText(role.getCity());
        cardBudgetRange.setText(role.getSalaryBudget());
        cardCandidates.setText(Integer.toString(role.getStatus().getCandidates()));
        cardConfirmed.setText(Integer.toString(role.getStatus().getConfirmed()));

        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime postDate = role.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        long diffInMillies = ChronoUnit.DAYS.between(postDate, currentDate);
        cardDays.setText(String.valueOf(TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)));

        cardOmitted.setText(Integer.toString(role.getStatus().getOmitted()));
        role.getSkills().forEach(s -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("no-x-chip.fxml"));
            try {
                HBox chip = fxmlLoader.load();
                NoXChipController chipController = fxmlLoader.getController();
                chipController.setData(new Chip(s));
                skillsScroll.getChildren().add(chip);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        List<Candidate> candidates = new ArrayList<>(role.getCandidates());
        try {
            for (int i = 0; i < candidates.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("candidateCard.fxml"));
                HBox cardBox = fxmlLoader.load();
                CandidateCardController cardController = fxmlLoader.getController();
                cardController.setData(candidates.get(i));
                candidatesCardLayout.getChildren().add(cardBox);
                candidatesCardLayout.getChildren().add(new Separator());
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        handleBackButton();
    }

    public void handleBackButton() {
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("dashboard.fxml"));
                BorderPane borderPane = new BorderPane();
                ToolBar toolBar = new ToolBar();
                SwitchScenes.configToolbar(toolBar, 56);
                try {
                    SwitchScenes.configBorderPane(borderPane, fxmlLoader, toolBar);
                    stage = (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);

                    Main.makeDraggable(borderPane, stage);
                    Scene dashboardScene = new Scene(borderPane, 1024, 575);
                    dashboardScene.setFill(Color.TRANSPARENT);
                    HBox logOutButton = (HBox) dashboardScene.lookup("#logOutButton");
                    //logOutButton.setOnMouseClicked(event -> switchScenes(login));

                    stage.setScene(dashboardScene);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
