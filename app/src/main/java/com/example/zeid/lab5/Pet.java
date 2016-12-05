package com.example.zeid.lab5;

import java.util.Date;

/**
 * Created by zeid on 01/12/16.
 */
public class Pet {
    public String date;
    public String name;
    public String type;
    public String breed;
    public String gender;
    public int age;
    public String link;
    public int price;
    public String ownerName;
    public String ownerNumber;


    public Pet(String date, String name, String type, String breed, int age , String gender, String link, int price, String ownerName, String ownerNumber){
        this.date = date;
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.gender = gender;
        this.age = age;
        this.link = link;
        this.price = price;
        this.ownerName = ownerName;
        this.ownerNumber = ownerNumber;
    }
}
