<%-- 
    Document   : homepage
    Created on : 20 Nov 2024, 20.17.47
    Author     : Victus
    ok
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="model.Game"%>
<%@page import="model.review"%>
<%@page import="model.User"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>GameRecc</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #002b40;
      color: white;
      margin: 0;
      padding: 0;
      display: flex;
      flex-direction: column;
      min-height: 100vh;
    }
    
    .main-content {
      flex: 1;
      padding: 2rem 0;
      background-color: #002b40;
    }
    
    .game-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
      gap: 1.5rem;
      padding: 0 1rem;
      max-width: 1200px;
      margin: 0 auto;
    }
    
    .game-card {
      background-color: #00334d;
      border-radius: 8px;
      overflow: hidden;
      transition: transform 0.3s ease;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }
    
    .game-card:hover {
      transform: translateY(-5px);
    }
    
    .game-card img {
      width: 100%;
      height: 250px;
      object-fit: cover;
    }
    
    .game-card-title {
      padding: 0.75rem;
      font-weight: bold;
      text-align: center;
      white-space: nowrap;
      overflow: hidden;
      text-decoration: none !important;
      color: white !important;
    }
    
    footer {
      background-color: #00334d;
      color: white;
      text-align: center;
      padding: 10px;
      margin-top: auto;
    }

    a {
      text-decoration: none;
      color: white;
    }

    .no-games {
      text-align: center;
      font-size: 1.2rem;
      margin-top: 2rem;
    }
  </style>
</head>
<body>
  <%@ include file="navbar.jsp" %>
  <%  
    List<Game> games = (List<Game>)request.getSession().getAttribute("games");   
    List<Game> filteredGame = (List<Game>)request.getSession().getAttribute("filteredGame");
    List<Game> sortedGame = (List<Game>)request.getSession().getAttribute("sortedGame");
    List<Game> displayGame = new ArrayList<>();
    if (games == null || games.isEmpty()) {
        games = (List<Game>) request.getSession().getAttribute("games");
    }
    if (filteredGame != null) {
      displayGame = filteredGame;
      if (sortedGame != null) {
        displayGame = sortedGame;
      }  
    } else {
      displayGame = games;   
      if (sortedGame != null) {
        displayGame = sortedGame;
      }
    }
  %>
  <div class="main-content">
  <div class="game-grid">
    <% if (displayGame != null && !displayGame.isEmpty()) { %>
      <% for (Game game : displayGame) { %>
        <a href="/Game?action=displaySingleGame&id=<%=game.getGameID()%>" style="text-decoration: none;">  
          <div class="game-card">
            <img src="<%= request.getContextPath() %>/<%= game.getPosterGame() %>" alt="Game Image">
            <div class="game-card-title"><%= game.getName() %></div>
            <p class="text-center" style="color: white;">Genre: <%= game.getGenre() %></p>
            <p class="text-center" style="color: white;">Rating: <%= game.getRating() %> ★</p>
          </div>
        </a>
      <% } %>
    <% } else { %>
      <p class="no-games">Tidak ada game untuk ditampilkan saat ini.</p>
    <% } %>
  </div>
</div>

  <footer class="text-center p-3">
    &copy; 2024 GameRecc. All rights reserved.
  </footer>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
