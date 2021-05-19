package com.example.polycustomer.event;

import com.example.polycustomer.Model.Canteen;
import com.example.polycustomer.Model.Product;

public interface callBack {
    void addChatFirbase(Canteen canteen);
    void productCallBack(Product product);
}
