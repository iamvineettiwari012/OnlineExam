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
public class logout extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) {
        try {
            HttpSession session = req.getSession();
            if (session.getAttribute("usertype") != null) {
                if (session.getAttribute("usertype").equals("admin")){
                    session.removeAttribute("usertype");
                    session.removeAttribute("username");
                    session.invalidate();
                    res.sendRedirect("AdminLogin");
                } else {
                    DatabaseConnection db = new DatabaseConnection();
                    db.pstmt = db.conn.prepareStatement("UPDATE member_info SET login_status = ? WHERE user_id = ?");
                    db.pstmt.setString(1, "0");
                    db.pstmt.setString(2, session.getAttribute("username").toString());
                    int i = db.pstmt.executeUpdate();
                    if (i > 0) {
                        session.removeAttribute("usertype");
                        session.removeAttribute("username");
                        session.removeAttribute("ExamArea");
                        session.removeAttribute("ques_list_from_db");
                        session.removeAttribute("qc");
                        session.invalidate();
                        res.sendRedirect("home");
                    } else {
                        res.sendRedirect("dashboard");
                    }
                }
            } else {
                res.sendRedirect("home");
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
