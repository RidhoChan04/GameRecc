/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Victus
 */
public class review {
    private int reviewID;
    private String komen;
    private double rating;
    private Date date;

    public review(int reviewID, String komen, double rating) {
        this.reviewID = reviewID;
        this.komen = komen;
        this.rating = rating;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = (int) reviewID;
    }

    public void setKomen(String komen) {
        this.komen = komen;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getReviewID() {
        return reviewID;
    }

    public String getKomen() {
        return komen;
    }

    public double getRating() {
        return rating;
    }
    
    
}
