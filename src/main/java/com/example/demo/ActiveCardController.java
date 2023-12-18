package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.*;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ActiveCardController {
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

    @FXML
    public void onCardButtonClick() throws IOException {
        Data.setCurrentRoleId(role.getId());
        Scenery.getInstance().changeScene(Screen.ROLE_DETAILS);
    }

    public void drawData(Role role) {
        this.role = role;

        cardOwner.setText(role.getOwner().getFirstName() + " " + role.getOwner().getLastName());
        cardTitle.setText(role.getTitle());

        cardCity.setText(role.getCity());
        cardSkills.setText(String.join(", ", role.getSkills()));
        cardSalaryBudget.setText(role.getSalaryBudget());

        int omittedCandidatesCount = (int) role.getCandidates().stream().filter(c -> c.getStatus() == EStatus.OMITTED || c.getStatus() == EStatus.FAILED).count();
        int confirmedCandidatesCount = (int) role.getCandidates().stream().filter(c -> c.getStatus() == EStatus.IN_TOUCH || c.getStatus() == EStatus.EMPLOYED).count();
        int allCandidatesCount = role.getCandidates().size();

        cardOmitted.setText(Integer.toString(omittedCandidatesCount));
        cardConfirmed.setText(Integer.toString(confirmedCandidatesCount));
        cardCandidates.setText(Integer.toString(allCandidatesCount));

        int confirmedPercent = allCandidatesCount == 0 ? 0 : confirmedCandidatesCount * 100 / allCandidatesCount;
        int omittedPercent = allCandidatesCount == 0 ? 0 : omittedCandidatesCount * 100 / allCandidatesCount;

        barConfirmed.setPrefWidth(confirmedPercent * barCandidates.getPrefWidth() / 100);
        barOmitted.setPrefWidth(omittedPercent * barCandidates.getPrefWidth() / 100);

        Date currentDate = new Date();
        Date postDate = role.getDate();
        long diffInMillies = Math.abs(postDate.getTime() - currentDate.getTime());
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        cardDate.setText(Long.toString(diffInDays));
    }
}
