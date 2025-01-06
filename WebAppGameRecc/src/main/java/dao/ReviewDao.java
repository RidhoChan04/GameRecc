/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author acer
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.review;

public class ReviewDao {

   private final String url = "jdbc:mysql://localhost:3306/dbmy";
    private final String dbUser = "root";
    private final String dbPassword = "ridho";

    // Method to add a review
    public boolean addReview(int gameID, int userID, double rating, String comment) {
        String query = "INSERT INTO review (User_userID, Game_GameID, Komen, Rating) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userID);
            stmt.setInt(2, gameID);
            stmt.setString(3, comment);
            stmt.setDouble(4, rating);
            

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<review> getReviewsByGameID(int GameID) {
        List<review> reviews = new ArrayList<>();
        String query = "SELECT * FROM review WHERE Game_GameID = ? ORDER BY Rating DESC";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, GameID);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                review review = new review(
                    rs.getInt("ReviewID"),
                    rs.getString("Komen"),
                    rs.getDouble("Rating"));
                reviews.add(review);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews;
    }
}

