package com.locdhph46788.asm1_ph46788;

public class CarModel {
    private String _id;
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
    public CarModel(String _id, String name, String price, String quantity, String status) {
        this._id = _id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
