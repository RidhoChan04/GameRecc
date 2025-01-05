/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wisnu
 */
public interface Sign {
    public void handleRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    public void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    public void handleLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
