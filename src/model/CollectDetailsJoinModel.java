package model;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-23
 **/
public class CollectDetailsJoinModel {
    private Garden garden;
    private Collect collect;
    private Products products;

    public CollectDetailsJoinModel() {
    }

    public CollectDetailsJoinModel(Garden garden, Collect collect, Products products) {
        this.garden = garden;
        this.collect = collect;
        this.products = products;
    }

    public Garden getGarden() {
        return garden;
    }

    public void setGarden(Garden garden) {
        this.garden = garden;
    }

    public Collect getCollect() {
        return collect;
    }

    public void setCollect(Collect collect) {
        this.collect = collect;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "CollectDetailsJoinModel{" +
                "garden=" + garden +
                ", collect=" + collect +
                ", products=" + products +
                '}';
    }
}
