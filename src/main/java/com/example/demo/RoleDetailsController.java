package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


public class RoleDetailsController {
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
    private Label userFullName;

    private Role role;

    @FXML
    private void onBackButtonClick() throws IOException {
        Scenery.getInstance().changeScene(Screen.ACTIVE_ROLES);
    }

    @FXML
    private void onArchiveButtonClick() throws IOException, SQLException {
        Data.archiveRole(role.getId());
        Data.loadRoles();
        Scenery.getInstance().changeScene(Screen.ACTIVE_ROLES);
    }


    @FXML
    private void onShareButtonClick() throws IOException {
        //Scenery.getInstance().getShareWithController().drawData();
        Scenery.getInstance().changeScene(Screen.SHARE_WITH);
    }

    public void drawData(Role role) {
        this.role = role;

        LocalDate currentDate = LocalDate.now();
        LocalDate postDate = role.getDate();
        long diffInDays = ChronoUnit.DAYS.between(postDate, currentDate);
        cardDays.setText(Long.toString(diffInDays));

        cardTitle.setText(role.getTitle());

        cardCity.setText(role.getCity());
        cardBudgetRange.setText(role.getSalaryBudget());

        int omittedCandidatesCount = (int) role.getCandidates().stream().filter(c -> c.getStatus() == EStatus.OMITTED || c.getStatus() == EStatus.FAILED).count();
        int confirmedCandidatesCount = (int) role.getCandidates().stream().filter(c -> c.getStatus() == EStatus.IN_TOUCH || c.getStatus() == EStatus.EMPLOYED).count();
        int allCandidatesCount = role.getCandidates().size();

        cardOmitted.setText(Integer.toString(omittedCandidatesCount));
        cardConfirmed.setText(Integer.toString(confirmedCandidatesCount));
        cardCandidates.setText(Integer.toString(allCandidatesCount));

        skillsScroll.getChildren().clear();
        role.getSkills().forEach(s -> {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("no-x-chip.fxml"));
            HBox chip = null;
            try {
                chip = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            NoXChipController chipController = fxmlLoader.getController();
            chipController.setData(s);
            skillsScroll.getChildren().add(chip);

        });

        candidatesCardLayout.getChildren().clear();
        role.getCandidates().forEach(c -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("candidate-card.fxml"));
            try {
                HBox cardBox = fxmlLoader.load();
                CandidateCardController cardController = fxmlLoader.getController();
                cardController.drawData(c);
                candidatesCardLayout.getChildren().add(cardBox);
                candidatesCardLayout.getChildren().add(new Separator());
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void drawUserData(User user) {
        //        myCircle.setFill(new ImagePattern(new Image("Avatar.png", false)));
        this.userFullName.setText(user.getFirstName() + " " + user.getLastName());
    }
}
