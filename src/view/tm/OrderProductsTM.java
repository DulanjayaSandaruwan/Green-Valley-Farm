package view.tm;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-09-24
 **/
public class OrderProductsTM {
    private String productId;
    private int qty;
    private double totalCost;

    public OrderProductsTM() {
    }

    public OrderProductsTM(String productId, int qty, double totalCost) {
        this.productId = productId;
        this.qty = qty;
        this.totalCost = totalCost;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
