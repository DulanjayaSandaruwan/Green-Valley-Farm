package model;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-18
 **/
public class FinalProductDetails {
    private String finalProductId;
    private String collectId;
    private int productQty;

    public FinalProductDetails() {
    }

    public FinalProductDetails(String finalProductId, String collectId, int productQty) {
        this.finalProductId = finalProductId;
        this.collectId = collectId;
        this.productQty = productQty;
    }

    public String getFinalProductId() {
        return finalProductId;
    }

    public void setFinalProductId(String finalProductId) {
        this.finalProductId = finalProductId;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }

    @Override
    public String toString() {
        return "FinalProductDetails{" +
                "finalProductId='" + finalProductId + '\'' +
                ", collectId='" + collectId + '\'' +
                ", productQty=" + productQty +
                '}';
    }
}
