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

    public OrderDetails() {
    }

    public OrderDetails(String finalProductId, String orderId, int orderQty, double discount) {
        this.finalProductId = finalProductId;
        this.orderId = orderId;
        this.orderQty = orderQty;
        this.discount = discount;
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

    @Override
    public String toString() {
        return "OrderDetails{" +
                "finalProductId='" + finalProductId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderQty=" + orderQty +
                ", discount=" + discount +
                '}';
    }
}
