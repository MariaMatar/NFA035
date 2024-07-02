package com.Nfa035;
import java.util.*;
public class Project {
    private String projectId;
    private String projectName;
    private String cost;
    private List<Task> tasks; // List of tasks associated with the project
    private int projectDuration; // Duration of the project
    private String projectState; // State of the project

    // Constructor
    public Project(String projectId,String cost, String projectName, List<Task> tasks, int projectDuration, String projectState) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.tasks = tasks;
        this.projectDuration = projectDuration;
        this.projectState = projectState;
        this.cost = cost;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public int getProjectDuration() {
        return projectDuration;
    }

    public void setProjectDuration(int projectDuration) {
        this.projectDuration = projectDuration;
    }

    public String getProjectState() {
        return projectState;
    }

    public void setProjectState(String projectState) {
        this.projectState = projectState;
    }
}
