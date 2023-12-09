package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.EStatus;
import model.Role;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

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
        archivedOwner.setText(role.getOwner().getFirstName() + " " + role.getOwner().getLastName());
        archivedTitle.setText(role.getTitle());

        archivedCity.setText(role.getCity());
        archivedSkills.setText(String.join(", ", role.getSkills()));
        archivedSalaryBudget.setText(role.getSalaryBudget());

        int omittedCandidatesCount = (int) role.getCandidates().stream().filter(c -> c.getStatus() == EStatus.OMITTED || c.getStatus() == EStatus.FAILED).count();
        int confirmedCandidatesCount = (int) role.getCandidates().stream().filter(c -> c.getStatus() == EStatus.IN_TOUCH || c.getStatus() == EStatus.EMPLOYED).count();
        int allCandidatesCount = role.getCandidates().size();

        archivedOmitted.setText(Integer.toString(omittedCandidatesCount));
        archivedConfirmed.setText(Integer.toString(confirmedCandidatesCount));
        archivedCandidates.setText(Integer.toString(allCandidatesCount));

        int confirmedPercent = confirmedCandidatesCount * 100 / allCandidatesCount;
        int omittedPercent = omittedCandidatesCount * 100 / allCandidatesCount;

        archivedBarConfirmed.setPrefWidth(confirmedPercent * archivedBarCandidates.getPrefWidth() / 100);
        archivedBarOmitted.setPrefWidth(omittedPercent * archivedBarCandidates.getPrefWidth() / 100);

        LocalDate currentDate = LocalDate.now();
        LocalDate postDate = role.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long diffInDays = ChronoUnit.DAYS.between(postDate, currentDate);
        archivedDate.setText(Long.toString(diffInDays));
    }
}
