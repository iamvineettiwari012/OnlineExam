/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import bin.DatabaseConnection;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Vineet Tiwari
 */
public class welcome extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) {
        res.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        HttpSession session = req.getSession();
        DatabaseConnection db = new DatabaseConnection();
        try {
            if (session.getAttribute("usertype") != null && session.getAttribute("usertype").equals("admin")) {
                PrintWriter out = res.getWriter();
                res.setContentType("text/html;charset=UTF-8");
                out.println("<!DOCTYPE html>" +
                            "<html lang=\"en\">" +
                            "<head>" +
                            "<meta charset=\"UTF-8\">" +
                            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                            "<meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">" +
                            "<title>Dashboard</title>" +
                            "<link rel=\"stylesheet\" href=\"css/style2.css\">" +
                            "<link rel=\"stylesheet\" href=\"css/bootstrap.min.css\">" +
                            "<link rel=\"stylesheet\" href=\"font-awesome/css/all.css\">" +
                            "</head>" +
                            "<body>" +
                            "" +
                            "<div class=\"body-wrap\" id=\"body-wrap\">" +
                            "<div class=\"sidebar\" id=\"sidebar\">" +
                            "<div class=\"col-md-12 side-nav-head text-center\">" +
                            "<h1>Dashboard</h1>" +
                            "</div>" +
                            "<div class=\"col-md-12 side-nav-menu\" id=\"side-nav-menu-main\">" +
                            "<ul class=\"list-unstyled side-main-nav\">" +
                            "<li><a href=\"welcome\" class=\"side-active-link\"><i class=\"fas fa-home\"></i> Home</a></li>" +
                            "<li><a href=\"CandidateList\"><i class=\"fas fa-users\"></i> Candidate List</a></li>" +
                            "<li class=\"\" id=\"drop1\"><a href=\"#\"><i class=\"fas fa-question-circle\"></i> Question <span class=\"arr\"><i class=\"fas fa-angle-down\"></i></span> </a>" +
                            "<ul class=\"self-dropdown-hidden sub-drop-men\" id=\"drop1-sub\">" +
                            "<li><a href=\"ViewQuestions\">View Question List</a></li>" +
                            "<li><a href=\"AddQuestion\">Add Question</a></li>" +
                            "<li><a href=\"EditQuestion\">Update Question</a></li>" +
                            "</ul>" +
                            "</li>" +
                            "<li><a href=\"Subjects\"><i class=\"fas fa-book\"></i> Subjects</a></li>" +
                            "<li><a href=\"#\"><i class=\"far fa-list-alt\"></i> Result</a></li>" +
                            "</ul>" +
                            "</div>" +
                            "</div>" +
                            "" +
                            "" +
                            "<div class=\"body-main\" id=\"body-main\">" +
                            "" +
                            "" +
                            "<div class=\"col-md-12 top-nav\">" +
                            "<div class=\"row nav-row\">" +
                            "<div class=\"col-md-1 nav-btn\">" +
                            "<button id=\"toggleSidebar\" class=\"mybtn\"><i class=\"fas fa-align-justify\" id=\"btn-icon\"></i></button>" +
                            "</div>" +
                            "<div class=\"col-md-3 search-form\">" +
                            "<input type=\"search\" name=\"search\" class=\"navSearchBar\">" +
                            "<button type=\"submit\" name=\"search\"><i class=\"fas fa-search\"></i></button>" +
                            "</div>" +
                            "<div class=\"col-md-8 text-right myNavMenu4\">" +
                            "<ul class=\"list-unstyled\">" +
                            "<li><a href=\"#\"><i class=\"fas fa-expand-arrows-alt\"></i></a></li>" +
                            "<li class=\"bell\"><a href=\"#\"><i class=\"far fa-bell\"></i><span>02</span></a></li>" +
                            "<li class=\"envelope\"><a href=\"#\"><i class=\"far fa-envelope\"></i><span>05</span></a></li>" +
                            "</ul>" +
                            "</div>" +
                            "</div>" +
                            "</div>" +
                            "" +
                            "<div class=\"col-md-12 bottom-top-nav\">" +
                            "<div class=\"row bottom-top-nav-wrap\">" +
                            "<h4>Dashboard <span class=\"sub-men-head\"><span class=\"sub-men\">Home</span> / Dashboard</span></h4>" +
                            "</div>" +
                            "<div class=\"col-md-2 profile-top-bottom-nav ml-auto\">" +
                            "<a href=\"#\" class=\"dropdown-toggle\" id=\"menu-profile\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\"><i class=\"fas fa-user-circle\"></i> Admin</a>" +
                            "<div class=\"dropdown-menu menu-profile\" x-placement=\"bottom-start\" aria-labelledby=\"menu-profile\">                                " +
                            "<a class=\"dropdown-item\" href=\"#\">Change Password</a>" +
                            "<a class=\"dropdown-item\" href=\"logout\">Logout</a>" +
                            "</div>" +
                            "</div>" +
                            "</div>" +
                            "" +
                            "<div class=\"col-md-12 wrap-welcome\">" +
                            "<div class=\"row\">" +
                            "<div class=\"col-md-4\">" +
                            "<div class=\"col-md-12 wrap-welcome-item wrap1\">" +
                            "<h3 class=\"text-center welcome-msg-head\">Total Candidates</h3>" +
                            "<p class=\"text-center cont-logo\"><i class=\"fas fa-users-cog\"></i></p>" +
                            "<p class=\"text-center content-welcome\"><span>");
                db.pstmt = db.conn.prepareStatement("SELECT COUNT(user_id) FROM member_info");
                db.rst = db.pstmt.executeQuery();
                if (db.rst.next()) {
                  out.println(db.rst.getString(1));
                }
                out.println("</span></p>" +
                            "</div>" +
                            "</div>" +
                            "<div class=\"col-md-4\">" +
                            "<div class=\"col-md-12 wrap-welcome-item wrap2\">" +
                            "<h3 class=\"text-center welcome-msg-head\">Total Exam Submitted</h3>" +
                            "<p class=\"text-center cont-logo\"><i class=\"fas fa-sort-amount-up\"></i></p>" +
                            "<p class=\"text-center content-welcome\"><span>");
                db.pstmt = db.conn.prepareStatement("SELECT COUNT(DISTINCT paper_id) FROM exam_paper");
                db.rst = db.pstmt.executeQuery();
                if (db.rst.next()) {
                    out.println(db.rst.getString(1));
                }
                out.println("</span></p>" +
                            "</div>" +
                            "</div>" +
                            "<div class=\"col-md-4\">" +
                            "<div class=\"col-md-12 wrap-welcome-item wrap3\">" +
                            "<h3 class=\"text-center welcome-msg-head\">Total Subjects</h3>" +
                            "<p class=\"text-center cont-logo\"><i class=\"fas fa-book\"></i></p>" +
                            "<p class=\"text-center content-welcome\"><span>");
                db.pstmt = db.conn.prepareStatement("SELECT COUNT(subject_id) FROM subject");
                            db.rst = db.pstmt.executeQuery();
                          if (db.rst.next()) {
                            out.println(db.rst.getString(1));
                           }
                out.println("</span></p>" +
                            "" +
                            "</div>" +
                            "</div>" +
                            "</div>" +
                            "</div>" +
                            "" +
                            "<div class=\"col-md-12 footer\">" +
                            "<h2 class=\"text-center\">&copy; 2019 | All Rights Reserved | Developed By Vineet Tiwari</h2>" +
                            "</div>" +
                            "" +
                            "</div>" +
                            "</div>" +
                            "" +
                            "<script src=\"js/jquery.min.js\"></script>" +
                            "<script src=\"js/bootstrap.bundle.min.js\"></script>" +
                            "<script src=\"js/bootstrap.min.js\"></script>"
                        + "<script src=\"js/popper.min.js\"></script>" 
                        + "<script>$(function () {" +
                            "  $('[data-toggle=\"tooltip\"]').tooltip()" +
                            "});$(document).ready(function(){" +
                            "  $('.toast').toast('show');" +
                            "});</script>"+
                            "<script src=\"js/script.js\"></script>" +
                            "</body>" +
                            "</html> ");
            } else {
                res.sendRedirect("home");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
