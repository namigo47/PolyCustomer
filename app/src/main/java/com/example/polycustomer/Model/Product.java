package com.example.polycustomer.Model;

import java.io.Serializable;

public class Product implements Serializable {
    private String avatarP;
    private String detail;
    private String donVi;
    private String key;
    private String nameP;
    private int price;
    private String type;
    private int soLuong;

    public Product(String avatarP, String detail, String donVi, String key, String nameP, int price, String type, int soLuong) {
        this.avatarP = avatarP;
        this.detail = detail;
        this.donVi = donVi;
        this.key = key;
        this.nameP = nameP;
        this.price = price;
        this.type = type;
        this.soLuong = soLuong;
    }

    public Product() {
    }

    public String getAvatarP() {
        return avatarP;
    }

    public void setAvatarP(String avatarP) {
        this.avatarP = avatarP;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNameP() {
        return nameP;
    }

    public void setNameP(String nameP) {
        this.nameP = nameP;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
