package com.example.adityadesai.cngcustomer.Objects;

/**
 * Created by adityadesai on 17/02/17.
 */

public class ItemDetail{

    private String itemName;
    private String itemPrice;
    private String itemDescription;
    private String itemUrl;


    public ItemDetail(String type, String price, String description, String Url){

        itemName=type;
        itemPrice=price;
        itemDescription=description;
        itemUrl = Url;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemUrl() {return itemUrl;}
}
