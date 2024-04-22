package com.example.test;

public class TableModel {
    private String gdID;
    private String gdName;
    private String gdPrice;
    private String gdNumber;
    private String gdGroup;
    private String gdStoreName;


    public TableModel(String gdID, String gdName, String gdPrice, String gdNumber, String gdGroup, String gdStoreName) {
        this.gdID = gdID;
        this.gdName = gdName;
        this.gdPrice = gdPrice;
        this.gdNumber = gdNumber;
        this.gdGroup = gdGroup;
        this.gdStoreName = gdStoreName;
    }


    public String getGdID() {
        return gdID;
    }

    public String getGdName() {
        return gdName;
    }

    public String getGdPrice() {
        return gdPrice;
    }

    public String getGdNumber() {
        return gdNumber;
    }

    public String getGdGroup() {
        return gdGroup;
    }
    public String getGdStoreName() {
        return gdStoreName;
    }

}
