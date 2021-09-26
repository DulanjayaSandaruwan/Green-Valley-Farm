package model;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-26
 **/
public class PurchaseItemDetails {
    private String supItemCode;
    private  String buyingQty;

    public PurchaseItemDetails() {
    }

    public PurchaseItemDetails(String supItemCode, String buyingQty) {
        this.supItemCode = supItemCode;
        this.buyingQty = buyingQty;
    }

    public String getSupItemCode() {
        return supItemCode;
    }

    public void setSupItemCode(String supItemCode) {
        this.supItemCode = supItemCode;
    }

    public String getBuyingQty() {
        return buyingQty;
    }

    public void setBuyingQty(String buyingQty) {
        this.buyingQty = buyingQty;
    }

    @Override
    public String toString() {
        return "PurchaseItemDetails{" +
                "supItemCode='" + supItemCode + '\'' +
                ", buyingQty='" + buyingQty + '\'' +
                '}';
    }
}