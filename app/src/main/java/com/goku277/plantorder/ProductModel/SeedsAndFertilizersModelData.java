package com.goku277.plantorder.ProductModel;

public class SeedsAndFertilizersModelData {
    private String name, price, detail, seedqty, category, imageUrl, quantity;

    public SeedsAndFertilizersModelData(String name, String price, String detail, String seedqty, String category, String imageUrl, String quantity) {
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.seedqty = seedqty;
        this.category = category;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public SeedsAndFertilizersModelData() {
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSeedqty() {
        return seedqty;
    }

    public void setSeedqty(String seedqty) {
        this.seedqty = seedqty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}