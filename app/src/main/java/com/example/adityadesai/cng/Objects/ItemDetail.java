package com.example.adityadesai.cng.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adityadesai on 17/02/17.
 */

public class ItemDetail{

    private String itemName;
    private String itemPrice;
    private String itemDescription;


    public ItemDetail(String type, String price, String description){

        itemName=type;
        itemPrice=price;
        itemDescription=description;
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
}
