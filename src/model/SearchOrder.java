package model;

import java.util.Objects;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-24
 **/
public class SearchOrder {
    private String customerId;
    private String customerName;
    private String orderId;
    private String orderDate;
    private double totalCost;

    public SearchOrder() {
    }

    public SearchOrder(String customerId, String customerName, String orderId, String orderDate, double totalCost) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.orderId = orderId;
        this.orderDate = orderDate;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "SearchOrder{" +
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", totalCost=" + totalCost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchOrder that = (SearchOrder) o;
        return Double.compare(that.totalCost, totalCost) == 0 &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(customerName, that.customerName) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(orderDate, that.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, customerName, orderId, orderDate, totalCost);
    }
}
