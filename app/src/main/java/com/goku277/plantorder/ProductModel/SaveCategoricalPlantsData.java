package com.goku277.plantorder.ProductModel;

public class SaveCategoricalPlantsData {
    private String name, price, detail, category, imageUri, quantity;

    public SaveCategoricalPlantsData(String name, String price, String detail, String category, String imageUri, String quantity) {
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.category = category;
        this.imageUri = imageUri;
        this.quantity= quantity;
    }

    public SaveCategoricalPlantsData() {

    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}