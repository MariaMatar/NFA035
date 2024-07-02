package com.Nfa035;

public class MaterialResource extends Resource{
    private String materialResourceId;
    private String materialResourceName;
    private double materialResourceUnitCost;
    private String materialResourceType;
    private boolean materialResourceAvailability;

    public MaterialResource(String materialResourceId, String materialResourceName, double materialResourceUnitCost, String materialResourceType, boolean materialResourceAvailability) {
        super(materialResourceId, materialResourceName, materialResourceUnitCost, materialResourceType, materialResourceAvailability);
        this.materialResourceId = materialResourceId;
        this.materialResourceName = materialResourceName;
        this.materialResourceUnitCost = materialResourceUnitCost;
        this.materialResourceType = materialResourceType;
        this.materialResourceAvailability = materialResourceAvailability;
    }

    public String getMaterialResourceId() {
        return materialResourceId;
    }

    public void setMaterialResourceId(String materialResourceId) {
        this.materialResourceId = materialResourceId;
    }

    public String getMaterialResourceName() {
        return materialResourceName;
    }

    public void setMaterialResourceName(String materialResourceName) {
        this.materialResourceName = materialResourceName;
    }

    public double getMaterialResourceUnitCost() {
        return materialResourceUnitCost;
    }

    public void setMaterialResourceUnitCost(double materialResourceUnitCost) {
        this.materialResourceUnitCost = materialResourceUnitCost;
    }

    public String getMaterialResourceType() {
        return materialResourceType;
    }

    public void setMaterialResourceType(String materialResourceType) {
        this.materialResourceType = materialResourceType;
    }

    public boolean isMaterialResourceAvailability() {
        return materialResourceAvailability;
    }

    public void setMaterialResourceAvailability(boolean materialResourceAvailability) {
        this.materialResourceAvailability = materialResourceAvailability;
    }
}
