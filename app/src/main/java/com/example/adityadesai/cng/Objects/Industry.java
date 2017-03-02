package com.example.adityadesai.cng.Objects;

/**
 * Created by adityadesai on 11/02/17.
 */

public class Industry {

    private String industryName;
    private int industryId;

    public Industry(String name, int id){
        industryName=name;
        industryId=id;
    }

    public String getName(){
        return industryName;
    }
    public int getId(){
        return industryId;
    }
}
