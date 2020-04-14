/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package member;

import bin.DatabaseConnection;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Vineet Tiwari
 */
public class Profile extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) {
        res.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        HttpSession session = req.getSession();
        try {
            if (session.getAttribute("usertype") != null && session.getAttribute("usertype").equals("member")) {
                if(session.getAttribute("exam_area_status") != null && session.getAttribute("exam_area_status").toString().equalsIgnoreCase("active")) {
                    res.sendRedirect("ExamArea");
                } else {
                PrintWriter out = res.getWriter();
                DatabaseConnection db = new DatabaseConnection();
                db.pstmt = db.conn.prepareStatement("SELECT * FROM member_info WHERE user_id = ?");
                db.pstmt.setString(1, session.getAttribute("username").toString());
                db.rst = db.pstmt.executeQuery();
                if (db.rst.next()) {
                res.setContentType("text/html;charset=UTF-8");
                out.println("<!DOCTYPE html>" +
                            "<html lang=\"en\">" +
                            "<head>" +
                            "<meta charset=\"UTF-8\">" +
                            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                            "<meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">" +
                            "<title>Dashboard</title>" +
                            "<link rel=\"stylesheet\" href=\"css/style.css\">" +
                            "<link rel=\"stylesheet\" href=\"css/bootstrap.min.css\">" +
                            "<link rel=\"stylesheet\" href=\"font-awesome/css/all.css\">" +
                            "</head>" +
                            "<body>" +
                            "<nav class=\"navbar sticky-top navbar-expand-lg mynav let\">" +
                            "<div class=\"container-fluid\">" +
                            "<a class=\"navbar-brand\" href=\"#\">Online Examination System</a>" +
                            "<button class=\"navbar-toggler d-lg-none\" type=\"button\" data-toggle=\"collapse\" data-target=\"#collapsibleNavId\" aria-controls=\"collapsibleNavId\"" +
                            "aria-expanded=\"false\" aria-label=\"Toggle navigation\">" +
                            "<span class=\"navbar-toggler-icon\"><i class=\"fas fa-align-justify\"></i></span>" +
                            "</button>" +
                            "<div class=\"collapse navbar-collapse\" id=\"collapsibleNavId\">" +
                            "<ul class=\"navbar-nav ml-auto mt-2 mt-lg-0\">" +
                            "<li class=\"nav-item\">" +
                            "<a class=\"nav-link\" href=\"dashboard\"><i class=\"fas fa-home\"></i> Home </a>" +
                            "</li>" +
                            "<li class=\"nav-item\">"+
                            "<a class=\"nav-link\" href=\"Exam\"><i class=\"fas fa-book\"></i> Exam</a>" +
                            "</li>" +
                            "<li class=\"nav-item\">"+
                            "<a class=\"nav-link\" href=\"Result\"><i class=\"fas fa-address-card\"></i> Result</a>" +
                            "</li>" +
                            "<li class=\"nav-item\">" +
                            "<a class=\"nav-link\" href=\"#\"><i class=\"fas fa-comment\"></i> Feedback</a>" +
                            "</li>	" +
                            "<li class=\"nav-item dropdown\">" +
                            "<a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"dropdownId\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\"><i class=\"fas fa-user-circle\"></i> Settings</a>" +
                            "<div class=\"dropdown-menu col-md-12 mydrop\" aria-labelledby=\"dropdownId\">" +
                            "<a class=\"dropdown-item active\" href=\"Profile\"><i class=\"fas fa-user-alt\"></i> View Profile <span class=\"sr-only\">(current)</span></a>" +
                            "<a class=\"dropdown-item\" href=\"EditProfile\"><i class=\"fas fa-user-edit\"></i> Edit Profile</a>" +
                            "<a class=\"dropdown-item\" href=\"ChangePassword\"><i class=\"fas fa-cog\"></i> Change Password</a>" +
                            "<a class=\"dropdown-item\" href=\"logout\"><i class=\"fas fa-sign-out-alt\"></i> Logout</a>" +
                            "</div>" +
                            "</li>	" +
                            "</ul>" +
                            "</div>" +
                            "</div>" +
                            "</nav>" +
                            "<div class=\"container-fluid user-profile-main\">"
                                +"<div class=\"col-md-12 user-profile-main-wrap\">"
                                +"<div class=\"col-md-1 user-logo text-center\">"
                                +"<p class=\"profile-logo\"><i class=\"fas fa-user-circle\"></i></p>"
                                +"</div>"
                                +"<h4 class=\"user-profile-name-head text-center\">"+db.rst.getString(3).toUpperCase()+"</h4>"
                                +"<div class=\"row\">"
                                    +"<div class=\"col-md-4\">"
                                        + "<div class=\"user-prof-cont-main col-md-12\">"
                                        + "<h3 class=\"user-prof-cont-head\">Username</h3>"
                                        + "<p class=\"user-prof-cont-content\">"+db.rst.getString(1)+"</p>"
                                        + "</div>"
                                    + "</div>"
                                    +"<div class=\"col-md-4\">"
                                        + "<div class=\"user-prof-cont-main col-md-12\">"
                                        + "<h3 class=\"user-prof-cont-head\">Father Name</h3>"
                                        + "<p class=\"user-prof-cont-content prof-cont-caps\">"+db.rst.getString(4)+"</p>"
                                        + "</div>"
                                    + "</div>"
                                    +"<div class=\"col-md-4\">"
                                        + "<div class=\"user-prof-cont-main col-md-12\">"
                                        + "<h3 class=\"user-prof-cont-head\">Email</h3>"
                                        + "<p class=\"user-prof-cont-content\">"+db.rst.getString(5)+"</p>"
                                        + "</div>"
                                    + "</div>"
                                + "</div>"
                                +"<div class=\"row\">"
                                    +"<div class=\"col-md-4\">"
                                        + "<div class=\"user-prof-cont-main col-md-12\">"
                                        + "<h3 class=\"user-prof-cont-head\">Gender</h3>"
                                        + "<p class=\"user-prof-cont-content prof-cont-caps\">"+db.rst.getString(6)+"</p>"
                                        + "</div>"
                                    + "</div>"
                                    +"<div class=\"col-md-4\">"
                                        + "<div class=\"user-prof-cont-main col-md-12\">"
                                        + "<h3 class=\"user-prof-cont-head\">Date Of Birth</h3>"
                                        + "<p class=\"user-prof-cont-content\">"+db.rst.getString(7)+"</p>"
                                        + "</div>"
                                    + "</div>"
                                    +"<div class=\"col-md-4\">"
                                        + "<div class=\"user-prof-cont-main col-md-12\">"
                                        + "<h3 class=\"user-prof-cont-head\">Qualification</h3>"
                                        + "<p class=\"user-prof-cont-content prof-cont-caps\">"+db.rst.getString(8)+"</p>"
                                        + "</div>"
                                    + "</div>"
                                + "</div>"
                                +"<div class=\"row\">"
                                    +"<div class=\"col-md-4\">"
                                        + "<div class=\"user-prof-cont-main col-md-12\">"
                                        + "<h3 class=\"user-prof-cont-head\">Address</h3>"
                                        + "<p class=\"user-prof-cont-content prof-cont-caps\">"+db.rst.getString(9)+"</p>"
                                        + "</div>"
                                    + "</div>"
                                    +"<div class=\"col-md-4\">"
                                        + "<div class=\"user-prof-cont-main col-md-12\">"
                                        + "<h3 class=\"user-prof-cont-head\">City</h3>"
                                        + "<p class=\"user-prof-cont-content prof-cont-caps\">"+db.rst.getString(10)+"</p>"
                                        + "</div>"
                                    + "</div>"
                                    +"<div class=\"col-md-4\">"
                                        + "<div class=\"user-prof-cont-main col-md-12\">"
                                        + "<h3 class=\"user-prof-cont-head\">Contact Number</h3>"
                                        + "<p class=\"user-prof-cont-content\">"+db.rst.getString(11)+"</p>"
                                        + "</div>"
                                    + "</div>"
                                + "</div>"
                                +"</div>"+
                            "</div>" +
                            "<script src=\"js/jquery.min.js\"></script>" +
                            "<script src=\"js/bootstrap.min.js\"></script>" +
                            "</body>" +
                            "</html>");
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
