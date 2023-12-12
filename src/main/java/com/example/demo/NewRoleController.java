package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class NewRoleController implements Initializable {
    @FXML
    private TextField skillsField;
    @FXML
    private FlowPane skillsContainer;

    private final ObservableList<String> skills = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        skills.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                drawChips();
            }
        });
    }

    @FXML
    private void onAddSkillButtonClick() {
        if (!Objects.equals(skillsField.getText(), "")) {
            skills.add(skillsField.getText());
            skillsField.setText("");
        }
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

}
