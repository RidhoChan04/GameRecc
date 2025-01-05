/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Victus
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.review;
public class Game {
   
    private int GameID;
    private String Name;
    private String Genre;
    private String Device;
    private double Price;
    private double Rating;
    private int age;
    private String PosterGame;
    private Date Date;
    private List<review> reviews;  
    private String deskripsi;

    public Game(int GameID, String Name, String Genre, String Device, double Price, String PosterGame, Date Date,int age, double Rating) {
        this.GameID = GameID;
        this.Name = Name;
        this.Genre = Genre;
        this.Device = Device;
        this.Price = Price;
        this.PosterGame = PosterGame;
        this.Date = Date;
        this.age=age;
        this.Rating=Rating;
    }

    public int getGameID() {
        return GameID;
    }

    public void setGameID(int GameID) {
        this.GameID = GameID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String Genre) {
        this.Genre = Genre;
    }

    public String getDevice() {
        return Device;
    }

    public void setDevice(String Device) {
        this.Device = Device;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double Rating) {
        this.Rating = Rating;
    }

    public String getPosterGame() {
        return PosterGame;
    }

    public void setPosterGame(String PosterGame) {
        this.PosterGame = PosterGame;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    public List<review> getReviews() {
        return reviews;
    }

    public void setReviews(List<review> reviews) {
        this.reviews = reviews;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getAge() {
        return age;
    }

    public String getDeskripsi() {
        return deskripsi;
    }
}
