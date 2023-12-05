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
import java.time.LocalDateTime;
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
        User user = new User(1, "Andra", "Enea", "Avatar.png");


        Role role1 = new Role();
        role1.setId(1);
        role1.setTitle("Senior Software Developer");
        role1.setCity("Timisoara, Romania");
        role1.setSalaryBudget("15k - 20k");
        role1.getSkills().add( "Angular");
        role1.getSkills().add( "Github");
        role1.getSkills().add( "Jira");
        role1.setStatus(new Status(5, 10, 20));

        Candidate candidate1 = new Candidate(1, "Maria", "Piersica", 25, "Brasov, Romania","https://i.imgur.com/w7X0qJu.png");
        Job job1 = new Job(1, "Intern", new Date(2020, Calendar.MARCH, 12), new Date(2021, Calendar.FEBRUARY, 10), ESeniority.ENTRY, "Nokia");
        Job job2 = new Job(2, "Junior Developer", new Date(2021, Calendar. JUNE, 2), new Date(2022, Calendar.DECEMBER, 12), ESeniority.JUNIOR, "Intel");
        Job job3 = new Job(3, "Middle Developer", new Date(2022, Calendar.DECEMBER, 19), new Date(2023, Calendar.DECEMBER, 5), ESeniority.MIDDLE, "IBM");
        candidate1.getJobs().add(job3);
        candidate1.getJobs().add(job2);
        candidate1.getJobs().add(job1);
        candidate1.getSkills().add("JavaScript");
        candidate1.getSkills().add("Angular");
        candidate1.getSkills().add( "AWS");
        candidate1.getSkills().add( "C++");
        candidate1.getSkills().add( "Python");
        candidate1.getSkills().add("Java" );
        candidate1.getSkills().add("MySQL");

        Candidate candidate2 = new Candidate(2, "Mihai", "Pop", 30, "Timisoara, Romania","https://i.imgur.com/jULTfan.png");
        Job job4 = new Job(4, "Intern", new Date(2016, Calendar.JUNE, 10), new Date(2017, Calendar.APRIL, 10), ESeniority.ENTRY, "Continental");
        Job job5 = new Job(5, "Junior Developer", new Date(2017, Calendar. JUNE, 2), new Date(2022, Calendar.DECEMBER, 12), ESeniority.JUNIOR, "Intel");
        Job job6 = new Job(6, "Middle Developer", new Date(2022, Calendar.DECEMBER, 19), new Date(2023, Calendar.DECEMBER, 5), ESeniority.MIDDLE, "Nokia");
        candidate2.getJobs().add(job6);
        candidate2.getJobs().add(job5);
        candidate2.getJobs().add(job4);
        candidate2.getSkills().add("JavaScript");
        candidate2.getSkills().add("Angular");
        candidate2.getSkills().add("AWS");
        candidate2.getSkills().add( "C++");
        candidate2.getSkills().add("Python");
        candidate2.getSkills().add("Java");
        candidate2.getSkills().add("MySQL");


        role1.getCandidates().add(candidate1);
        role1.getCandidates().add(candidate2);
        role1.setOwner(user);
        role1.setActive(true);
        role1.setDate(new Date(123, Calendar.DECEMBER, 4));



        Role role2 = new Role();
        role2.setId(2);
        role2.setTitle("Junior Software Developer");
        role2.setCity("Brasov, Romania");
        role2.setSalaryBudget("4k - 6k");
        role2.getSkills().add("C");
        role2.getSkills().add("C++");
        role2.getSkills().add("Github");


        role2.setOwner(user);
        role2.setActive(false);
        role2.setDate(new Date(123, Calendar.JUNE, 10));

        role2.setStatus(new Status(27, 40, 40));


        Role role3 = new Role();
        role3.setId(3);
        role3.setTitle("Middle Software Developer");
        role3.setCity("Timisoara, Romania");
        role3.setSalaryBudget("6k - 8k");
        role3.getSkills().add("Python");
        role3.getSkills().add("Jenkins");
        role3.getSkills().add("MongoDB");

        role3.setOwner(user);
        role3.setActive(true);
        role3.setDate(new Date(123, Calendar.DECEMBER, 2));
        role3.getCandidates().add(candidate1);

        Candidate candidate3 = new Candidate(3, "Andrei", "Neacsu", 28, "Timisoara, Romania","https://imgur.com/YDEKMnk.png");
        Job job7 = new Job(7, "Junior", new Date(2018, Calendar.DECEMBER, 10), new Date(2020, Calendar.APRIL, 10), ESeniority.JUNIOR, "Continental");
        candidate3.getJobs().add(job7);
        candidate3.getJobs().add(job4);

        candidate3.getSkills().add("C++");
        candidate3.getSkills().add("MySQL");
        candidate3.getSkills().add("Java");
        candidate3.getSkills().add("Python");
        candidate3.getSkills().add("Jenkins");

        role3.getCandidates().add(candidate3);
        role3.setStatus(new Status(20, 40, 45));


        Role role4 = new Role();
        role4.setId(4);
        role4.setTitle("Graphic Designer");
        role4.setCity("Oradea, Romania");
        role4.setSalaryBudget("4k - 7k");
        role4.getSkills().add("Figma");
        role4.getSkills().add("Adobe");
        role4.getSkills().add("CSS");
        role4.getSkills().add("HTML");

        role4.setOwner(user);
        role4.setActive(true);

        Candidate candidate4 =  new Candidate(4, "Larisa", "Soare", 19, "Arad, Romania","https://imgur.com/08SGkW1.png");
        candidate4.getSkills().add("Figma");
        candidate4.getSkills().add("Blender");
        candidate4.getSkills().add("Photoshop");
        candidate4.getSkills().add("Canva");
        Job job9 = new Job(9, "UI / UX Designer", new Date(2022,Calendar.DECEMBER, 12), new Date(2023, Calendar.JUNE,23),ESeniority.JUNIOR, "Cobalt Sign");
        candidate4.getJobs().add(job9);
        role4.getCandidates().add(candidate4);

        Candidate candidate5 = new Candidate(5, "Mircea", "Enescu", 23, "Timisoara, Romania", "https://i.imgur.com/WFUT6eE.png");
        candidate5.getSkills().add("Adobe");
        candidate5.getSkills().add("Blender");
        candidate5.getSkills().add("Photoshop");
        candidate5.getSkills().add("Figma");
        Job job8 = new Job(8, "UI / UX Designer", new Date(2021,Calendar.DECEMBER, 12), new Date(2023, Calendar.NOVEMBER,23),ESeniority.JUNIOR, "Cobalt Sign");
        candidate5.getJobs().add(job8);
        role4.getCandidates().add(candidate5);
        role4.setDate(new Date(123, Calendar.NOVEMBER, 23));
        role4.setStatus(new Status(10, 18, 20));


        roles.add(role1);
        roles.add(role2);
        roles.add(role3);
        roles.add(role4);
        return roles;
    }
}
