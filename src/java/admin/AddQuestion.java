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
public class AddQuestion extends HttpServlet {
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
                            "<title>Add Question</title>" +
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
                            "<li class=\"\" id=\"drop1\"><a href=\"#\" class=\"side-active-link\"><i class=\"fas fa-question-circle\"></i> Question <span class=\"arr\"><i class=\"fas fa-angle-down\"></i></span> </a>" +
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
                            "<h4>Dashboard <span class=\"sub-men-head\"><span class=\"sub-men\">Add Question</span> / Dashboard</span></h4>" +
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
                            "<div class=\"col-md-10 offset-md-1 add-ques-head\">" +
                            "<h3>Add Questions</h3>" +
                            "</div>" +
                            "<div class=\"col-md-10 offset-md-1 add-ques-form\">"
                        + "<form action=\"\" method=\"post\">" +
                            "<div class=\"row\">" +
                            "<div class=\"form-group col-md-6\">" +
                            "<label>Subject Name</label>                       " +
                            "<select name=\"sub_name\" class=\"form-control\">");
        db.pstmt = db.conn.prepareStatement("SELECT * FROM subject");
        db.rst = db.pstmt.executeQuery();
        if (db.rst.isBeforeFirst()) {
            while (db.rst.next()) {
                out.println("<option value=\""+db.rst.getString(1)+"\">"+db.rst.getString(2)+"</option>");
            }
        } else {
            out.println("<option>please add subject first.</option>");
        }
                        
                            
        out.println("</select>" +
                            "</div>" +
                            "<div class=\"form-group col-md-6\"> " +
                            "<label>Question</label>" +
                            "<textarea name=\"question\" class=\"form-control\"></textarea>" +
                            "</div>" +
                            "</div>" +
                            "" +
                            "<div class=\"row\">" +
                            "<div class=\"form-group col-md-6\">" +
                            "<label>Option 1</label>" +
                            "<textarea name=\"opt1\" class=\"form-control\"></textarea>" +
                            "</div>" +
                            "<div class=\"form-group col-md-6\">" +
                            "<label>Option 2</label>" +
                            "<textarea name=\"opt2\" class=\"form-control\"></textarea>" +
                            "</div>" +
                            "</div>" +
                            "" +
                            "<div class=\"row\">" +
                            "<div class=\"form-group col-md-6\">" +
                            "<label>Option 3</label>" +
                            "<textarea name=\"opt3\" class=\"form-control\"></textarea>" +
                            "</div>" +
                            "<div class=\"form-group col-md-6\">" +
                            "<label>Option 4</label>" +
                            "<textarea name=\"opt4\" class=\"form-control\"></textarea>" +
                            "</div>" +
                            "</div>" +
                            "" +
                            "<div class=\"row\">" +
                            "<div class=\"form-group col-md-12\">" +
                            "<label>Correct Option</label>" +
                            "<select name=\"correct_opt\" class=\"form-control\">" +
                            "<option value=\"option1\">Option 1</option>" +
                            "<option value=\"option2\">Option 2</option>" +
                            "<option value=\"option3\">Option 3</option>" +
                            "<option value=\"option4\">Option 4</option>" +
                            "</select>" +
                            "</div>" +
                            "</div>" +
                            "" +
                            "<div class=\"row\">" +
                            "<div class=\"form-group col-md-12\">" +
                            "<input type=\"submit\" name=\"addQues\" value=\"Add Question\" class=\"btn btn-success btn-block\">" +
                            "</div>" +
                            "</div></form>" +
                            "</div>" +
                            "</div>" +
                            "" +
                            "<div class=\"col-md-12 footer\">" +
                            "<h2 class=\"text-center\">&copy; 2019 | All Rights Reserved | Developed By Vineet Tiwari</h2>" +
                            "</div>" +
                            "" +
                            "</div>" +
                            "</div>" +
                            "" );
        
        
                if (req.getParameter("addQues") != null) {
            db.pstmt = db.conn.prepareStatement("INSERT INTO ques_mstr (ques, option1, option2, option3, option4, answer, subject_id) VALUES (?,?,?,?,?,?,?)");
            db.pstmt.setString(1, req.getParameter("question"));
            db.pstmt.setString(2, req.getParameter("opt1"));
            db.pstmt.setString(3, req.getParameter("opt2"));
            db.pstmt.setString(4, req.getParameter("opt3"));
            db.pstmt.setString(5, req.getParameter("opt4"));
            db.pstmt.setString(6, req.getParameter("correct_opt"));
            db.pstmt.setString(7, req.getParameter("sub_name"));
            int j = db.pstmt.executeUpdate();
            if (j > 0) {
                res.sendRedirect("AddQuestion?sucMsg=Successfully added question.");                
            } else {
                res.sendRedirect("AddQuestion?errMsg=Something went wrong while adding questions.");
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
