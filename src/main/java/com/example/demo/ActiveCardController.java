package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class ActiveCardController {
    @FXML
    private VBox cardButton;
    @FXML
    private Label cardCity;
    @FXML
    private Label cardDate;
    @FXML
    private Label cardOwner;
    @FXML
    private Label cardSalaryBudget;
    @FXML
    private Label cardSkills;
    @FXML
    private Label cardTitle;
    @FXML
    private Label cardOmitted;
    @FXML
    private Label cardConfirmed;
    @FXML
    private Label cardCandidates;
    @FXML
    private HBox barCandidates;
    @FXML
    private HBox barConfirmed;
    @FXML
    private HBox barOmitted;
    private Role role;
    private Stage stage;

    public void setData(Role role) throws ParseException {
        cardTitle.setText(role.getTitle());
        cardCity.setText(role.getCity());

        LocalDate currentDate = LocalDate.now();
        LocalDate postDate = role.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long diffInDays = ChronoUnit.DAYS.between(postDate, currentDate);
        cardDate.setText(Long.toString(diffInDays));

        cardOwner.setText(role.getOwner().getFirstName() + " " + role.getOwner().getLastName());
        cardSalaryBudget.setText(role.getSalaryBudget());
        cardSkills.setText(String.join(", ", role.getSkills()));
        cardOmitted.setText(Integer.toString(role.getStatus().getOmitted()));
        cardConfirmed.setText(Integer.toString(role.getStatus().getConfirmed()));
        cardCandidates.setText(Integer.toString(role.getStatus().getCandidates()));
        int confirmedPercent = role.getStatus().getConfirmed() * 100 / role.getStatus().getCandidates();
        int omittedPercent = role.getStatus().getOmitted() * 100 / role.getStatus().getCandidates();
        barConfirmed.setPrefWidth(confirmedPercent * barCandidates.getPrefWidth() / 100);
        barOmitted.setPrefWidth(omittedPercent * barCandidates.getPrefWidth() / 100);
        this.role = role;
        openPost(this.role);
    }

    public void openPost(Role role) {
        cardButton.setOnMouseClicked(mouseEvent -> {
           FXMLLoader fxmlLoader = new FXMLLoader();
           fxmlLoader.setLocation(getClass().getResource("post-candidates.fxml"));
            BorderPane borderPane = new BorderPane();
            ToolBar toolBar = new ToolBar();
            SwitchScenes.configToolbar(toolBar, 56);
            try {
                SwitchScenes.configBorderPane(borderPane, fxmlLoader, toolBar);
                stage = (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);

                Main.makeDraggable(borderPane, stage);

                PostCandidatesController postCandidatesController = fxmlLoader.getController();
                postCandidatesController.setData(role);

                Scene postCandidatesScene = new Scene(borderPane, 1024, 575);
                postCandidatesScene.setFill(Color.TRANSPARENT);

                stage.setScene(postCandidatesScene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
