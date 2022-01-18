package model;

import java.sql.Timestamp;

public class Order {

    private int id;
    private String nameClient;
    private String product;
    private int orderQuantity;
    private Timestamp timestamp;

    public Order() {
    }

    public Order(int id, String nameClient, String product, int orderQuantity) {
        this.id = id;
        this.nameClient = nameClient;
        this.product = product;
        this.orderQuantity = orderQuantity;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameClient() {
        return nameClient;
    }


    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
