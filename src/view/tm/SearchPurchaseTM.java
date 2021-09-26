package view.tm;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-26
 **/
public class SearchPurchaseTM {
    private String supplierId;
    private String supplierName;
    private String buyId;
    private String buyingDate;
    private double buyingCost;

    public SearchPurchaseTM() {
    }

    public SearchPurchaseTM(String supplierId, String supplierName, String buyId, String buyingDate, double buyingCost) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.buyId = buyId;
        this.buyingDate = buyingDate;
        this.buyingCost = buyingCost;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getBuyId() {
        return buyId;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
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
}
