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
public class DoLogin extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        try {
            DatabaseConnection db = new DatabaseConnection();
            db.pstmt = db.conn.prepareStatement("SELECT pswd,user_status,login_status FROM member_info WHERE user_id = ?");
            db.pstmt.setString(1, req.getParameter("username"));
            db.rst = db.pstmt.executeQuery();
            if (db.rst.next()) {
                if (db.rst.getString(1).equals(req.getParameter("password"))) {
                    if (db.rst.getString(2).equals("0")){
                        if (db.rst.getString(3).equals("0")) {
                            db.pstmt = db.conn.prepareStatement("UPDATE member_info SET login_status = ? WHERE user_id = ?");
                            db.pstmt.setString(1, "1");
                            db.pstmt.setString(2, req.getParameter("username"));
                            int i = db.pstmt.executeUpdate();
                            if (i > 0) {
                                HttpSession session = req.getSession();
                                session.setAttribute("usertype", "member");
                                session.setAttribute("username", req.getParameter("username"));
                                res.sendRedirect("dashboard");
                            } else {
                                res.sendRedirect("home?errMsg=Something went wrong while logging in.");
                            }
                        } else {
                            res.sendRedirect("home?errMsg=User is already logged in. Only one loggin is allowed at a time.");
                        }
                    } else {
                        res.sendRedirect("home?errMsg=Your account has been blocked. Please contact administratorr.");
                    }
                } else {
                    res.sendRedirect("home?errMsg=Password is incorrect.");
                }               
            } else {
                res.sendRedirect("home?errMsg=Username is invalid.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
