package com.Nfa035;
import java.util.*;
public class Task {
    private String id;
    private String name;
    private List<java.lang.Process> processes; // List of processes associated with the task
    private double cost;
    private String state;
    private int duration;

    public Task(String id, String name, List<java.lang.Process> processes, double cost, String state, int duration) {
        this.id = id;
        this.processes = processes;
        this.cost = cost;
        this.state = state;
        this.duration = duration;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<java.lang.Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<java.lang.Process> processes) {
        this.processes = processes;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
