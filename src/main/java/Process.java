import java.util.*;
public class Process {
    private String processId;
    private String name;
    private double cost;
    private String state;
    private int duration;
    private List<String> resourcesNeeded; // Liste des ressources nécessaires pour ce processus

    public Process(String processId, String name, double cost, String state, int duration, List<String> resourcesNeeded) {
        this.processId = processId;
        this.name = name;
        this.cost = cost;
        this.state = state;
        this.duration = duration;
        this.resourcesNeeded = resourcesNeeded;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<String> getResourcesNeeded() {
        return resourcesNeeded;
    }

    public void setResourcesNeeded(List<String> resourcesNeeded) {
        this.resourcesNeeded = resourcesNeeded;
    }
}
