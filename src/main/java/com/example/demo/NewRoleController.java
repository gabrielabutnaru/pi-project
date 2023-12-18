package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.Candidate;
import model.Data;
import model.Screen;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class NewRoleController implements Initializable {
    @FXML
    private ImageView userAvatar;

    @FXML
    private TextField titleField;

    @FXML
    private Label titleErrorMessageLabel;

    @FXML
    private TextField cityField;

    @FXML
    private Label cityErrorMessageLabel;

    @FXML
    private TextField salaryBudgetField;

    @FXML
    private Label salaryBudgetErrorMessageLabel;

    @FXML
    private TextField skillsField;

    @FXML
    private FlowPane skillsContainer;

    @FXML
    private Label userFullName;

    @FXML
    private Slider skillsMatchingPercentageSlider;

    @FXML
    private Label skillsErrorMessageLabel;

    private final ObservableList<String> skills = FXCollections.observableArrayList();

    @FXML
    public void onActiveRolesButtonClick() throws IOException {
        Scenery.getInstance().changeScene(Screen.ACTIVE_ROLES);
    }

    @FXML
    public void onArchivedRolesButtonClick() throws IOException {
        Scenery.getInstance().changeScene(Screen.ARCHIVED_ROLES);
    }

    @FXML
    private void onLogOutButtonClick() throws IOException {
        Scenery.getInstance().changeScene(Screen.LOGIN);
    }

    @FXML
    private void onAddSkillButtonClick() {
        if (!Objects.equals(skillsField.getText(), "")) {
            skills.add(skillsField.getText());
            skillsField.setText("");
        }
    }

    @FXML
    private void onCreateNewRoleButton() throws SQLException, IOException {
        Boolean hasError = false;
        if (skills.isEmpty()) {
            skillsErrorMessageLabel.setVisible(true);
            hasError = true;
        } else {
            skillsErrorMessageLabel.setVisible(false);
        }
        if (cityField.getText().equals("")) {
            cityErrorMessageLabel.setVisible(true);
            hasError = true;
        } else {
            cityErrorMessageLabel.setVisible(false);
        }
        if (titleField.getText().equals("")) {
            titleErrorMessageLabel.setVisible(true);
            hasError = true;
        } else {
            titleErrorMessageLabel.setVisible(false);
        }
        if (salaryBudgetField.getText().equals("")) {
            salaryBudgetErrorMessageLabel.setVisible(true);
            hasError = true;
        } else {
            salaryBudgetErrorMessageLabel.setVisible(false);
        }
        if (hasError) return;
        Integer newRoleId = Data.addNewRoleToDataBase(this.titleField.getText(), this.cityField.getText(), this.salaryBudgetField.getText(), this.skills.stream().map(String::strip).collect(Collectors.joining(",")));
        if (newRoleId != null) {
            List<Candidate> candidates = Data.getAllCandidates();
            for (Candidate candidate : candidates) {
                int countSkills = 0;
                for (String skill : skills) {
                    if (candidate.getSkills().contains(skill)) countSkills++;
                }
                if (countSkills >= skillsMatchingPercentageSlider.getValue()) {
                    Data.addCandidateToRole(candidate.getId(), newRoleId);
                }
            }
            Data.setCurrentRoleId(newRoleId);
            Scenery.getInstance().changeScene(Screen.ROLE_DETAILS);
        }
    }


    public void redraw() {
        Circle clip = new Circle(32, 32, 32);
        userAvatar.setImage(new Image(Data.getCurrentUser().getAvatar(), true));
        userAvatar.setClip(clip);
        this.userFullName.setText(Data.getCurrentUser().getFirstName() + " " + Data.getCurrentUser().getLastName());
        this.titleField.setText("");
        this.titleErrorMessageLabel.setVisible(false);
        this.cityField.setText("");
        this.cityErrorMessageLabel.setVisible(false);
        this.salaryBudgetField.setText("");
        this.salaryBudgetErrorMessageLabel.setVisible(false);
        this.skills.clear();
        this.skillsErrorMessageLabel.setVisible(false);
    }

    public void drawChips() {
            skillsContainer.getChildren().clear();
            for (int i = 0; i < skills.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("chip.fxml"));
                try {
                    HBox cardBox = fxmlLoader.load();
                    ChipController chipController = fxmlLoader.getController();
                    int finalI = i;
                    chipController.setData(skills.get(i), new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            skills.remove(finalI);
                        }
                    });
                    skillsContainer.getChildren().add(cardBox);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        skills.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                drawChips();
                skillsMatchingPercentageSlider.setMax(skills.size());
                skillsMatchingPercentageSlider.setValue(skills.size());
            }
        });
    }
}
