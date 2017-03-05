package com.example.adityadesai.cng.Objects;

/**
 * Created by adityadesai on 11/02/17.
 */

public class Industry {

    private String industryName;
    private long industryId;

    public Industry(){}

    public Industry(String name, Long id){
        industryName=name;
        industryId=id;
    }

    public String getName(){
        return industryName;
    }
    public Long getId(){
        return industryId;
    }
}
