package com.example.android.ecommerce.classesInfo;

public class Order {
    private String Name;
    private String Description;
    private String Price;
    private String Date;
    private String Time;
    private String image_uri;



    public Order() {
    }

    public Order(String name, String description, String price, String date, String time, String image_uri) {
        Name = name;
        Description = description;
        Price = price;
        Date = date;
        Time = time;
        this.image_uri = image_uri;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }
}
