/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bin;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Vineet Tiwari
 */
public class DoAdminLogin extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        try {
            DatabaseConnection db = new DatabaseConnection();
            db.pstmt = db.conn.prepareStatement("SELECT pswd FROM admin WHERE user = ?");
            db.pstmt.setString(1, req.getParameter("username"));
            db.rst = db.pstmt.executeQuery();
            if (db.rst.next()) {
                if (db.rst.getString(1).equals(req.getParameter("password"))) {
                    HttpSession session = req.getSession();
                    session.setAttribute("usertype", "admin");
                    session.setAttribute("username", req.getParameter("username"));
                    res.sendRedirect("welcome");
                } else {
                    res.sendRedirect("AdminLogin?errMsg=Password is incorrect.");
                }               
            } else {
                res.sendRedirect("AdminLogin?errMsg=Username is invalid.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
