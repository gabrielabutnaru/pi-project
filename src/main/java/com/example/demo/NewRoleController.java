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
import javafx.scene.shape.Circle;
import model.Candidate;
import model.Data;
import model.Screen;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The controller for the new role screen.
 */
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

    /**
     * Function that runs when the active roles button is clicked.
     * Used to navigate to the active roles screen.
     */
    @FXML
    public void onActiveRolesButtonClick() throws IOException {
        Scenery.getInstance().changeScene(Screen.ACTIVE_ROLES);
    }

    /**
     * Function that runs when the archived roles button is clicked.
     * Used to navigate to the archived roles screen.
     */
    @FXML
    public void onArchivedRolesButtonClick() throws IOException {
        Scenery.getInstance().changeScene(Screen.ARCHIVED_ROLES);
    }

    /**
     * Function that runs when the logout button is clicked.
     * Used to log out the user.
     */
    @FXML
    private void onLogOutButtonClick() throws IOException {
        Scenery.getInstance().changeScene(Screen.LOGIN);
    }

    /**
     * Function that runs when the + button is clicked.
     * Used to add a new skill to the role.
     */
    @FXML
    private void onAddSkillButtonClick() {
        if (!Objects.equals(skillsField.getText(), "")) {
            skills.add(skillsField.getText());
            skillsField.setText("");
        }
    }

    /**
     * Function that runs when the create role button is clicked.
     * Used to create a new role.
     */
    @FXML
    private void onCreateNewRoleButton() throws SQLException, IOException {
        Boolean hasError = false;
        if (skills.isEmpty()) {
            skillsErrorMessageLabel.setVisible(true);
            hasError = true;
        } else {
            skillsErrorMessageLabel.setVisible(false);
        }
        if (cityField.getText().isEmpty()) {
            cityErrorMessageLabel.setVisible(true);
            hasError = true;
        } else {
            cityErrorMessageLabel.setVisible(false);
        }
        if (titleField.getText().isEmpty()) {
            titleErrorMessageLabel.setVisible(true);
            hasError = true;
        } else {
            titleErrorMessageLabel.setVisible(false);
        }
        if (salaryBudgetField.getText().isEmpty()) {
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


    /**
     * Function that updates the screen state.
     */
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

    /**
     * Function that draws the skills chips based on the chip component.
     */
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

    /**
     * Function that is called when the component is initialized.
     * @param url -
     * @param resourceBundle -
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        skills.addListener(new ListChangeListener<String>() {
            /**
             * Function that runs when a change is made to the skills list.
             * @param change -
             */
            @Override
            public void onChanged(Change<? extends String> change) {
                drawChips();
                skillsMatchingPercentageSlider.setMax(skills.size());
                skillsMatchingPercentageSlider.setValue(skills.size());
            }
        });
    }
}
