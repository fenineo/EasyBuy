package com.example.easybuy.entity;

/**
 * 订单明细表实体类
 */
public class OrderDetail {
    private int id;             //主键
    private int orderId;        //订单主键
    private int productId;      //商品主键
    private int quantity;       //数量
    private double cost;        //消费

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", cost=" + cost +
                '}';
    }

    public OrderDetail() {
    }

    public OrderDetail(int id, int orderId, int productId, int quantity, double cost) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
