package com.example.polycustomer.Model;

import java.util.ArrayList;

public class Canteen {
    private String id, avatar, nameboss, nameCanteen, address, phone,token;
    private ArrayList<Product> listProduct;


    public Canteen(String id, String avatar, String nameCanteen, String token) {
        this.id = id;
        this.avatar = avatar;
        this.nameCanteen = nameCanteen;
        this.token = token;
    }
    public Canteen() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNameboss() {
        return nameboss;
    }

    public void setNameboss(String nameboss) {
        this.nameboss = nameboss;
    }

    public String getNameCanteen() {
        return nameCanteen;
    }

    public void setNameCanteen(String nameCanteen) {
        this.nameCanteen = nameCanteen;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<Product> getListProduct() {
        return listProduct;
    }

    public void setListProduct(ArrayList<Product> listProduct) {
        this.listProduct = listProduct;
    }
}
