package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Candidate;
import model.Data;
import model.EStatus;
import model.Screen;

import java.io.IOException;
import java.sql.SQLException;

/**
 * The controller for the candidate details screen.
 */
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

    public Label getCandidateStatus() {
        return candidateStatus;
    }

    @FXML
    private Label candidateStatus;


    /**
     * Function that runs when the back button is clicked.
     * Used to navigate back to the role details screen.
     */
    @FXML
    void onBackButtonClick() throws IOException {
        Scenery.getInstance().changeScene(Screen.ROLE_DETAILS);
    }

    /**
     * Function that runs when the omit button is clicked.
     * Used to set the status of the candidate to `omitted`.
     */
    @FXML
    void onOmitButtonClick() throws SQLException {
        Data.setCandidateStatus(EStatus.OMITTED, Data.getCurrentCandidateId(), Data.getCurrentRoleId());
        Data.loadRoles();
        redraw();
    }

    /**
     * Function that runs when the fail button is clicked.
     * Used to set the status of the candidate to failed.
     */
    @FXML
    void onFailButtonClick() throws SQLException {
        Data.setCandidateStatus(EStatus.FAILED, Data.getCurrentCandidateId(), Data.getCurrentRoleId());
        Data.loadRoles();
        redraw();
    }

    /**
     * Function that runs when the `in touch` button is clicked.
     * Used to set the status of the candidate to `in touch`.
     */
    @FXML
    void onInTouchButtonClick() throws SQLException {
        Data.setCandidateStatus(EStatus.IN_TOUCH, Data.getCurrentCandidateId(), Data.getCurrentRoleId());
        Data.loadRoles();
        redraw();
    }
    /**
     * Function that runs when the confirm button is clicked.
     * Used to set the status of the candidate to confirmed.
     */
    @FXML
    void onConfirmedButtonClick() throws SQLException {
        Data.setCandidateStatus(EStatus.EMPLOYED, Data.getCurrentCandidateId(), Data.getCurrentRoleId());
        Data.loadRoles();
        redraw();
    }

    /**
     * Function that updates the screen state.
     */
    public void redraw() {
        Candidate c = Data.getCurrentCandidate();
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

