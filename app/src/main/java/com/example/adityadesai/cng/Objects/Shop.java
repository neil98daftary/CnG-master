package com.example.adityadesai.cng.Objects;

/**
 * Created by adityadesai on 13/02/17.
 */

public class Shop {
    private String shopName;
    private String shopAddress;
    private String shopPhone;

    public Shop(String name, String address, String phone){
        shopName=name;
        shopAddress=address;
        shopPhone=phone;
    }

    public String getShopName(){
        return shopName;
    }

    public String getShopAddress(){
        return shopAddress;
    }

    public String getShopPhone(){
        return shopPhone;
    }
}
