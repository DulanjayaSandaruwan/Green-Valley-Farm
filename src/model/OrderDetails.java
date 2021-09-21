package model;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-17
 **/
public class OrderDetails {
    private String finalProductId;
    private String orderId;
    private int orderQty;
    private double discount;
    private String finalProductName;
    private String finalProductType;
    private double unitPrice;
    private double itemTotal;

    public OrderDetails() {
    }

    public OrderDetails(String finalProductId, String orderId, int orderQty, double discount, String finalProductName, String finalProductType, double unitPrice, double itemTotal) {
        this.finalProductId = finalProductId;
        this.orderId = orderId;
        this.orderQty = orderQty;
        this.discount = discount;
        this.finalProductName = finalProductName;
        this.finalProductType = finalProductType;
        this.unitPrice = unitPrice;
        this.itemTotal = itemTotal;
    }

    public String getFinalProductId() {
        return finalProductId;
    }

    public void setFinalProductId(String finalProductId) {
        this.finalProductId = finalProductId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
}
