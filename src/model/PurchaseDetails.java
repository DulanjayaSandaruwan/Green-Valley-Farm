package model;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-18
 **/
public class PurchaseDetails {
    private String buyingId;
    private String supItemCode;
    private int buyingQty;

    public PurchaseDetails() {
    }

    public PurchaseDetails(String buyingId, String supItemCode, int buyingQty) {
        this.buyingId = buyingId;
        this.supItemCode = supItemCode;
        this.buyingQty = buyingQty;
    }

    public String getBuyingId() {
        return buyingId;
    }

    public void setBuyingId(String buyingId) {
        this.buyingId = buyingId;
    }

    public String getSupItemCode() {
        return supItemCode;
    }

    public void setSupItemCode(String supItemCode) {
        this.supItemCode = supItemCode;
    }

    public int getBuyingQty() {
        return buyingQty;
    }

    public void setBuyingQty(int buyingQty) {
        this.buyingQty = buyingQty;
    }

    @Override
    public String toString() {
        return "PurchaseDetails{" +
                "buyingId='" + buyingId + '\'' +
                ", supItemCode='" + supItemCode + '\'' +
                ", buyingQty=" + buyingQty +
                '}';
    }
}

