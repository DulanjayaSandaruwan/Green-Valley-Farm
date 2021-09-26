package view.tm;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-26
 **/
public class SearchCollectTM {
    private String gardenId;
    private String gardenLocation;
    private String gardenType;
    private String collectId;
    private String collectDate;

    public SearchCollectTM() {
    }

    public SearchCollectTM(String gardenId, String gardenLocation, String gardenType, String collectId, String collectDate) {
        this.gardenId = gardenId;
        this.gardenLocation = gardenLocation;
        this.gardenType = gardenType;
        this.collectId = collectId;
        this.collectDate = collectDate;
    }

    public String getGardenId() {
        return gardenId;
    }

    public void setGardenId(String gardenId) {
        this.gardenId = gardenId;
    }

    public String getGardenLocation() {
        return gardenLocation;
    }

    public void setGardenLocation(String gardenLocation) {
        this.gardenLocation = gardenLocation;
    }

    public String getGardenType() {
        return gardenType;
    }

    public void setGardenType(String gardenType) {
        this.gardenType = gardenType;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(String collectDate) {
        this.collectDate = collectDate;
    }
}
