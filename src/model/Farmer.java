package model;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-13
 **/
public class Farmer {
    private String farmerId;
    private String farmerName;
    private String farmerAddress;
    private String farmerContact;
    private String gardenId;

    public Farmer() {
    }

    public Farmer(String farmerId, String farmerName, String farmerAddress, String farmerContact, String gardenId) {
        this.farmerId = farmerId;
        this.farmerName = farmerName;
        this.farmerAddress = farmerAddress;
        this.farmerContact = farmerContact;
        this.gardenId = gardenId;
    }

    public String getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getFarmerAddress() {
        return farmerAddress;
    }

    public void setFarmerAddress(String farmerAddress) {
        this.farmerAddress = farmerAddress;
    }

    public String getFarmerContact() {
        return farmerContact;
    }

    public void setFarmerContact(String farmerContact) {
        this.farmerContact = farmerContact;
    }

    public String getGardenId() {
        return gardenId;
    }

    public void setGardenId(String gardenId) {
        this.gardenId = gardenId;
    }

    @Override
    public String toString() {
        return "Farmer{" +
                "farmerId='" + farmerId + '\'' +
                ", farmerName='" + farmerName + '\'' +
                ", farmerAddress='" + farmerAddress + '\'' +
                ", farmerContact='" + farmerContact + '\'' +
                ", gardenId='" + gardenId + '\'' +
                '}';
    }
}
