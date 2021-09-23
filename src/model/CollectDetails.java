package model;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-23
 **/
public class CollectDetails {
    private String finalProductId;
    private String collectId;
    private String finalProductName;
    private String  finalProductType;
    private int productQty;

    public CollectDetails() {
    }

    public CollectDetails(String finalProductId, String collectId, String finalProductName, String finalProductType, int productQty) {
        this.finalProductId = finalProductId;
        this.collectId = collectId;
        this.finalProductName = finalProductName;
        this.finalProductType = finalProductType;
        this.productQty = productQty;
    }

    public String getFinalProductId() {
        return finalProductId;
    }

    public void setFinalProductId(String finalProductId) {
        this.finalProductId = finalProductId;
    }

    public String getFinalProductName() {
        return finalProductName;
    }

    public void setFinalProductName(String finalProductName) {
        this.finalProductName = finalProductName;
    }

    public String getFinalProductType() {
        return finalProductType;
    }

    public void setFinalProductType(String finalProductType) {
        this.finalProductType = finalProductType;
    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }
}
