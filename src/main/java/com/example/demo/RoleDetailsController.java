package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
    private void onBackButtonClick() throws IOException, SQLException {
        Scenery.getInstance().changeScene(Screen.ACTIVE_ROLES);
    }

    @FXML
    private void onArchiveButtonClick() throws IOException, SQLException {
        Data.archiveRole(Data.getCurrentRoleId());
        Scenery.getInstance().changeScene(Screen.ACTIVE_ROLES);
    }

    @FXML
    private void onExportToCSVButtonClick() throws IOException {
        FileChooser fileChooser = new FileChooser();
        Role role = Data.getCurrentRole();
        String fileName = role.getTitle() + " by " + role.getOwner().getFirstName() + " " + role.getOwner().getLastName() + " - generat la " + LocalDate.now() + ".csv";
        fileChooser.setInitialFileName(fileName);

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(Scenery.getInstance().getStage());

        if (file != null) {
            List<String[]> dataLines = new ArrayList<>();
            dataLines.add(new String[]{"First Name", "Last Name", "Age", "City", "Phone", "Current Status"});
            for (Candidate candidate : role.getCandidates()) {
                dataLines.add(new String[]{candidate.getFirstName(), candidate.getLastName(), Integer.toString(candidate.getAge()), candidate.getCity(), candidate.getPhone(), Data.EStatusToString.get(candidate.getStatus())});
            }
            try (PrintWriter pw = new PrintWriter(file)) {
                dataLines.stream()
                        .map(this::convertToCSV)
                        .forEach(pw::println);
            }
        }
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    private String escapeSpecialCharacters(String data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data cannot be null");
        }
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    @FXML
    private void onShareButtonClick() throws IOException {
        //Scenery.getInstance().getShareWithController().drawData();
        Scenery.getInstance().changeScene(Screen.SHARE_WITH);
    }

    public void redraw() {
        Role role = Data.getCurrentRole();

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
}
