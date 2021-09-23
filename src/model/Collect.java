package model;

import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-18
 **/
public class Collect {
    private String collectId;
    private String collectDate;
    private String gardenId;
    private ArrayList<FinalProductDetails> finalProductDetails;

    public Collect() {
    }

    public Collect(String collectId, String collectDate, String gardenId, ArrayList<FinalProductDetails> finalProductDetails) {
        this.collectId = collectId;
        this.collectDate = collectDate;
        this.gardenId = gardenId;
        this.finalProductDetails = finalProductDetails;
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

    public String getGardenId() {
        return gardenId;
    }

    public void setGardenId(String gardenId) {
        this.gardenId = gardenId;
    }

    public ArrayList<FinalProductDetails> getFinalProductDetails() {
        return finalProductDetails;
    }

    public void setFinalProductDetails(ArrayList<FinalProductDetails> finalProductDetails) {
        this.finalProductDetails = finalProductDetails;
    }

    @Override
    public String toString() {
        return "Collect{" +
                "collectId='" + collectId + '\'' +
                ", collectDate='" + collectDate + '\'' +
                ", gardenId='" + gardenId + '\'' +
                ", finalProductDetails=" + finalProductDetails +
                '}';
    }
}