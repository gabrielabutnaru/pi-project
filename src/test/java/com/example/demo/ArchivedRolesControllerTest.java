package com.example.demo;

import javafx.application.Platform;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import jdk.jfr.Description;
import model.Data;
import model.Role;
import model.Screen;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class ArchivedRolesControllerTest {
    ArchivedRolesController controller;

    @Start
    void start(Stage stage) throws IOException {
        Data.setCurrentUser(new User(0, "test", "test", "https://upload.wikimedia.org/wikipedia/commons/c/ca/1x1.png"));
        controller = Scenery.getInstance().getArchivedRolesController();
    }

    @Test
    @DisplayName("it displays the available archived roles")
    void rolesAvailable() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Role r1 = new Role();
                r1.setOwner(new User(0, "test", "test", "https://upload.wikimedia.org/wikipedia/commons/c/ca/1x1.png"));
                r1.setActive(false);
                r1.setDate(new Date());
                Data.roles = new ArrayList<>(List.of(r1, r1));
                controller.redraw();

                /* role, separator, role = 3 children */
                Assertions.assertInstanceOf(VBox.class, controller.getArchivedCardLayout().getChildren().getFirst());
                Assertions.assertInstanceOf(Separator.class, controller.getArchivedCardLayout().getChildren().get(1));
                Assertions.assertInstanceOf(VBox.class, controller.getArchivedCardLayout().getChildren().getLast());
                Assertions.assertEquals(3, controller.getArchivedCardLayout().getChildren().size());
            }
        });
    }

    @Test
    @DisplayName("it displays informative message when only active roles are existent")
    void onlyActiveRoles() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Role r1 = new Role();
                r1.setActive(true);
                Data.roles = new ArrayList<>(List.of(r1, r1));
                controller.redraw();

                Assertions.assertEquals(1,
                        controller.getArchivedCardLayout().getChildren().size());
                Assertions.assertInstanceOf(Label.class, controller.getArchivedCardLayout().getChildren().getFirst());
                Assertions.assertEquals("There are only active roles!",
                        ((Label) controller.getArchivedCardLayout().getChildren().getFirst()).getText());
            }
        });
    }

    @Test
    @DisplayName("it displays informative message regarding no roles")
    void noRoles() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Data.roles = new ArrayList<>();
                controller.redraw();

                Assertions.assertEquals(1,
                        controller.getArchivedCardLayout().getChildren().size());
                Assertions.assertInstanceOf(Label.class, controller.getArchivedCardLayout().getChildren().getFirst());
                Assertions.assertEquals("There are no roles at all!",
                        ((Label) controller.getArchivedCardLayout().getChildren().getFirst()).getText());
            }
        });
    }
}
