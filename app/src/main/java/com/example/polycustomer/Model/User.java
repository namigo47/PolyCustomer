package com.example.polycustomer.Model;

public class User {
    private String id, nameUser, numberUser, addressUser, classUser, imageURl, token, listChat;

    public User(String id, String nameUser, String numberUser, String addressUser,
                String classUser, String imageURl, String token, String listChat) {

        this.id = id;
        this.nameUser = nameUser;
        this.numberUser = numberUser;
        this.addressUser = addressUser;
        this.classUser = classUser;
        this.imageURl = imageURl;
        this.token = token;
        this.listChat = listChat;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getNumberUser() {
        return numberUser;
    }

    public void setNumberUser(String numberUser) {
        this.numberUser = numberUser;
    }

    public String getAddressUser() {
        return addressUser;
    }

    public void setAddressUser(String addressUser) {
        this.addressUser = addressUser;
    }

    public String getClassUser() {
        return classUser;
    }

    public void setClassUser(String classUser) {
        this.classUser = classUser;
    }

    public String getImageURl() {
        return imageURl;
    }

    public void setImageURl(String imageURl) {
        this.imageURl = imageURl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getListChat() {
        return listChat;
    }

    public void setListChat(String listChat) {
        this.listChat = listChat;
    }
}
