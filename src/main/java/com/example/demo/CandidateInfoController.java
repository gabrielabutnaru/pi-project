package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class CandidateInfoController {
    @FXML
    private Label candidateAge;

    @FXML
    private Circle candidateAvatar;

    @FXML
    private Label candidateCity;

    @FXML
    private VBox candidateJobs;

    @FXML
    private Label candidateName;

    @FXML
    private HBox candidateSkills;

    @FXML
    private Button closeButton;

    @FXML
    private ImageView confirmedButton;

    @FXML
    private ImageView inTouchButton;

    @FXML
    private ImageView omitButton;

    public void setData(Candidate candidate) throws ParseException {
        Font.loadFont(getClass().getResourceAsStream("RobotoFlex-Regular.ttf"), 14);
        candidateAge.setText(Integer.toString(candidate.getAge()));
        candidateAvatar.setFill(new ImagePattern(new Image(candidate.getAvatar(), false)));
        candidateCity.setText(candidate.getCity());
        candidate.getJobs().forEach(job -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("no-x-chip.fxml"));
            try {
                HBox chip = fxmlLoader.load();
                NoXChipController chipController = fxmlLoader.getController();
                chipController.setData(new Chip(job.getCompany()));

                Label jobTitle = new Label(job.getTitle());
                Label connector = new Label(" at ");
                jobTitle.setStyle("-fx-font-family: 'Roboto Flex'");
                jobTitle.setTextFill(Color.rgb(119,119,119));
                connector.setStyle("-fx-font-family: 'Roboto Flex'");
                connector.setTextFill(Color.rgb(119,119,119));

                HBox jobComponent = new HBox(jobTitle, connector, chip);
                jobComponent.setAlignment(Pos.CENTER);

                candidateJobs.getChildren().add(jobComponent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        candidateName.setText(candidate.getFirstName() + " " + candidate.getLastName());
        candidate.getSkills().forEach(skill -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("no-x-chip.fxml"));
            try {
                HBox chip = fxmlLoader.load();
                NoXChipController chipController = fxmlLoader.getController();
                chipController.setData(new Chip(skill));
                candidateSkills.getChildren().add(chip);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        configButtons(candidate);
    }

    public void configButtons(Candidate candidate) {
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) closeButton.getScene().getWindow();
                stage.close();
            }
        });
        omitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                candidate.setStatus(EStatus.OMITTED);
            }
        });
        confirmedButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                candidate.setStatus(EStatus.EMPLOYED);
            }
        });
        inTouchButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                candidate.setStatus(EStatus.IN_TOUCH);
            }
        });
    }


}
