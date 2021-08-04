package com.example.easybuy.entity;

/**
 * 订单详细表Vo类
 */
public class OrderDetailVo {
    private int id;             //详细订单主键
    private int orderId;        //订单主键
    private int quantity;       //数量
    private double cost;        //消费
    private String name;        //商品名称
    private String fileName;    //商品图片文件名

    public OrderDetailVo() {
    }

    public OrderDetailVo(int id, int orderId, int quantity, double cost, String name, String fileName) {
        this.id = id;
        this.orderId = orderId;
        this.quantity = quantity;
        this.cost = cost;
        this.name = name;
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "OrderDetailVo{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", quantity=" + quantity +
                ", cost=" + cost +
                ", name='" + name + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
