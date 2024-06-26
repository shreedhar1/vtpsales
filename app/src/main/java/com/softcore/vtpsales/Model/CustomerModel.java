package com.softcore.vtpsales.Model;

import com.google.gson.annotations.SerializedName;

public class CustomerModel {
    private String CardCode;
    private String CardName;
    private String CardType;
    private String Address;
    private String Street;
    private String Block;
    private String City;
    private String ZipCode;
    @SerializedName("ShipTo State")
    private String ShipToState;
    @SerializedName("ShipTo Country")
    private String ShipToCountry;
    private String SalesPerson;
    private String CollectionPerson;

    public String getSalesPerson() {
        return SalesPerson;
    }

    public void setSalesPerson(String salesPerson) {
        SalesPerson = salesPerson;
    }

    public String getCollectionPerson() {
        return CollectionPerson;
    }

    public void setCollectionPerson(String collectionPerson) {
        CollectionPerson = collectionPerson;
    }

    // Getters and setters
    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String cardCode) {
        CardCode = cardCode;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String cardName) {
        CardName = cardName;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getBlock() {
        return Block;
    }

    public void setBlock(String block) {
        Block = block;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getShipToState() {
        return ShipToState;
    }

    public void setShipToState(String shipToState) {
        ShipToState = shipToState;
    }

    public String getShipToCountry() {
        return ShipToCountry;
    }

    public void setShipToCountry(String shipToCountry) {
        ShipToCountry = shipToCountry;
    }


}

