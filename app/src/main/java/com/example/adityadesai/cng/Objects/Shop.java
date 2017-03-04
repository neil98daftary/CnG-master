package com.example.adityadesai.cng.Objects;

/**
 * Created by adityadesai on 13/02/17.
 */

public class Shop {
    private String shopName;
    private String shopAddress;
    private String shopPhone;
    private String shop_id;

    public Shop(String name, String address, String phone,String id){
        shopName=name;
        shopAddress=address;
        shopPhone=phone;
        shop_id = id;
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

    public String getShop_id() {return shop_id;}
}
