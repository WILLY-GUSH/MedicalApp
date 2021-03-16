package com.example.medicalapp;

public class ModelShop {
    private String name, shopName, ImageShop, registerNumber, AccountType, phone, email, deliveryFee, country, state, city, address, latitude, longitude, shopOpen, Uid, timestamp, online;

    public ModelShop() {
    }

    public ModelShop(String name, String shopName, String imageShop, String registerNumber, String phone, String email, String deliveryFee, String country, String state, String city, String address, String latitude, String longitude, String shopOpen, String uid, String timestamp, String online, String accountType) {
        this.name = name;
        this.shopName = shopName;
        ImageShop = imageShop;
        this.registerNumber = registerNumber;
        this.AccountType = accountType;
        this.phone = phone;
        this.email = email;
        this.deliveryFee = deliveryFee;
        this.country = country;
        this.state = state;
        this.city = city;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.shopOpen = shopOpen;
        Uid = uid;
        this.timestamp = timestamp;
        this.online = online;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getImageShop() {
        return ImageShop;
    }

    public void setImageShop(String imageShop) {
        ImageShop = imageShop;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String accountType) {
        AccountType = accountType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getShopOpen() {
        return shopOpen;
    }

    public void setShopOpen(String shopOpen) {
        this.shopOpen = shopOpen;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }
}