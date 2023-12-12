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
import model.Candidate;
import model.EStatus;
import model.Role;
import model.Screen;

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

    private Candidate candidate;


    /*
    Nu se loaduie cand trebuie! Also daca modific statusul unui candidat pe un anumit rol, atunci se modifica peste tot.
     */
    public void setData(Candidate candidate) throws ParseException {
        this.candidate = candidate;

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
    }

    @FXML
    protected void onViewCandidateButton() throws IOException, ParseException {
        Scenery.getInstance().getCandidateDetailsController().drawData(candidate);
        Scenery.getInstance().changeScene(Screen.CANDIDATE_DETAILS);
    }

}
