import java.util.*;
public class Resource {
    private String resourceId;
    protected String resourceName;
    private double resourceUnitCost;
    private String resourceType;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public double getResourceUnitCost() {
        return resourceUnitCost;
    }

    public void setResourceUnitCost(double resourceUnitCost) {
        this.resourceUnitCost = resourceUnitCost;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public boolean isResourceAvailability() {
        return resourceAvailability;
    }

    public void setResourceAvailability(boolean resourceAvailability) {
        this.resourceAvailability = resourceAvailability;
    }

    public Resource(String resourceId, String resourceName, double resourceUnitCost, String resourceType, boolean resourceAvailability) {
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.resourceUnitCost = resourceUnitCost;
        this.resourceType = resourceType;
        this.resourceAvailability = resourceAvailability;
    }

    private boolean resourceAvailability; // Corrected spelling and type to boolean

}
