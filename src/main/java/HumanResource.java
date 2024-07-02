public class HumanResource extends Resource{
    private String humanResourceId;
    private String humanResourceName;
    private double humanResourceUnitCost;
    private String humanResourceType;
    private boolean humanResourceAvailability;
    private double hourlyRate;

    public HumanResource(String resourceId, String resourceName, double resourceUnitCost, String resourceType, boolean resourceAvailability, double hourlyRate) {
        super(resourceId, resourceName, resourceUnitCost, resourceType, resourceAvailability);
        this.hourlyRate = hourlyRate;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public HumanResource(String humanResourceId, String humanResourceName, double humanResourceUnitCost, String humanResourceType, boolean humanResourceAvailability) {
        super(humanResourceId, humanResourceName, humanResourceUnitCost, humanResourceType, humanResourceAvailability);
        this.humanResourceId = humanResourceId;
        this.humanResourceName = humanResourceName;
        this.humanResourceUnitCost = humanResourceUnitCost;
        this.humanResourceType = humanResourceType;
        this.humanResourceAvailability = humanResourceAvailability;
    }

    public String getHumanResourceId() {
        return humanResourceId;
    }

    public void setHumanResourceId(String humanResourceId) {
        this.humanResourceId = humanResourceId;
    }

    public String getHumanResourceName() {
        return humanResourceName;
    }

    public void setHumanResourceName(String humanResourceName) {
        this.humanResourceName = humanResourceName;
    }

    public double getHumanResourceUnitCost() {
        return humanResourceUnitCost;
    }

    public void setHumanResourceUnitCost(double humanResourceUnitCost) {
        this.humanResourceUnitCost = humanResourceUnitCost;
    }

    public String getHumanResourceType() {
        return humanResourceType;
    }

    public void setHumanResourceType(String humanResourceType) {
        this.humanResourceType = humanResourceType;
    }

    public boolean isHumanResourceAvailability() {
        return humanResourceAvailability;
    }

    public void setHumanResourceAvailability(boolean humanResourceAvailability) {
        this.humanResourceAvailability = humanResourceAvailability;
    }
}
