package com.example.android.ecommerce.classesInfo;

public class Product {

    private String Name,Description,Price, image_uri,Category;


    public Product() {
    }

    public Product(String name, String description, String price, String image_uri, String category) {
        Name = name;
        Description = description;
        Price = price;
        this.image_uri = image_uri;
        Category = category;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
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

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }
}
