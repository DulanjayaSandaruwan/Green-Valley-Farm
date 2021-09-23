package view.tm;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-20
 **/
public class OrderDetailsTM {
    private String finalProductId;
    private int orderQty;
    private double discount;
    private String finalProductName;
    private String  finalProductType;
    private double unitPrice;
    private double itemTotal;
    private String customerId;

    public OrderDetailsTM() {
    }

    public OrderDetailsTM(String finalProductId, int orderQty, double discount, String finalProductName, String finalProductType, double unitPrice, double itemTotal, String customerId) {
        this.finalProductId = finalProductId;
        this.orderQty = orderQty;
        this.discount = discount;
        this.finalProductName = finalProductName;
        this.finalProductType = finalProductType;
        this.unitPrice = unitPrice;
        this.itemTotal = itemTotal;
        this.customerId = customerId;
    }

    public String getFinalProductId() {
        return finalProductId;
    }

    public void setFinalProductId(String finalProductId) {
        this.finalProductId = finalProductId;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(double itemTotal) {
        this.itemTotal = itemTotal;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
