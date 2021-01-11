package com.example.sports;

import java.util.HashMap;

public class Receipt {
    private String image,sport,groundId,timestamp,bookedFor;
    private String cost;

    public Receipt() {
    }

    public Receipt(HashMap<String,Object> data) {
        this.image = (String) data.get("image");
        this.sport = (String) data.get("sport");
        this.groundId = (String) data.get("groundId");
        this.timestamp = (String) data.get("timestamp");
        this.cost = (String) data.get("cost");
        this.bookedFor = (String) data.get("bookedFor");
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getGroundId() {
        return groundId;
    }

    public void setGroundId(String groundId) {
        this.groundId = groundId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getBookedFor() {
        return bookedFor;
    }

    public void setBookedFor(String bookedFor) {
        this.bookedFor = bookedFor;
    }
}
