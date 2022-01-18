package model;

public class Product {
    private int id;
    private String type;
    private int quantity;
    private float price;

    public Product() {
    }

    public Product(int id, String type, int quantity, float price) {
        this.id = id;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
