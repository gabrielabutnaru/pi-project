package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.Data;
import model.Screen;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class NewRoleController implements Initializable {
    @FXML
    private ImageView userAvatar;

    @FXML
    private TextField skillsField;

    @FXML
    private FlowPane skillsContainer;

    @FXML
    private Label userFullName;

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


    public void redraw() {
        Circle clip = new Circle(32, 32, 32);
        userAvatar.setImage(new Image(Data.getCurrentUser().getAvatar(), true));
        userAvatar.setClip(clip);
        this.userFullName.setText(Data.getCurrentUser().getFirstName() + " " + Data.getCurrentUser().getLastName());
        skills.clear();
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
            }
        });
    }
}
