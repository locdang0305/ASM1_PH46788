package com.locdhph46788.asm1_ph46788;

public class CarModel {
    private String id;
    private String name;
    private String price;
    private String quantity;
    private String status;

    public CarModel() {
    }
    public CarModel( String name, String price, String quantity, String status) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }
    public CarModel(String id, String name, String price, String quantity, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
