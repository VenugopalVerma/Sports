package com.example.sports;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Ground implements Serializable {
    private String name,docId;
    private float rating;
    private String image;
    private List<String> sportsPlayed;
    private Map<String,Object> address;

    public Ground(String name, Map<String, Object> address, float rating) {
        this.name = name;
        this.address = address;
        this.rating = rating;

    }

    public Ground() {
    }

    public Ground(String name, Map<String, Object> address, float rating, String image) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.image = image;
    }

    public List<String> getSportsPlayed() {
        return sportsPlayed;
    }

    public void setSportsPlayed(List<String> sportsPlayed) {
        this.sportsPlayed = sportsPlayed;
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

    public Map<String, Object> getAddress() {
        return address;
    }

    public void setAddress(Map<String, Object> address) {
        this.address = address;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
