package com.example.shoehub.feedback_u;

public class FeedBackClass {

    String feedbackID;
    String brand;
    String type;
    String description;


    public FeedBackClass() {
    }

    public FeedBackClass(String feedbackID, String brand, String type, String description) {
        this.feedbackID = feedbackID;
        this.brand = brand;
        this.type = type;
        this.description = description;
    }

    public String getFeedbackID() {
        return feedbackID;
    }

    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
