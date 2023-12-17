package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.Candidate;
import model.Data;
import model.EStatus;
import model.Screen;

import java.io.IOException;
import java.sql.SQLException;

public class CandidateDetailsController {
    @FXML
    private Label candidateAge;

    @FXML
    private ImageView candidateAvatar;

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


    @FXML
    void onBackButtonClick(MouseEvent event) throws IOException, SQLException {
        Scenery.getInstance().changeScene(Screen.ROLE_DETAILS);
    }

    @FXML
    void onOmitButtonClick(MouseEvent event) throws SQLException {
        Data.setCandidateStatus(EStatus.OMITTED, Data.getCurrentCandidateId(), Data.getCurrentRoleId());
        Data.loadRoles();
        redraw();
    }

    @FXML
    void onFailButtonClick(MouseEvent event) throws SQLException {
        Data.setCandidateStatus(EStatus.FAILED, Data.getCurrentCandidateId(), Data.getCurrentRoleId());
        Data.loadRoles();
        redraw();
    }

    @FXML
    void onInTouchButtonClick(MouseEvent event) throws SQLException {
        Data.setCandidateStatus(EStatus.IN_TOUCH, Data.getCurrentCandidateId(), Data.getCurrentRoleId());
        Data.loadRoles();
        redraw();
    }

    @FXML
    void onConfirmedButtonClick(MouseEvent event) throws SQLException {
        Data.setCandidateStatus(EStatus.EMPLOYED, Data.getCurrentCandidateId(), Data.getCurrentRoleId());
        Data.loadRoles();
        redraw();
    }


    public void redraw() {
        Candidate c = Data.getCurrentCandidate() ;
        candidateAge.setText(Integer.toString(c.getAge()));
        candidateAvatar.setImage(new Image(c.getAvatar(), true));
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

