package com.goku277.plantorder.ProductModel;

public class SetProductDetails {
    private String name, price, details, category, id, imageUrl, quantity;

    public SetProductDetails(String name, String price, String details, String category, String id, String imageUrl, String quantity) {
        this.name = name;
        this.price = price;
        this.details = details;
        this.category = category;
        this.id = id;
        this.imageUrl= imageUrl;
        this.quantity= quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public SetProductDetails() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}