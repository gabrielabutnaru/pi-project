package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.EStatus;
import model.Role;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * The controller for the active card component.
 */
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

    /**
     * Function that draws a given role on the screen based on the archived card component.
     * @param role given role
     */
    public void setData (Role role) throws ParseException {
        archivedOwner.setText(role.getOwner().getFirstName() + " " + role.getOwner().getLastName());
        archivedTitle.setText(role.getTitle());

        archivedCity.setText(role.getCity());
        archivedSkills.setText(String.join(", ", role.getSkills()));
        archivedSalaryBudget.setText(role.getSalaryBudget());

        int rejectedCandidatesCount = (int) role.getCandidates().stream().filter(c -> c.getStatus() == EStatus.OMITTED || c.getStatus() == EStatus.FAILED).count();
        int reviewedCandidatesCount = (int) role.getCandidates().stream().filter(c -> c.getStatus() != EStatus.DEFAULT).count();
        int allCandidatesCount = role.getCandidates().size();

        archivedOmitted.setText(Integer.toString(rejectedCandidatesCount));
        archivedConfirmed.setText(Integer.toString(reviewedCandidatesCount));
        archivedCandidates.setText(Integer.toString(allCandidatesCount));

        int confirmedPercent = allCandidatesCount == 0 ? 0 : reviewedCandidatesCount * 100 / allCandidatesCount;
        int omittedPercent = allCandidatesCount == 0 ? 0 : rejectedCandidatesCount * 100 / allCandidatesCount;

        archivedBarConfirmed.setPrefWidth(confirmedPercent * archivedBarCandidates.getPrefWidth() / 100);
        archivedBarOmitted.setPrefWidth(omittedPercent * archivedBarCandidates.getPrefWidth() / 100);

        Date currentDate = new Date();
        Date postDate = role.getDate();
        long diffInMillies = Math.abs(postDate.getTime() - currentDate.getTime());
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        archivedDate.setText(Long.toString(diffInDays));
    }
}
