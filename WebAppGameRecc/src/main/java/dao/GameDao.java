/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;
import model.Game;
import java.sql.*;
import java.util.stream.Collectors;

/**
 *
 * @author acer
 */
public class GameDao {

    public GameDao() {
    }
    ;
    
    private final String url = "jdbc:mysql://localhost:3306/dbmy";
    private final String dbUser = "root";
    private final String dbPassword = "ridho";
    
    public boolean addGame(String name, String genre, String device, double Price, int age, double rating, java.util.Date Date, String PosterGame, String Deskripsi) throws SQLException {
        String sql = "INSERT INTO game (Name, Genre , Device ,Price ,Age,Rating,Date,PosterGame,Deskripsi) VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found: " + e.getMessage());
        }
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, genre);
            stmt.setString(3, device);
            stmt.setDouble(4, Price);
            stmt.setInt(5, age);
            stmt.setDouble(6, rating);
            java.sql.Date releaseDateSql = new java.sql.Date(Date.getTime()); // Convert to java.sql.Date
            stmt.setDate(7, releaseDateSql);
            stmt.setString(8, PosterGame);
            stmt.setString(9, Deskripsi);

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateGame(int GameID, String name, String genre, String device, double Price, int age, double rating, java.util.Date Date, String PosterGame, String deskripsi) throws SQLException {
        String sql = "UPDATE game SET Name=?, Genre=?, Device =?,Price =? ,Age=?,Rating=?,Date=?,PosterGame=?, Deskripsi=? WHERE GameID = ?";
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, genre);
            stmt.setString(3, device);
            stmt.setDouble(4, Price);
            stmt.setInt(5, age);
            stmt.setDouble(6, rating);
            java.sql.Date releaseDateSql = new java.sql.Date(Date.getTime()); // Convert to java.sql.Date
            stmt.setDate(7, releaseDateSql);
            stmt.setString(8, PosterGame);
            stmt.setString(9, deskripsi);
            stmt.setInt(10, GameID);

            return stmt.executeUpdate() > 0; 
        }
    }

    public boolean deleteGame(int id) throws SQLException {
        String sql = "DELETE FROM game WHERE GameID=?";
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            

            return stmt.executeUpdate() > 0; 
        }
    }

    // Method to retrieve a game by ID
    public Game getGameById(int GameID) throws SQLException {
        String sql = "SELECT * FROM game WHERE GameID = ?";
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, GameID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                String Name = rs.getString("Name");
                String Genre = rs.getString("Genre");
                String Device = rs.getString("Device");
                Double Price = rs.getDouble("Price");
                int Age = rs.getInt("Age");
                Double Rating = rs.getDouble("Rating");
                Date Date = rs.getDate("Date");
                String PosterGame = rs.getString("PosterGame");
                String Deskripsi = rs.getString("Deskripsi");
                Game game = new Game(GameID, Name, Genre, Device, Price, PosterGame, Date, Age, Rating);
                game.setDeskripsi(Deskripsi);
                game.setRating(Rating);
                return (game);
            }
        }
        return null; // Returns null if no game is found with the given ID
    }

    // Method to retrieve all game
    public List<Game> getAllGame() throws SQLException {
        List<Game> game = new ArrayList<>();
        String sql = "SELECT * FROM game";
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int GameID = rs.getInt("GameID");

                String Name = rs.getString("Name");
                String Genre = rs.getString("genre");
                String Device = rs.getString("Device");
                Double Price = rs.getDouble("Price");
                int Age = rs.getInt("Age");
                Date Date = rs.getDate("Date");
                String PosterGame = rs.getString("PosterGame");
                Double Rating = rs.getDouble("Rating");
                game.add(new Game(GameID, Name, Genre, Device, Price, PosterGame, Date, Age, Rating));

            }
        }
        return game;
    }

    public List<Game> searchGameByTitle(String title) {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM game WHERE Name LIKE ?";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + title + "%"); // Use % for partial matching
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int GameID = rs.getInt("GameID");

                String Name = rs.getString("Name");
                String Genre = rs.getString("Genre");
                String Device = rs.getString("Device");
                Double Price = rs.getDouble("Price");
                int Age = rs.getInt("Age");
                Date Date = rs.getDate("Date");
                String PosterGame = rs.getString("PosterGame");
                String Deskripsi = rs.getString("Deskripsi");
                Double Rating = rs.getDouble("Rating");

                Game game = new Game(GameID, Name, Genre, Device, Price, PosterGame, Date, Age, Rating);
                game.setDeskripsi(Deskripsi);
                games.add(game);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (title.isEmpty()) {
            return null;
        }

        return games;
    }

    public List<Game> getAllGameSortedAZ() throws SQLException {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM game order by Name ASC";
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int GameID = rs.getInt("GameID");

                String Name = rs.getString("Name");
                String Genre = rs.getString("Genre");
                String Device = rs.getString("Device");
                Double Price = rs.getDouble("Price");
                int Age = rs.getInt("Age");
                Date Date = rs.getDate("Date");
                String PosterGame = rs.getString("PosterGame");
                String deskripsi = rs.getString("Deskripsi");
                Double Rating = rs.getDouble("Rating");
                Game game = new Game(GameID, Name, Genre, Device, Price, PosterGame, Date, Age, Rating);
                game.setDeskripsi(deskripsi);
                games.add(game);

            }
        }
        return games;
    }

    public List<Game> getAllGameSortedZA() throws SQLException {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM game order by Name DESC";
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int GameID = rs.getInt("GameID");

                String Name = rs.getString("Name");
                String Genre = rs.getString("Genre");
                String Device = rs.getString("Device");
                Double Price = rs.getDouble("Price");
                int Age = rs.getInt("Age");
                Date Date = rs.getDate("Date");
                String PosterGame = rs.getString("PosterGame");
                String deskripsi = rs.getString("Deskripsi");
                Double Rating = rs.getDouble("Rating");
                Game game = new Game(GameID, Name, Genre, Device, Price, PosterGame, Date, Age, Rating);
                game.setDeskripsi(deskripsi);
                games.add(game);

            }
        }
        return games;
    }

    public List<Game> getAllGameSortedTOP() throws SQLException {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM game order by Rating DESC";
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int GameID = rs.getInt("GameID");

                String Name = rs.getString("Name");
                String Genre = rs.getString("Genre");
                String Device = rs.getString("Device");
                Double Price = rs.getDouble("Price");
                int Age = rs.getInt("Age");
                Date Date = rs.getDate("Date");
                String PosterGame = rs.getString("PosterGame");
                String deskripsi = rs.getString("Deskripsi");
                Double Rating = rs.getDouble("Rating");
                Game game = new Game(GameID, Name, Genre, Device, Price, PosterGame, Date, Age, Rating);
                game.setDeskripsi(deskripsi);
                games.add(game);

            }
        }
        return games;
    }

    public List<Game> GenreSort(String genre) {
        List<Game> games = new ArrayList<>();

        String sql = "SELECT * FROM game WHERE Genre = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, genre);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int GameID = rs.getInt("GameID");

                String Name = rs.getString("Name");
                String Genre = rs.getString("Genre");
                String Device = rs.getString("Device");
                Double Price = rs.getDouble("Price");
                int Age = rs.getInt("Age");
                Date Date = rs.getDate("Date");
                String PosterGame = rs.getString("PosterGame");
                String Deskripsi = rs.getString("Deskripsi");
                Double Rating = rs.getDouble("Rating");

                Game game = new Game(GameID, Name, Genre, Device, Price, PosterGame, Date, Age, Rating);
                game.setDeskripsi(Deskripsi);
                games.add(game);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (genre.isEmpty()) {
            return null;
        }

        return games;
    }

    public List<Game> DeviceSort(String device) {
        List<Game> games = new ArrayList<>();

        String sql = "SELECT * FROM game WHERE Device = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, device);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int GameID = rs.getInt("GameID");

                String Name = rs.getString("Name");
                String Genre = rs.getString("Genre");
                String Device = rs.getString("Device");
                Double Price = rs.getDouble("Price");
                int Age = rs.getInt("Age");
                Date Date = rs.getDate("Date");
                String PosterGame = rs.getString("PosterGame");
                String Deskripsi = rs.getString("Deskripsi");
                Double Rating = rs.getDouble("Rating");

                Game game = new Game(GameID, Name, Genre, Device, Price, PosterGame, Date, Age, Rating);
                game.setDeskripsi(Deskripsi);
                games.add(game);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (device.isEmpty()) {
            return null;
        }

        return games;
    }

    public List<Game> PriceSort(String harga) {
        List<Game> games = new ArrayList<>();
        
        String sql = null;
        if (harga.equals("Free")){
            sql = "SELECT * FROM game WHERE Price = 0";
        }else if (harga.equals("Above100")) {
            sql = "SELECT * FROM game WHERE Price > 100000";
        }else if (harga.equals("Under100")) {
            sql = "SELECT * FROM game WHERE Price > 0 AND Price <= 100000";
        }


        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword); PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int GameID = rs.getInt("GameID");

                String Name = rs.getString("Name");
                String Genre = rs.getString("Genre");
                String Device = rs.getString("Device");
                Double Price = rs.getDouble("Price");
                int Age = rs.getInt("Age");
                Date Date = rs.getDate("Date");
                String PosterGame = rs.getString("PosterGame");
                String Deskripsi = rs.getString("Deskripsi");
                Double Rating = rs.getDouble("Rating");

                Game game = new Game(GameID, Name, Genre, Device, Price, PosterGame, Date, Age, Rating);
                game.setDeskripsi(Deskripsi);
                games.add(game);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (harga.isEmpty()) {
            return null;
        }

        return games;
    }

    public void updateAllGameRatings() throws SQLException { // update rating 
        String query = "UPDATE game g "
                + "LEFT JOIN ("
                + "    SELECT Game_GameID, ROUND(AVG(Rating), 1) AS avgRating "
                + "    FROM review "
                + "    GROUP BY Game_GameID"
                + ") r ON g.GameID = r.Game_GameID "
                + "SET g.Rating = IFNULL(r.avgRating, 0)";
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.executeUpdate(); // Jalankan query
        }
    }

}
