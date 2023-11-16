package com.example.itemxplorerteam1;

public class ModalItems {
    private String productName;
    private String productCategory;
    private String productPrice;
    private String productBarCode;

    private String productLocation;


    public ModalItems() {

    }

    public ModalItems(String productName, String itemcategory, String productPrice, String productBarCode, String productLocation){

        this.productLocation = productLocation;
        this.productName = productName;
        this.productCategory =itemcategory;
        this.productPrice = productPrice;
        this.productBarCode = productBarCode;
    }

    public String getProductLocation() {
        return productLocation;
    }
    public String getProductName() {
        return productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductBarCode() {
        return productBarCode;
    }
}

