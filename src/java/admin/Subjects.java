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
public class Subjects extends HttpServlet {
    
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
                            "<li><a href=\"welcome\"><i class=\"fas fa-home\"></i> Home</a></li>" +
                            "<li><a href=\"CandidateList\"><i class=\"fas fa-users\"></i> Candidate List</a></li>" +
                            "<li class=\"\" id=\"drop1\"><a href=\"#\"><i class=\"fas fa-question-circle\"></i> Question <span class=\"arr\"><i class=\"fas fa-angle-down\"></i></span> </a>" +
                            "<ul class=\"self-dropdown-hidden sub-drop-men\" id=\"drop1-sub\">" +
                            "<li><a href=\"ViewQuestions\">View Question List</a></li>" +
                            "<li><a href=\"AddQuestion\">Add Question</a></li>" +
                            "<li><a href=\"EditQuestion\">Update Question</a></li>" +
                            "</ul>" +
                            "</li>" +
                            "<li><a href=\"Subjects\" class=\"side-active-link\"><i class=\"fas fa-book\"></i> Subjects</a></li>" +
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
                            "<h4>Dashboard <span class=\"sub-men-head\"><span class=\"sub-men\">Subjects </span> / Dashboard</span></h4>" +
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
                            "<div class=\"col-md-12 wrap-welcome\">"
                            + "<div class=\"row\">" +
                            "<div class=\"col-md-4\">" +
                            "<div class=\"col-md-12 subject-main sub-table\">" +
                            "<h4>Subject List</h4>" +
                            "<table class=\"table\">" +
                            "<thead class=\"thead-violet\">" +
                            "<tr>" +
                            "<th scope=\"col\">Subject ID</th>" +
                            "<th scope=\"col\">Subject Name</th>" +
                            "<th scope=\"col\"></th>" +
                            "</tr>" +
                            "</thead>" +
                            "<tbody>");
                
                db.pstmt = db.conn.prepareStatement("SELECT * FROM subject");
                db.rst = db.pstmt.executeQuery();
                if (db.rst.isBeforeFirst()) {
                    while (db.rst.next()) {
                        out.println("<tr>"
                                + "<td>"+db.rst.getString(1)+"</td><td>"+db.rst.getString(2)+"</td><td><form action=\"\" method=\"post\">"
                                        + "<input type=\"hidden\" name=\"subjet_id_del\" value=\""+db.rst.getString(1)+"\"><button type=submit name=delete_subject_btn class=\"btn btn-danger\"><i class=\"fas fa-trash-alt\"></i></button>"
                                        + "</form></td>"
                                + "</tr>");
                    }
                } else {
                    out.println("<tr><th colspan=3>No Data Found.</th></tr>");
                }
                            
            out.println("</tbody>" +
                            "</table>" +
                            "</div>" +
                            "</div>" +
                            "<div class=\"col-md-4\">" +
                            "<div class=\"col-md-12 subject-main\">" +
                            "<h4>Add Subject</h4>" +
                            "<form action=\"\" method=post>" +
                            "<div class=\"form-group col-md-12\">" +
                            "<label>Enter Subject Name</label>" +
                            "<input type=\"text\" name=\"subject_name\" class=\"form-control\" placeholder=\"Enter Subject Name\">" +
                            "</div>" +
                            "<div class=\"form-group col-md-12\">" +
                            "<input type=\"submit\" name=\"add_subject\" value=\"Add Subject\" class=\"btn btn-primary btn-block\">" +
                            "</div>" +
                            "</form>" +
                            "</div>" +
                            "</div>" +
                            "<div class=\"col-md-4\">" +
                            "<div class=\"col-md-12 subject-main\">" +
                            "<h4>Update Subject</h4>" +
                            "<form action=\"\">" +
                            "<div class=\"row col-md-12\">" +
                            "<div class=\"form-group col-md-8\">" +
                            "<input type=\"search\" name=\"search_subject_id\" placeholder=\"Subject ID\" class=\"form-control\">" +
                            "</div>" +
                            "<div class=\"form-group col-md-2\">" +
                            "<input type=\"submit\" name=\"search_subject\" value=\"SEARCH\" class=\"btn btn-primary\">" +
                            "</div>" +
                            "</div>" +
                            "</form>");
            
            if (req.getParameter("search_subject") != null) {
                String search_id = req.getParameter("search_subject_id");
                db.pstmt = db.conn.prepareStatement("SELECT * FROM subject WHERE subject_id = ?");
                db.pstmt.setString(1, search_id);
                db.rst = db.pstmt.executeQuery();
                if (db.rst.next()) {
                    out.println("<form action=\"\" method=\"post\">" +
                            "<div class=\"form-group col-md-12\">" +
                            "<label>Update Subject Name</label>" +
                            "<input type=\"text\" name=\"subject_name_up\" class=\"form-control\" value=\""+db.rst.getString(2)+"\" placeholder=\"Enter Subject Name\">" +
                            "</div><input type=hidden name=\"up_search_subject_id\" value=\""+db.rst.getString(1)+"\"" +
                            "<div class=\"form-group col-md-12\">" +
                            "<input type=\"submit\" name=\"update_subject\" value=\"Update Subject\" class=\"btn btn-primary btn-block\">" +
                            "</div>" +
                            "</form>" );
                } else {
                    res.sendRedirect("Subjects?errMsg=No subject found with the search id entered.");
                }
            }
            
            
            if (req.getParameter("update_subject") != null) {
                db.pstmt = db.conn.prepareStatement("UPDATE subject SET subject_name = ? WHERE subject_id = ?");
                db.pstmt.setString(1, req.getParameter("subject_name_up"));
                db.pstmt.setString(2, req.getParameter("up_search_subject_id"));
                int i = db.pstmt.executeUpdate();
                if (i > 0) {
                    res.sendRedirect("Subjects?sucMsg=Successfully updated subject.");
                } else {
                    res.sendRedirect("Subjects?errMsg=Something went wrong while updating subject");
                }
            }

            out.println("</div>" +
                            "</div>" +
                            "</div>" +
                            "</div>" +
                            "" +
                            "<div class=\"col-md-12 footer\">" +
                            "<h2 class=\"text-center\">&copy; 2019 | All Rights Reserved | Developed By Vineet Tiwari</h2>" +
                            "</div>" +
                            "" +
                            "</div>" +
                            "</div>");
                
            if (req.getParameter("add_subject")!=null) {
                if (req.getParameter("subject_name") != "") {
                db.pstmt = db.conn.prepareStatement("INSERT INTO subject (subject_name) VALUES (?)");
                db.pstmt.setString(1, req.getParameter("subject_name"));
                int done = db.pstmt.executeUpdate();
                if (done > 0) {
                    res.sendRedirect("Subjects?sucMsg=Subject successfully added");
                } else {
                    res.sendRedirect("Subjects?errMsg=Something went wrong while adding subject.");
                }
                }else {
                    res.sendRedirect("Subjects?errMsg=Please fill subject name before adding.");
                }
            }
            
            if (req.getParameter("delete_subject_btn") != null) {
                db.pstmt = db.conn.prepareStatement("DELETE FROM subject WHERE subject_id = ?");
                db.pstmt.setString(1, req.getParameter("subjet_id_del"));
                int d = db.pstmt.executeUpdate();
                if (d > 0) {
                    res.sendRedirect("Subjects?sucMsg=Successfully deleted subject column.");
                } else {
                    res.sendRedirect("Subjects?errMsg=Something went wrong.");
                }
            }
                
            if (req.getParameter("errMsg") != null) {
            
            out.println("<div aria-live=\"polite\" aria-atomic=\"true\" style=\"position: relative; min-height: 200px;\">"
                        + "<div class=\"toast col-md-2 \"  role=\"alert\" style=\"position: fixed; top: 80px; right: 5px; padding: 0px;\"  data-delay=\"5000\" data-autohide=\"true\">"
                    +"  <div class=\"toast-header bg-danger\">"
                    +"    <strong class=\"mr-auto\" style=\"color: #fff;\"><h4>Error Message<h4></strong>"
                    +"  </div>"
                    +"  <div class=\"toast-body err-toast-my\">"+
                    req.getParameter("errMsg")
                    +"  </div>"
                    +"</div>"
                    + "</div>");
            
            }
            if (req.getParameter("sucMsg") != null) {
            
            out.println("<div aria-live=\"polite\" aria-atomic=\"true\" style=\"position: relative; min-height: 200px;\">"
                        + "<div class=\"toast col-md-2 \"  role=\"alert\" style=\"position: fixed; top: 80px; right: 5px; padding: 0px;\"  data-delay=\"5000\" data-autohide=\"true\">"
                    +"  <div class=\"toast-header bg-success\">"
                    +"    <strong class=\"mr-auto\" style=\"color: #fff;\"><h4>Success Message<h4></strong>"
                    +"  </div>"
                    +"  <div class=\"toast-body err-toast-my\">"+
                    req.getParameter("sucMsg")
                    +"  </div>"
                    +"</div>"
                    + "</div>");
                
            }
                
                            out.println("<script src=\"js/jquery.min.js\"></script>" +
                            "<script src=\"js/bootstrap.bundle.min.js\"></script>" +
                            "<script src=\"js/bootstrap.min.js\"></script>" 
                        + "<script src=\"js/popper.min.js\"></script>" +
                            "<script src=\"js/script.js\"></script>" +
                         "<script>$(function () {" +
                            "  $('[data-toggle=\"tooltip\"]').tooltip()" +
                            "});$(document).ready(function(){" +
                            "  $('.toast').toast('show');" +
                            "});</script>"+
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
