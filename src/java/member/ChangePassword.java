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
public class ChangePassword extends HttpServlet {
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
                            "<a class=\"nav-link\" href=\"#\"><i class=\"fas fa-address-card\"></i> Result</a>" +
                            "</li>" +
                            "<li class=\"nav-item\">" +
                            "<a class=\"nav-link\" href=\"#\"><i class=\"fas fa-comment\"></i> Feedback</a>" +
                            "</li>	" +
                            "<li class=\"nav-item dropdown\">" +
                            "<a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"dropdownId\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\"><i class=\"fas fa-user-circle\"></i> Settings</a>" +
                            "<div class=\"dropdown-menu col-md-12 mydrop\" aria-labelledby=\"dropdownId\">" +
                            "<a class=\"dropdown-item\" href=\"Profile\"><i class=\"fas fa-user-alt\"></i> View Profile</a>" +
                            "<a class=\"dropdown-item\" href=\"EditProfile\"><i class=\"fas fa-user-edit\"></i> Edit Profile</a>" +
                            "<a class=\"dropdown-item active\" href=\"ChangePassword\"><i class=\"fas fa-cog\"></i> Change Password <span class=\"sr-only\">(current)</span></a>" +
                            "<a class=\"dropdown-item\" href=\"logout\"><i class=\"fas fa-sign-out-alt\"></i> Logout</a>" +
                            "</div>" +
                            "</li>	" +
                            "</ul>" +
                            "</div>" +
                            "</div>" +
                            "</nav>" +
                            "<div class=\"container-fluid user-password-change\">"
                                +"<div class=\"col-md-4 offset-md-4 user-change-password-wrap\">"
                                + "<h3>Change Password</h3>"
                                + "<form class=\"col-md-12\" action=\"\" method=\"post\">"
                                + "<div class=\"row\">"
                                + "<div class=\"form-group col-md-12\">"
                                + "<label>Old Password</label>"
                                + "<input type=\"password\" name=\"oldpass\" class=\"form-control\">"
                                + "</div>"
                                + "</div>"
                                + "<div class=\"row\">"
                                + "<div class=\"form-group col-md-12\">"
                                + "<label>New Password</label>"
                                + "<input type=\"password\" name=\"newpass\" class=\"form-control\">"
                                + "</div>"
                                + "</div>"
                                + "<div class=\"row\">"
                                + "<div class=\"form-group col-md-12\">"
                                + "<label>Confirm New Password</label>"
                                + "<input type=\"password\" name=\"cpass\" class=\"form-control\">"
                                + "</div>"
                                + "</div>"
                                + "<div class=\"row\">"
                                + "<div class=\"form-group col-md-12\">"
                                + "<input type=\"submit\" name=\"changepass\" value=\"Change Password\" class=\"form-control btn btn-success btn-block\">"
                                + "</div>"
                                + "</div>"
                                + "</form>"
                                +"</div>"+
                            "</div>");
                
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
            
            if (req.getParameter("changepass")!=null) {
                if (req.getParameter("oldpass") != "" && req.getParameter("newpass") != "" && req.getParameter("cpass") != "") {
                    if (req.getParameter("newpass").equals(req.getParameter("cpass"))) {
                        db.pstmt = db.conn.prepareStatement("SELECT name FROM member_info WHERE user_id = ? AND pswd = ?");
                        db.pstmt.setString(1, session.getAttribute("username").toString());
                        db.pstmt.setString(2, req.getParameter("oldpass"));
                        db.rst = db.pstmt.executeQuery();
                        if (db.rst.next()) {
                            db.pstmt = db.conn.prepareStatement("UPDATE member_info SET pswd = ? WHERE user_id = ?");
                            db.pstmt.setString(1, req.getParameter("cpass"));
                            db.pstmt.setString(2, session.getAttribute("username").toString());
                            int i = db.pstmt.executeUpdate();
                            if (i > 0) {
                                res.sendRedirect("ChangePassword?sucMsg=Successfully changed password");
                            } else {
                                res.sendRedirect("ChangePassword?errMsg=Something went wrong while changing password. Please try again after sometime.");
                            }                        
                        } else {
                            res.sendRedirect("ChangePassword?errMsg=Old password does not matched.");
                        }
                    } else {
                        res.sendRedirect("ChangePassword?errMsg=New password and Confirm password does not matched.");
                    }
                } else {
                    res.sendRedirect("ChangePassword?errMsg=All the fields are required.");
                }
            }
                
            out.println("<script src=\"js/jquery.min.js\"></script>" +
                            "<script src=\"js/bootstrap.min.js\"></script>" +
                            "<script>$(document).ready(function(){" +
                            "  $('.toast').toast('show');" +
                            "});</script>"+
                            "</body>" +
                            "</html>");
                }
            } else {
                res.sendRedirect("home");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
