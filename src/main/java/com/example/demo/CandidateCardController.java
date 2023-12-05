package com.example.demo;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.text.ParseException;

public class CandidateCardController {
    @FXML
    private Circle candidateAvatar;

    @FXML
    private Label candidateCompany;

    @FXML
    private HBox candidateContainer;

    @FXML
    private Label candidateJob;

    @FXML
    private Label candidateName;

    @FXML
    private HBox viewCandidateButton;

    private double xOffset;
    private double yOffset;

    public void setData(Candidate candidate) throws ParseException {
        candidateName.setText(candidate.getFirstName() + " " + candidate.getLastName());
        candidateJob.setText(candidate.getJobs().get(0).getTitle());
        candidateCompany.setText(candidate.getJobs().get(0).getCompany());
        if (candidate.getStatus() == EStatus.DEFAULT) {
            candidateContainer.setStyle("-fx-background-color: transparent");
        } else if (candidate.getStatus() == EStatus.OMITTED) {
            candidateContainer.setStyle("-fx-background-color: #FFE1C6");
        } else if (candidate.getStatus() == EStatus.FAILED) {
            candidateContainer.setStyle("-fx-background-color: #FFB7B7");
        } else if (candidate.getStatus() == EStatus.IN_TOUCH) {
            candidateContainer.setStyle("-fx-background-color: #DEE4FF");
        } else if (candidate.getStatus() == EStatus.EMPLOYED) {
            candidateContainer.setStyle("-fx-background-color: #DBFBE2");
        }
        candidateAvatar.setFill(new ImagePattern(new Image(candidate.getAvatar(), false)));
        openCandidate(candidate);
    }

    public void openCandidate(Candidate candidate) {
        viewCandidateButton.setOnMouseClicked(mouseEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("candidateInfo.fxml"));
            Stage candidateStage = new Stage();
            try {
                VBox candidateInfoCard = fxmlLoader.load();
                CandidateInfoController candidateInfoController = fxmlLoader.getController();
                candidateInfoController.setData(candidate);
                Scene candidateScene = new Scene(candidateInfoCard);
                candidateScene.setFill(Color.TRANSPARENT);
                candidateStage.initStyle(StageStyle.UNDECORATED);
                candidateStage.initStyle(StageStyle.TRANSPARENT);
                makeDraggable(candidateScene, candidateStage);
                candidateStage.setScene(candidateScene);
                candidateStage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void makeDraggable(Scene scene, Stage stage) {
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }

}
