package com.example.android.ecommerce.classesInfo;

public class Category {

   private String Name;
   private String Description;
   private String image_uri;

    public Category() {
    }

    public Category(String name, String description, String image_uri) {
        Name = name;
        Description = description;
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

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }
}
