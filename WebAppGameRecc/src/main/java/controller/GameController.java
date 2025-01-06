/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.GameDao;
import dao.ReviewDao;
import dao.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import model.Game;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.review;
import model.User;
import java.util.Comparator;

/**
 *
 * @author acer
 */
@WebServlet(name = "GameController", urlPatterns = {"/Game"})
@MultipartConfig
public class GameController extends HttpServlet {

    private final GameDao gameDao = new GameDao();
    private final ReviewDao ReviewDao = new ReviewDao();
    private final UserDao userDao = new UserDao();
    private final String uploadDirectory = "C:/uploaded_images"; // Change to your preferred upload directory

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Game> games = gameDao.getAllGame();
            request.getSession().setAttribute("games", games);

        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        String action = request.getParameter("action");
        if (action == null) {
            response.getWriter().println("No action specified.");
            return;
        }

        if (action.equals("home")) {
            request.getSession().setAttribute("filteredGame", null);
            request.getSession().setAttribute("sortedGame", null);
            response.sendRedirect("/views/homepage.jsp");
        } else if (action.equals("displaySingleGame")) {
            String ID = request.getParameter("id");
            int id = Integer.parseInt(ID);
            try {
                Game game = gameDao.getGameById(id);
                List<review> reviews = new ArrayList<>();
                reviews = ReviewDao.getReviewsByGameID(id);
                game.setReviews(reviews);
                response.getWriter().print(123);
                request.getSession().setAttribute("singleGame", game);
            } catch (SQLException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.sendRedirect("/views/Review.jsp");
        } else if (action.equals("DisplayGameEdit")) {
            String ID = request.getParameter("id");
            int id = Integer.parseInt(ID);
            try {
                Game game = gameDao.getGameById(id);
                List<review> reviews = new ArrayList<>();
                reviews = ReviewDao.getReviewsByGameID(id);
                game.setReviews(reviews);

                request.getSession().setAttribute("Game", game);
            } catch (SQLException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.sendRedirect("/views/mengubahGame.jsp");
        } else if (action.equals("searchGame")) {
            request.getSession().setAttribute("sortedGame", null);
            List<Game> filteredGame = searchGame(request, response);
            request.getSession().setAttribute("filteredGame", filteredGame);
            response.sendRedirect("/views/homepage.jsp");
        } else if (action.equals("sortAZ")) {
            try {
                List<Game> sortedGame = SortingGame(request, response, "AZ");
                response.getWriter().print(sortedGame.getFirst().getGameID());
                request.getSession().setAttribute("sortedGame", sortedGame);
                response.sendRedirect("/views/homepage.jsp");
            } catch (SQLException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (action.equals("sortZA")) {
            try {
                List<Game> sortedGame = SortingGame(request, response, "ZA");
                response.getWriter().print(sortedGame.getFirst().getGameID());
                request.getSession().setAttribute("sortedGame", sortedGame);
                response.sendRedirect("/views/homepage.jsp");
            } catch (SQLException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (action.equals("sortTOP")) {
            try {
                List<Game> sortedGame = SortingGame(request, response, "TOP");
                response.getWriter().print(sortedGame.getFirst().getGameID());
                request.getSession().setAttribute("sortedGame", sortedGame);
                response.sendRedirect("/views/homepage.jsp");
            } catch (SQLException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (action.equals("Genre")) {
            try {
                request.getSession().setAttribute("sortedGame", null);
                List<Game> GenreGame = Genre(request, response);
                request.getSession().setAttribute("sortedGame", GenreGame);
                response.sendRedirect("/views/homepage.jsp");
            } catch (SQLException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (action.equals("Device")) {
            try {
                request.getSession().setAttribute("sortedGame", null);
                List<Game> DeviceGame = Device(request, response);
                request.getSession().setAttribute("sortedGame", DeviceGame);
                response.sendRedirect("/views/homepage.jsp");
            } catch (SQLException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (action.equals("Harga")) {
            try {
                request.getSession().setAttribute("sortedGame", null);
                List<Game> SortPrice = Price(request, response);
                request.getSession().setAttribute("sortedGame", SortPrice);
                response.sendRedirect("/views/homepage.jsp");
            } catch (SQLException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action.equals("addReview")) {
            String ID = request.getParameter("idGame");
            int id = Integer.parseInt(ID);
            User user = (User) request.getSession().getAttribute("user");
            String rating = request.getParameter("rating");
            double rate = Double.parseDouble(rating);
            String review = request.getParameter("review");
            boolean cekReview = ReviewDao.addReview(id, user.getUserID(), rate, review);

            if (cekReview) {
                try {
                    gameDao.updateAllGameRatings(); // Perbarui semua rating game
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                response.sendRedirect(request.getContextPath() + "/Game?action=displaySingleGame&id=" + ID);
            }

        } else if (action.equals("addGame")) {
            AddGame(request, response);
        } else if (action.equals("deleteGame")) {
            DeleteGame(request, response);
        } else if (action.equals("editGame")) {
            EditGame(request, response);
        }

    }

    protected void AddGame(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String genre = request.getParameter("genre");
        String device = request.getParameter("device");
        String price = request.getParameter("price");
        String age = request.getParameter("age");
        String description = request.getParameter("description");
        String dateString = request.getParameter("date");

        double Price = Double.parseDouble(price);
        int Age = Integer.parseInt(age);
        double Rating = 0;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // Match HTML date format
        Date releaseDate = null;
        try {
            releaseDate = formatter.parse(dateString);
        } catch (ParseException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Handle the uploaded poster image
        Part filePart = request.getPart("poster");
        String fileName = filePart.getSubmittedFileName();
        String filePath = uploadDirectory + File.separator + fileName;

        // Save the file to the server
        File fileSaveDir = new File(uploadDirectory);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs(); // Create the directory if it does not exist
        }
        filePart.write(filePath);

        // Relative path to store in the database
        String relativePath = "uploaded_images/" + fileName;

        boolean gameAdded = false;

        try {
            gameAdded = gameDao.addGame(name, genre, device, Price, Age, Rating, releaseDate, relativePath, description);
        } catch (SQLException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (gameAdded) {

            response.sendRedirect("/Game?action=home"); 

        } else {

            response.getWriter().println("Gagal menambahkan Game.");
        }
    }

    protected void DeleteGame(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
       

        boolean DeletGame = false;

        try {
            DeletGame = gameDao.deleteGame(Integer.parseInt(id));
        } catch (SQLException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (DeletGame) {
            response.sendRedirect("/Game?action=home");
        } else {
            request.setAttribute("errorM", "Game Tersebut Tidak Ditemukan");
            request.getRequestDispatcher("/views/menghapusgame.jsp").forward(request, response);
        }

    }

    protected void EditGame(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String genre = request.getParameter("genre");
        String device = request.getParameter("device");
        String price = request.getParameter("price");
        String age = request.getParameter("age");
        String description = request.getParameter("description");
        String dateString = request.getParameter("date");
        String ID = request.getParameter("id");
        int id = Integer.parseInt(ID);

        double Price = Double.parseDouble(price);
        int Age = Integer.parseInt(age);
        double Rating = 0;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // Match HTML date format
        Date releaseDate = null;

        try {
            releaseDate = formatter.parse(dateString);
        } catch (ParseException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Handle the uploaded poster image
        Part filePart = request.getPart("poster");
        String fileName = filePart.getSubmittedFileName();
        String filePath = uploadDirectory + File.separator + fileName;

        // Save the file to the server
        File fileSaveDir = new File(uploadDirectory);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs(); // Create the directory if it does not exist
        }
        filePart.write(filePath);

        // Relative path to store in the database
        String relativePath = "uploaded_images/" + fileName;

        boolean ubahGame = false;

        try {
            ubahGame = gameDao.updateGame(id, name, genre, device, Price, Age, Rating, releaseDate, relativePath, description);
        } catch (SQLException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (ubahGame) {

            response.sendRedirect("/Game?action=home");

        } else {
            response.getWriter().print(id);
            response.getWriter().println("Gagal mengubah game.");
        }

    }

    protected List<Game> searchGame(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");

        List<Game> filteredGames = gameDao.searchGameByTitle(query);

        return filteredGames;

    }

    protected List<Game> SortingGame(HttpServletRequest request, HttpServletResponse response, String pil) throws ServletException, IOException, SQLException {
        List<Game> SortedGames = null;
        if (pil.equals("AZ")) {
            SortedGames = gameDao.getAllGameSortedAZ();
        } else if (pil.equals("ZA")) {
            SortedGames = gameDao.getAllGameSortedZA();
        } else if (pil.equals("TOP")) {
            SortedGames = gameDao.getAllGameSortedTOP();
        }
        return SortedGames;
    }

    protected List<Game> Genre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String genre = request.getParameter("genre");
        List<Game> GenreGames = gameDao.GenreSort(genre);

        return GenreGames;
    }

    protected List<Game> Device(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String device = request.getParameter("device");
        List<Game> GenreGames = gameDao.DeviceSort(device);

        return GenreGames;
    }
    
    protected List<Game> Price(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String harga = request.getParameter("harga");
        List<Game> GenreGames = gameDao.PriceSort(harga);

        return GenreGames;
    }
}
