package model;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-12
 **/
public class Garden {
    private String gardenId;
    private String gardenType;
    private String gardenLocation;
    private double extendOfLand;
    private String description;

    public Garden() {
    }

    public Garden(String gardenId, String gardenType, String gardenLocation, double extendOfLand, String description) {
        this.gardenId = gardenId;
        this.gardenType = gardenType;
        this.gardenLocation = gardenLocation;
        this.extendOfLand = extendOfLand;
        this.description = description;
    }

    public String getGardenId() {
        return gardenId;
    }

    public void setGardenId(String gardenId) {
        this.gardenId = gardenId;
    }

    public String getGardenType() {
        return gardenType;
    }

    public void setGardenType(String gardenType) {
        this.gardenType = gardenType;
    }

    public String getGardenLocation() {
        return gardenLocation;
    }

    public void setGardenLocation(String gardenLocation) {
        this.gardenLocation = gardenLocation;
    }

    public double getExtendOfLand() {
        return extendOfLand;
    }

    public void setExtendOfLand(double extendOfLand) {
        this.extendOfLand = extendOfLand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Garden{" +
                "gardenId='" + gardenId + '\'' +
                ", gardenType='" + gardenType + '\'' +
                ", gardenLocation='" + gardenLocation + '\'' +
                ", extendOfLand=" + extendOfLand +
                ", description='" + description + '\'' +
                '}';
    }
}
