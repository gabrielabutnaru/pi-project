package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.Candidate;
import model.EStatus;
import model.Screen;

import java.io.IOException;
import java.text.ParseException;

public class CandidateDetailsController {
    @FXML
    private Label candidateAge;

    @FXML
    private Rectangle candidateAvatar;

    @FXML
    private Label candidateCity;

    @FXML
    private VBox candidateJobs;

    @FXML
    private Label candidateMail;

    @FXML
    private Label candidateName;

    @FXML
    private Label candidatePhone;

    @FXML
    private HBox candidateSkills;

    @FXML
    private Label candidateStatus;

    private Candidate candidate;

    @FXML
    void onBackButtonClick(MouseEvent event) throws IOException {
        Scenery.getInstance().changeScene(Screen.ROLE_DETAILS);
    }

    @FXML
    void onOmitButtonClick(MouseEvent event) {
        candidate.setStatus(EStatus.OMITTED);
    }

    @FXML
    void onFailButtonClick(MouseEvent event) {
        candidate.setStatus(EStatus.FAILED);
    }

    @FXML
    void onInTouchButtonClick(MouseEvent event) {
        candidate.setStatus(EStatus.IN_TOUCH);
    }

    @FXML
    void onConfirmedButtonClick(MouseEvent event) {
        candidate.setStatus(EStatus.EMPLOYED);
    }


    public void drawData(Candidate c) throws ParseException {
        this.candidate = c;
        candidateAge.setText(Integer.toString(c.getAge()));
        candidateAvatar.setFill(new ImagePattern(new Image(c.getAvatar(), false)));
        candidateCity.setText(c.getCity());
        candidateMail.setText(c.getMail());
        candidatePhone.setText(c.getPhone());

        if (c.getStatus() == EStatus.DEFAULT) {
            candidateStatus.setText("-");
        } else if (c.getStatus() == EStatus.OMITTED) {
            candidateStatus.setText("OMITTED");
        } else if (c.getStatus() == EStatus.FAILED) {
            candidateStatus.setText("FAILED");
        } else if (c.getStatus() == EStatus.IN_TOUCH) {
            candidateStatus.setText("IN TOUCH");
        } else if (c.getStatus() == EStatus.EMPLOYED) {
            candidateStatus.setText("EMPLOYED");
        }

        candidateJobs.getChildren().clear();
        c.getJobs().forEach(job -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("no-x-chip.fxml"));
            try {
                HBox chip = fxmlLoader.load();
                NoXChipController chipController = fxmlLoader.getController();
                chipController.setData(job.getCompany());

                Label jobTitle = new Label(job.getTitle());
                Label connector = new Label(" at ");
                jobTitle.setStyle("-fx-font-family: 'Roboto Flex'");
                jobTitle.setTextFill(Color.rgb(119, 119, 119));
                connector.setStyle("-fx-font-family: 'Roboto Flex'");
                connector.setTextFill(Color.rgb(119, 119, 119));

                HBox jobComponent = new HBox(jobTitle, connector, chip);
                jobComponent.setAlignment(Pos.CENTER_LEFT);

                candidateJobs.getChildren().add(jobComponent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        candidateName.setText(c.getFirstName() + " " + c.getLastName());

        candidateSkills.getChildren().clear();
        c.getSkills().forEach(skill -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("no-x-chip.fxml"));
            try {
                HBox chip = fxmlLoader.load();
                NoXChipController chipController = fxmlLoader.getController();
                chipController.setData(skill);
                candidateSkills.getChildren().add(chip);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

