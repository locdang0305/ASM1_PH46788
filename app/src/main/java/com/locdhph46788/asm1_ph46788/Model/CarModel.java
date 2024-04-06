package com.locdhph46788.asm1_ph46788.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class CarModel implements Serializable {
    private String _id;
    private String name;
    private String price;
    private String quantity;
    private String statuss;
    private ArrayList<String> image;

    public CarModel() {
    }
    public CarModel( String name, String price, String quantity, String statuss) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.statuss = statuss;
    }
    public CarModel(String name, String price, String quantity, String statuss, ArrayList<String> image) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.statuss = statuss;
        this.image = image;
    }
    public CarModel(String _id, String name, String price, String quantity, String statuss) {
        this._id = _id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.statuss = statuss;
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

    public String getStatuss() {
        return statuss;
    }

    public void setStatuss(String statuss) {
        this.statuss = statuss;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }
}
