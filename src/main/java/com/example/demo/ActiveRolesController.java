package com.example.demo;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.*;

public class ActiveRolesController implements Initializable {
    @FXML
    private VBox activeCardLayout;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        activeCardLayout.setOnMousePressed(Event::consume);
        List<Role> roles = new ArrayList<>(getPosts());
        try {
            for (int i = 0; i < roles.size(); i++) {
                if (roles.get(i).getActive()) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("card.fxml"));
                    VBox cardBox = fxmlLoader.load();
                    ActiveCardController cardController = fxmlLoader.getController();
                    cardController.setData(roles.get(i));
                    activeCardLayout.getChildren().add(cardBox);
                    activeCardLayout.getChildren().add(new Separator());
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static List<Role> getPosts() {
        List<Role> roles = new ArrayList<>();
        Role role1 = new Role();
        role1.setId(1);
        role1.setTitle("Senior Software Developer");
        role1.setCity("Timisoara, Romania");
        role1.setSalaryBudget("15k - 20k");
        role1.getSkills().add("Angular");
        role1.getSkills().add("JavaScript");
        role1.setStatus(new Status(5, 10, 20));
        Candidate candidate1 = new Candidate(1, "Maria", "Piersica", 25, "Brasov, Romania","https://i.imgur.com/jULTfan.png");
        Job job1 = new Job(1, "Intern", new Date(2020, Calendar.MARCH, 12), new Date(2021, Calendar.FEBRUARY, 10), ESeniority.ENTRY, "Nokia");
        candidate1.getJobs().add(job1);
        candidate1.getSkills().add("JavaScript");
        candidate1.getSkills().add("Angular");
        candidate1.getSkills().add("AWS");
        role1.getCandidates().add(candidate1);

        User user = new User(1, "Andra", "Enea", "Avatar.png");
        role1.setOwner(user);
        role1.setActive(false);
        role1.setDate(new Date(2022, Calendar.OCTOBER, 12));

        Role role2 = new Role();
        role2.setId(2);
        role2.setTitle("Junior Software Developer");
        role2.setCity("Brasov, Romania");
        role2.setSalaryBudget("10k - 15k");
        role2.getSkills().add("Angular");
        role2.getSkills().add("React");

        role2.setOwner(user);
        role2.setActive(true);
        role2.setDate(new Date(2023, Calendar.NOVEMBER, 11));

        role2.setStatus(new Status(10, 20, 30));

        role2.getCandidates().add(candidate1);

        Role role3 = new Role();
        role3.setId(3);
        role3.setTitle("Junior Software Developer");
        role3.setCity("Brasov, Romania");
        role3.setSalaryBudget("10k - 15k");
        role3.getSkills().add("Angular");
        role3.getSkills().add("React");

        role3.setOwner(user);
        role3.setActive(true);
        role3.setDate(new Date(2023, 9, 11));

        role3.setStatus(new Status(10, 20, 30));

        Role role4 = new Role();
        role4.setId(4);
        role4.setTitle("Junior Software Developer");
        role4.setCity("Brasov, Romania");
        role4.setSalaryBudget("10k - 15k");
        role4.getSkills().add("Angular");
        role4.getSkills().add("React");

        role4.setOwner(user);
        role4.setActive(true);
        role4.setDate(new Date(2023, 9, 11));
        role4.setStatus(new Status(10, 20, 30));

        Role role5 = new Role();
        role5.setId(5);
        role5.setTitle("Junior Software Developer");
        role5.setCity("Brasov, Romania");
        role5.setSalaryBudget("10k - 15k");
        role5.getSkills().add("Angular");
        role5.getSkills().add("React");
        role5.setOwner(user);
        role5.setActive(true);
        role5.setDate(new Date(2023, 9, 11));
        role5.setStatus(new Status(10, 20, 30));

        Role role6 = new Role();
        role6.setId(6);
        role6.setTitle("Junior Software Developer");
        role6.setCity("Brasov, Romania");
        role6.setSalaryBudget("10k - 15k");
        role6.getSkills().add("Angular");
        role6.getSkills().add("React");
        role6.setOwner(user);
        role6.setActive(true);
        role6.setDate(new Date(2023, 9, 11));
        role6.setStatus(new Status(10, 20, 30));

        Role role7 = new Role();
        role7.setId(7);
        role7.setTitle("Junior Software Developer");
        role7.setCity("Brasov, Romania");
        role7.setSalaryBudget("10k - 15k");
        role7.getSkills().add("Angular");
        role7.getSkills().add("React");
        role7.setOwner(user);
        role7.setActive(true);
        role7.setDate(new Date(2023, 9, 11));
        role7.setStatus(new Status(10, 20, 30));

        roles.add(role1);
        roles.add(role2);
        roles.add(role3);
        roles.add(role4);
        roles.add(role5);
        roles.add(role6);
        roles.add(role7);
        return roles;
    }
}
