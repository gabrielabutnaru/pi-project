package com.example.demo;

import javafx.application.Platform;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import jdk.jfr.Description;
import model.*;
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
class CandidateDetailsControllerTest {
    CandidateDetailsController controller;

    @Start
    void start(Stage stage) throws IOException {
        User testUser = new User(0, "test", "test", "https://upload.wikimedia.org/wikipedia/commons/c/ca/1x1.png");
        Data.setCurrentUser(testUser);
        Data.roles = new ArrayList<>(List.of(new Role(0, "Test role", "Test city", new Date(), "Test salary budget", true, testUser)));
        Data.setCurrentRoleId(0);
        Data.setCurrentCandidateId(0);
        Data.roles.getFirst().getCandidates().add(new Candidate(0, "test", "test", 20, "test", "https://upload.wikimedia.org/wikipedia/commons/c/ca/1x1.png", "test", "test", EStatus.DEFAULT));
        controller = Scenery.getInstance().getCandidateDetailsController();
    }

    @Test
    @DisplayName("it displays OMITTED when the candidate has the OMITTED status")
    void omittedCandidate() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Data.roles.getFirst().getCandidates().getFirst().setStatus(EStatus.OMITTED);
                controller.redraw();

                Assertions.assertEquals("OMITTED", controller.getCandidateStatus().getText());
            }
        });
    }

    @Test
    @DisplayName("it displays - when the candidate has the DEFAULT status")
    void defaultCandidate() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Data.roles.getFirst().getCandidates().getFirst().setStatus(EStatus.DEFAULT);
                controller.redraw();

                Assertions.assertEquals("-", controller.getCandidateStatus().getText());
            }
        });
    }

    @Test
    @DisplayName("it displays IN TOUCH when the candidate has the IN_TOUCH status")
    void inTouchCandidate() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Data.roles.getFirst().getCandidates().getFirst().setStatus(EStatus.IN_TOUCH);
                controller.redraw();

                Assertions.assertEquals("IN TOUCH", controller.getCandidateStatus().getText());
            }
        });
    }

    @Test
    @DisplayName("it displays FAILED when the candidate has the FAILED status")
    void failedCandidate() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Data.roles.getFirst().getCandidates().getFirst().setStatus(EStatus.FAILED);
                controller.redraw();

                Assertions.assertEquals("FAILED", controller.getCandidateStatus().getText());
            }
        });
    }

    @Test
    @DisplayName("it displays EMPLOYED when the candidate has the EMPLOYED status")
    void employedCandidate() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Data.roles.getFirst().getCandidates().getFirst().setStatus(EStatus.EMPLOYED);
                controller.redraw();

                Assertions.assertEquals("EMPLOYED", controller.getCandidateStatus().getText());
            }
        });
    }
}
