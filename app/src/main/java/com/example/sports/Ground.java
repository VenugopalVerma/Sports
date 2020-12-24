package com.example.sports;

import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Ground {
    private String name,address;
    private float rating;
    private String image;
    private HashMap<String, Object> data;

    public Ground(String name, String address, float rating) {
        this.name = name;
        this.address = address;
        this.rating = rating;

    }

    public Ground() {
    }

    public Ground(String name, String address, float rating, String image) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
}
