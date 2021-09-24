package view.tm;

import java.util.Objects;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-20
 **/
public class OrderDetailsTM {
    private String customerId;
    private String customerName;
    private String orderId;
    private String oderDate;
    private double totalCost;

    public OrderDetailsTM() {
    }

    public OrderDetailsTM(String customerId, String customerName, String orderId, String oderDate, double totalCost) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.orderId = orderId;
        this.oderDate = oderDate;
        this.totalCost = totalCost;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOderDate() {
        return oderDate;
    }

    public void setOderDate(String oderDate) {
        this.oderDate = oderDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "OrderDetailsTM{" +
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", orderId='" + orderId + '\'' +
                ", oderDate='" + oderDate + '\'' +
                ", totalCost=" + totalCost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailsTM that = (OrderDetailsTM) o;
        return Double.compare(that.totalCost, totalCost) == 0 &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(customerName, that.customerName) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(oderDate, that.oderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, customerName, orderId, oderDate, totalCost);
    }
}
