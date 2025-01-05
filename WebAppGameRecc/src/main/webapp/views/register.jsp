<%-- 
    Document   : register
    Created on : 20 Nov 2024, 20.14.56
    Author     : Victus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>GameRecce Sign Up</title>
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      margin: 0;
      padding: 0;
      font-family: Arial, sans-serif;
      background-color: #f2f2f2;
      display: flex;
      justify-content: center;
      align-items: center;
    }

    .login-container {
      display: flex;
      background-color: #D9D9D9;
      width: 100%;
    }

    .left-panel {
      background-color: #093545;
      height: 100vh;
      display: flex;
      width: 40%; 
      padding: 20px;
      justify-content: center;
      position: relative; 
    }

    #logo {
      max-width: 120%;
      border-radius: 25px;
      height: auto;
      position: absolute;
      top: 50%;
      left: 0;
      transform: translate(30%, -50%); 
    }

    #gambarBwh {
      max-width: 100%; 
      height: auto;
      position: absolute;
      left: 0;
      bottom: 0;
      object-fit: cover; 
      overflow: hidden; 
    }

    .right-panel {
      padding: 80px 180px; 
      padding-right: 350px;
      width: 60%; 
      display: flex;
      flex-direction: column;
      justify-content: center;
    }

    .right-panel h2 {
      font-size: 4rem;  
      font-weight: bold;
      margin-bottom: 20px;
    }

    .form-control {
      border-radius: 10px;
      margin-bottom: 20px;
      font-size: 1.1rem; 
      background-color: #879FA2;
    }

    .btn-primary {
      background-color: #043744;
      border: none;
      border-radius: 10px;
      padding: 15px; 
      font-size: 1.2rem; 
    }

    .btn-primary:hover {
      background-color: #065063;
    }

    a {
      color: #043744;
      text-decoration: none;
    }

    a:hover {
      text-decoration: underline;
    }

    .error-message {
      color: #DB0E12;
    }
  </style>
</head>
<body>
  <div class="login-container">
    <div class="left-panel">
        <img id="logo" src="../gambar/logogamerec_kotak.png" alt="GameRecce Logo">
        <img id="gambarBwh" src="../gambar/Group_8.png" alt="GameRecce Logo">
    </div>
    <div class="right-panel">
      <h2>Sign Up</h2>
             <% String errorMessage = (String) request.getAttribute("errorMessage");
         if (errorMessage != null) { %>
        <div class="error-message">
          <%= errorMessage %>
        </div>
      <% } %>
      <form action="/User" method="post">
        <input type="hidden" name="action" value="register">
        <div class="mb-3">
          <input type="text" class="form-control" id="username" name="username" placeholder="&#xf007; Username" style="font-family: Arial, FontAwesome;">
        </div>
        <div class="mb-3">
          <input type="password" class="form-control" id="password" name="password" placeholder="&#xf023; Password" style="font-family: Arial, FontAwesome;">
        </div>
        <button type="submit" class="btn btn-primary w-100">Sign Up</button>
      </form>

      <div class="text-center mt-3">
        <p>Existing User? <a href="/login">Sign In</a></p>
      </div>
    </div>
  </div>

  <!-- Bootstrap JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
