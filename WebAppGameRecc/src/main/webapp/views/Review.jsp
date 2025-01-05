<%-- 
    Document   : Review
    Created on : 21 Nov 2024, 16.19.33
    Author     : Victus
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
        <title>Game Review</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #002b40;
                color: #e2e8f0;
            }

            .details-grid {
                display: grid;
                grid-template-columns: repeat(2, 1fr);
                gap: 2rem;
                margin-top: 1.5rem;
            }

            .detail-item {
                display: flex;
                align-items: center;
                gap: 0.75rem;
                font-size: 1.25rem;
                color: #e2e8f0;
                line-height: 1.6;
            }

            .detail-item i {
                color: #64ffda;
                font-size: 1.2rem;
            }

            .card-title {
                font-weight: bold;
                color: #64ffda;
                margin-bottom: 1rem;
            }

            .text-info {
                color: #48b9ff;
                font-weight: bold;
            }

            .text-success {
                color: #64ffda;
                font-weight: bold;
            }

            .text-light {
                color: #e2e8f0;
                font-weight: bold;
            }

            .card {
                background-color: rgba(17, 34, 64, 0.8) !important;
                color: #e2e8f0 !important;
            }
            .btn-primary {
                background-color: #64ffda;
                border-color: #64ffda;
                color: #0a192f;
            }
            .btn-primary:hover {
                background-color: #48b9ff;
                border-color: #48b9ff;
            }
            .error-message {
                background-color: rgba(220, 53, 69, 0.8);
                padding: 1rem;
                border-radius: 0.25rem;
                margin: 1rem 0;
            }
            .game-poster {
                width: 100%;
                height: 100%;
            }
            .poster-container {
                width: 50px;
                height: 500px;
                overflow: hidden;
            }
            .btn {
                border-radius: 10px;
                padding: 5px 15px;
                background-color: #8bc8bd !important;
                color: #00334d !important;
                font-weight: bold;
                transition: background-color 0.3s ease;
                margin-bottom: 20px;
            }
            .btn:hover {
                background-color: #6fa89c;
                color: white;
            }
        </style>
    </head>
    <body>
        <%@ include file="navbar.jsp" %>
        <div class="container py-5">
            <%            try {
                    Game game = (Game) request.getSession().getAttribute("singleGame");
                    User user = (User) request.getSession().getAttribute("user");

                    if (game == null) {
                        throw new Exception("Game data tidak ditemukan");
                    }

                    List<review> reviews = game != null ? game.getReviews() : null;
            %>
            <div class="card mb-4">
                <div class="row g-0">
                    <div class="col-md-4 poster-container">
                        <img src="${pageContext.request.contextPath}/<%= game.getPosterGame()%>" 
                             alt="<%= game.getName()%>" 
                             class="game-poster"
                             onerror="this.src='${pageContext.request.contextPath}/images/default-game.jpg'">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h1 class="card-title text-uppercase"><%= game.getName()%></h1>
                            <p class="card-text">
                                <i class="fas fa-star text-warning"></i> <%= game.getRating()%>
                            </p>
                            <div class="details-grid">
                                <div class="detail-item">
                                    <i class="fas fa-gamepad"></i> <span>Genre:</span>
                                    <span class="text-info"><%= game.getGenre()%></span>
                                </div>
                                <div class="detail-item">
                                    <i class="fas fa-laptop"></i> <span>Device:</span>
                                    <span class="text-info"><%= game.getDevice()%></span>
                                </div>
                                <div class="detail-item">
                                    <i class="fas fa-money-bill-wave"></i> <span>Harga:</span>
                                    <span class="text-success">Rp <%= game.getPrice()%></span>
                                </div>
                                <div class="detail-item">
                                    <i class="fas fa-calendar-alt"></i> <span>Rilis:</span>
                                    <span class="text-light"><%= game.getDate()%></span>
                                </div>
                            </div>
                            <div class="card mb-4">
                                <div class="card-body">
                                    <h5 class="card-title">Deskripsi</h5>
                                    <div class="card mb-3">
                                        <div class="card-body">
                                            <p class="card-text"><%= game.getDeskripsi()%></p>
                                        </div> 
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>   
            <%
                Boolean Admin = (Boolean) request.getSession().getAttribute("isAdmin");
                if (Admin != null && Admin) {
            %>
            <div >
                <form id="gameForm"  action="/Game" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="deleteGame">
                    <input type="hidden" name="id" value=<%= game.getGameID() %>>
                    
                    <button type="submit" class="btn">Hapus Game</button>
                </form> 
                <form action="/Game" method="get">
                    <input type="hidden" name="action" value="DisplayGameEdit">
                    <input type="hidden" name="id" value="<%=game.getGameID()%>">
                    <button type="submit" class="btn">Edit Game</button>
                </form>

            </div>
            <%
                }
            %>
            <div class="card mb-4">
                <div class="card-body">
                    <h2 class="card-title">Reviews</h2>
                    <% if (reviews != null && !reviews.isEmpty()) { %>
                    <% for (review review : reviews) {%>
                    <div class="card mb-3">
                        <div class="card-body">
                            <p class="card-text"><%= review.getKomen()%></p>
                            <p class="card-text"> Rating: <%= review.getRating()%> <i class="fas fa-star text-warning"></i></p>
                        </div>
                    </div>
                    <% } %>
                    <% } else { %>
                    <p class="card-text">Belum ada review untuk game ini</p>
                    <% } %>
                </div>
            </div>

            <% if (user != null) {%>
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Tambahkan Review</h5>
                    <form action="${pageContext.request.contextPath}/Game" method="post">
                        <input type="hidden" name="action" value="addReview">
                        <input type="hidden" name="idGame" value="<%= game.getGameID()%>">
                        <input type="hidden" name="idUser" value="<%= user.getUserID()%>">

                        <div class="mb-3">
                            <label for="review" class="form-label">Review Anda</label>
                            <textarea class="form-control" id="review" name="review" rows="3" required></textarea>
                        </div>

                        <div class="mb-3">
                            <label for="rating" class="form-label">Rating (0-5)</label>
                            <input type="number" class="form-control" id="rating" name="rating" 
                                   min="0" max="5" step="0.1" required>
                        </div>

                        <button type="submit" class="btn btn-success">Submit Review</button>
                    </form>
                </div>
            </div>
            <% } else { %>
            <div class="alert alert-warning" role="alert">
                Silakan login untuk memberikan review
            </div>
            <% } %>

            <%
            } catch (Exception e) {
            %>
            <div class="error-message">
                <h3>Error</h3>
                <p>Terjadi kesalahan: <%= e.getMessage()%></p>
                <a href="${pageContext.request.contextPath}/Game?action=homepage" class="btn btn-primary">
                    Kembali ke Daftar Game
                </a>
            </div>
            <%
                }
            %>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>


