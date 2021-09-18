package model;

import java.util.ArrayList;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-18
 **/
public class Purchase {
    private String buyingId;
    private String supId;
    private String buyingDate;
    private double buyingCost;
    private ArrayList<PurchaseDetails> purchaseDetails;

    public Purchase() {
    }

    public Purchase(String buyingId, String supId, String buyingDate, double buyingCost, ArrayList<PurchaseDetails> purchaseDetails) {
        this.buyingId = buyingId;
        this.supId = supId;
        this.buyingDate = buyingDate;
        this.buyingCost = buyingCost;
        this.purchaseDetails = purchaseDetails;
    }

    public String getBuyingId() {
        return buyingId;
    }

    public void setBuyingId(String buyingId) {
        this.buyingId = buyingId;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getBuyingDate() {
        return buyingDate;
    }

    public void setBuyingDate(String buyingDate) {
        this.buyingDate = buyingDate;
    }

    public double getBuyingCost() {
        return buyingCost;
    }

    public void setBuyingCost(double buyingCost) {
        this.buyingCost = buyingCost;
    }

    public ArrayList<PurchaseDetails> getPurchaseDetails() {
        return purchaseDetails;
    }

    public void setPurchaseDetails(ArrayList<PurchaseDetails> purchaseDetails) {
        this.purchaseDetails = purchaseDetails;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "buyingId='" + buyingId + '\'' +
                ", supId='" + supId + '\'' +
                ", buyingDate='" + buyingDate + '\'' +
                ", buyingCost=" + buyingCost +
                ", purchaseDetails=" + purchaseDetails +
                '}';
    }
}
