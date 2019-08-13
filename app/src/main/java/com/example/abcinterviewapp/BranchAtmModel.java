package com.example.abcinterviewapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class BranchAtmModel {

    private int id;
    private String name,address,phone,email,website,type;
    private LatLng location;
    private ArrayList<WorkingHours> workingHours;

    public BranchAtmModel() {
    }

    public ArrayList<WorkingHours> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(ArrayList<WorkingHours> workingHours) {
        this.workingHours = workingHours;
    }

    public BranchAtmModel(int id, String name, String address, String email, String website, String type, LatLng location) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.website = website;
        this.type = type;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
