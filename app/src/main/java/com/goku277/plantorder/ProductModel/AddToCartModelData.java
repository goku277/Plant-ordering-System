package com.goku277.plantorder.ProductModel;

public class AddToCartModelData {
    private String imageUrl, price, name, qty;

    public AddToCartModelData(String imageUrl, String price, String name, String qty) {
        this.imageUrl = imageUrl;
        this.price = price;
        this.name = name;
        this.qty= qty;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}