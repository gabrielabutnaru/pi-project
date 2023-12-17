package com.example.demo;

import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

public class CandidateCardController {
    @FXML
    private ImageView candidateAvatar;

    @FXML
    private Label candidateCompany;

    @FXML
    private HBox candidateContainer;

    @FXML
    private Label candidateJob;

    @FXML
    private Label candidateName;

    private Candidate candidate;

    public void drawData(Candidate candidate) throws ParseException {
        this.candidate = candidate;

        Circle clip = new Circle(26, 26, 26);
        candidateAvatar.setImage(new Image(candidate.getAvatar(), true));
        candidateAvatar.setClip(clip);

        candidateName.setText(candidate.getFirstName() + " " + candidate.getLastName());
        if (!candidate.getJobs().isEmpty()) {
            candidateJob.setText(candidate.getJobs().get(0).getTitle());
            candidateCompany.setText(candidate.getJobs().get(0).getCompany());
        }

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
    }

    @FXML
    protected void onViewCandidateButton() throws IOException {
        Data.setCurrentCandidateId(candidate.getId());
        Scenery.getInstance().changeScene(Screen.CANDIDATE_DETAILS);
    }

}
