package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class ArchivedCardController {
    @FXML
    private Label archivedCity;

    @FXML
    private Label archivedDate;

    @FXML
    private Label archivedOwner;

    @FXML
    private Label archivedSalaryBudget;

    @FXML
    private Label archivedSkills;

    @FXML
    private Label archivedTitle;

    @FXML
    private Label archivedOmitted;

    @FXML
    private Label archivedConfirmed;

    @FXML
    private Label archivedCandidates;

    @FXML
    private HBox archivedBarCandidates;

    @FXML
    private HBox archivedBarConfirmed;

    @FXML
    private HBox archivedBarOmitted;

    public void setData (Role role) throws ParseException {
        archivedTitle.setText(role.getTitle());
        archivedCity.setText(role.getCity());

        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime postDate = role.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        long diffInMillies = ChronoUnit.DAYS.between(postDate, currentDate);
        archivedDate.setText(String.valueOf(TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)));

        archivedOwner.setText(role.getOwner().getFirstName() + " " + role.getOwner().getLastName());
        archivedSalaryBudget.setText(role.getSalaryBudget());
        archivedSkills.setText(String.join(", ", role.getSkills()));
        archivedOmitted.setText(Integer.toString(role.getStatus().getOmitted()));
        archivedConfirmed.setText(Integer.toString(role.getStatus().getConfirmed()));
        archivedCandidates.setText(Integer.toString(role.getStatus().getCandidates()));
        int confirmedPercent = role.getStatus().getConfirmed() * 100 / role.getStatus().getCandidates();
        int omittedPercent = role.getStatus().getOmitted() * 100 / role.getStatus().getCandidates();
        archivedBarConfirmed.setPrefWidth(confirmedPercent * archivedBarCandidates.getPrefWidth() / 100);
        archivedBarOmitted.setPrefWidth(omittedPercent * archivedBarCandidates.getPrefWidth() / 100);

    }
}
