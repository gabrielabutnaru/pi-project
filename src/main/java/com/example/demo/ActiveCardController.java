package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * The controller for the active card component.
 */
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
    @FXML
    private Label sharedWithLabel;
    @FXML
    private HBox sharedWithButton;
    private Role role;

    /**
     * Function that runs when the card is pressed.
     * Used to navigate to role details screen.
     */
    @FXML
    public void onCardButtonClick() throws IOException {
        Data.setCurrentRoleId(role.getId());
        Scenery.getInstance().changeScene(Screen.ROLE_DETAILS);
    }

    /**
     * Function that draws a given role on the screen based on the active card component.
     * @param role given role
     */
    public void drawData(Role role) throws SQLException {
        this.role = role;

        String sharedWith = String.join("\n", Data.getRoleSharedWithUsers(role.getId()).stream().map(user -> user.getFirstName() + " " + user.getLastName()).collect(Collectors.toCollection(ArrayList<String>::new)));
        if (sharedWith.isEmpty()) {
            sharedWithButton.setVisible(false);
        }
        sharedWithLabel.getTooltip().setText(sharedWith);

        cardOwner.setText(role.getOwner().getFirstName() + " " + role.getOwner().getLastName());
        cardTitle.setText(role.getTitle());

        cardCity.setText(role.getCity());
        cardSkills.setText(String.join(", ", role.getSkills()));
        cardSalaryBudget.setText(role.getSalaryBudget());

        int rejectedCandidatesCount = (int) role.getCandidates().stream().filter(c -> c.getStatus() == EStatus.OMITTED || c.getStatus() == EStatus.FAILED).count();
        int reviewedCandidatesCount = (int) role.getCandidates().stream().filter(c -> c.getStatus() != EStatus.DEFAULT).count();
        int allCandidatesCount = role.getCandidates().size();

        cardOmitted.setText(Integer.toString(rejectedCandidatesCount));
        cardConfirmed.setText(Integer.toString(reviewedCandidatesCount));
        cardCandidates.setText(Integer.toString(allCandidatesCount));

        int confirmedPercent = allCandidatesCount == 0 ? 0 : reviewedCandidatesCount * 100 / allCandidatesCount;
        int omittedPercent = allCandidatesCount == 0 ? 0 : rejectedCandidatesCount * 100 / allCandidatesCount;

        barConfirmed.setPrefWidth(confirmedPercent * barCandidates.getPrefWidth() / 100);
        barOmitted.setPrefWidth(omittedPercent * barCandidates.getPrefWidth() / 100);

        Date currentDate = new Date();
        Date postDate = role.getDate();
        long diffInMillies = Math.abs(postDate.getTime() - currentDate.getTime());
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        cardDate.setText(Long.toString(diffInDays));
    }
}
