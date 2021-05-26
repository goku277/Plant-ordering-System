package com.goku277.plantorder.ProductModel;

public class SetSoilAndFertilizerData {
    private String name, price, imageUrl, seedsQty, id, category, details, quantity;

    public SetSoilAndFertilizerData(String name, String price, String imageUrl, String seedsQty, String id, String category, String details, String quantity) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.seedsQty = seedsQty;
        this.id= id;
        this.category = category;
        this.details = details;
        this.quantity = quantity;
    }

    public SetSoilAndFertilizerData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSeedsQty() {
        return seedsQty;
    }

    public void setSeedsQty(String seedsQty) {
        this.seedsQty = seedsQty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}