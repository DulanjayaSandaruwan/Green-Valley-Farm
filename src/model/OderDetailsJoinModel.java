package model;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-19
 **/
public class OderDetailsJoinModel {
    private Customer customer;
    private Order order;
    private Products products;

    public OderDetailsJoinModel() {
    }

    public OderDetailsJoinModel(Customer customer, Order order, Products products) {
        this.customer = customer;
        this.order = order;
        this.products = products;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }
}
