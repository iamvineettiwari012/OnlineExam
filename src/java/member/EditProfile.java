/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package member;

import bin.DatabaseConnection;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vineet Tiwari
 */
public class EditProfile extends HttpServlet {
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
                            "<a class=\"dropdown-item\" href=\"Profile\"><i class=\"fas fa-user-alt\"></i> View Profile</a>" +
                            "<a class=\"dropdown-item active\" href=\"EditProfile\"><i class=\"fas fa-user-edit\"></i> Edit Profile <span class=\"sr-only\">(current)</span></a>" +
                            "<a class=\"dropdown-item\" href=\"ChangePassword\"><i class=\"fas fa-cog\"></i> Change Password</a>" +
                            "<a class=\"dropdown-item\" href=\"logout\"><i class=\"fas fa-sign-out-alt\"></i> Logout</a>" +
                            "</div>" +
                            "</li>	" +
                            "</ul>" +
                            "</div>" +
                            "</div>" +
                            "</nav>" +
                            "<div class=\"container-fluid user-edit-profile-main\">"
                                +"<div class=\"col-md-6 offset-md-3 user-edit-profile-wrap\">"
                                    +"<h3 class=\"edit-profile-header text-center\">Edit Profile</h3>"
                                    +"<form class=\"col-md-12 edit-form\" action=\"\" method=\"post\">" +
                                     "<div class=\"form-row\">" +
                                     "<div class=\"form-group col-md-6\">" +
                                    "<label>Name</label>" +
                                    "<input type=\"text\" name=name value=\""+db.rst.getString(2)+"\" class=\"form-control\">" +
                                    "</div>" +
                                    "<div class=\"form-group col-md-6\">" +
                                    "<label>Father Name</label>" +
                                    "<input type=\"text\" name=fname value=\""+db.rst.getString(7)+"\"  class=\"form-control\">" +
                                    "</div>" +
                                    "</div>" +
                                    "<div class=\"form-group\">" +
                                    "<label>Email</label>" +
                                    "<input type=\"email\" name=email value=\""+db.rst.getString(8)+"\"  class=\"form-control\">" +
                                    "</div>" +
                                    "<div class=\"form-row\">" +
                                    "<div class=\"form-group col-md-6\">" +
                                    "<label>Gender</label>" +
                                    "<div class=\"reg-gender col-md-12\">");
                
                            if (db.rst.getString(9).equalsIgnoreCase("male")) {
                                    out.println("<label><input type=\"radio\" name=\"gender\" value=\"male\" checked> Male</label>");
                                    out.println("<label class=\"mar-left\"><input type=\"radio\" name=\"gender\" value=\"female\"> Female</label>");                                
                            } else {
                                    out.println("<label><input type=\"radio\" name=\"gender\" value=\"male\"> Male</label>");
                                    out.println("<label class=\"mar-left\"><input type=\"radio\" name=\"gender\" value=\"female\" checked> Female</label>");                                    
                            }
                                    out.println("</div>"+
                                    "</div>" +
                                    "<div class=\"form-group col-md-6\">" +
                                    "<label>Date Of Birth</label>" +
                                    "<input type=\"date\" name=dob value=\""+db.rst.getString(10)+"\"  class=\"form-control\">"+
                                    "</div>" +
                                    "<div class=\"form-group col-md-12\">" +
                                    "<label>Qualification</label>" +
                                    "<select class=\"form-control\" name=\"qual\">");
                                    
                                    if (db.rst.getString(11).equalsIgnoreCase("10")) {
                                        out.println("<option value=\"10\" selected>10 (Matriculation)</option>"+
                                        "<option value=\"12\">10+2 (Intermediate)</option>"+
                                        "<option value=\"B.Sc.\">B.Sc.</option>"+
                                        "<option value=\"BCA\">BCA</option>"+
                                        "<option value=\"B.Tech.\">B.Tech.</option>"+
                                        "<option value=\"M.Sc.\">M.Sc.</option>"+
                                        "<option value=\"MCA\">MCA</option>"+
                                        "<option value=\"M.Tech.\">M.Tech.</option>");
                                    } else if (db.rst.getString(11).equalsIgnoreCase("12")) {
                                        out.println("<option value=\"10\">10 (Matriculation)</option>"+
                                        "<option value=\"12\" selected>10+2 (Intermediate)</option>"+
                                        "<option value=\"B.Sc.\">B.Sc.</option>"+
                                        "<option value=\"BCA\">BCA</option>"+
                                        "<option value=\"B.Tech.\">B.Tech.</option>"+
                                        "<option value=\"M.Sc.\">M.Sc.</option>"+
                                        "<option value=\"MCA\">MCA</option>"+
                                        "<option value=\"M.Tech.\">M.Tech.</option>");
                                    } else if (db.rst.getString(11).equalsIgnoreCase("B.Sc.")) {
                                        out.println("<option value=\"10\">10 (Matriculation)</option>"+
                                        "<option value=\"12\">10+2 (Intermediate)</option>"+
                                        "<option value=\"B.Sc.\" selected>B.Sc.</option>"+
                                        "<option value=\"BCA\">BCA</option>"+
                                        "<option value=\"B.Tech.\">B.Tech.</option>"+
                                        "<option value=\"M.Sc.\">M.Sc.</option>"+
                                        "<option value=\"MCA\">MCA</option>"+
                                        "<option value=\"M.Tech.\">M.Tech.</option>");
                                    }  else if (db.rst.getString(11).equalsIgnoreCase("BCA")) {
                                        out.println("<option value=\"10\">10 (Matriculation)</option>"+
                                        "<option value=\"12\">10+2 (Intermediate)</option>"+
                                        "<option value=\"B.Sc.\">B.Sc.</option>"+
                                        "<option value=\"BCA\" selected>BCA</option>"+
                                        "<option value=\"B.Tech.\">B.Tech.</option>"+
                                        "<option value=\"M.Sc.\">M.Sc.</option>"+
                                        "<option value=\"MCA\">MCA</option>"+
                                        "<option value=\"M.Tech.\">M.Tech.</option>");
                                    }   else if (db.rst.getString(11).equalsIgnoreCase("B.Tech.")) {
                                        out.println("<option value=\"10\">10 (Matriculation)</option>"+
                                        "<option value=\"12\">10+2 (Intermediate)</option>"+
                                        "<option value=\"B.Sc.\">B.Sc.</option>"+
                                        "<option value=\"BCA\">BCA</option>"+
                                        "<option value=\"B.Tech.\" selected>B.Tech.</option>"+
                                        "<option value=\"M.Sc.\">M.Sc.</option>"+
                                        "<option value=\"MCA\">MCA</option>"+
                                        "<option value=\"M.Tech.\">M.Tech.</option>");
                                    }   else if (db.rst.getString(11).equalsIgnoreCase("M.Sc.")) {
                                        out.println("<option value=\"10\">10 (Matriculation)</option>"+
                                        "<option value=\"12\">10+2 (Intermediate)</option>"+
                                        "<option value=\"B.Sc.\">B.Sc.</option>"+
                                        "<option value=\"BCA\">BCA</option>"+
                                        "<option value=\"B.Tech.\">B.Tech.</option>"+
                                        "<option value=\"M.Sc.\" selected>M.Sc.</option>"+
                                        "<option value=\"MCA\">MCA</option>"+
                                        "<option value=\"M.Tech.\">M.Tech.</option>");
                                    }   else if (db.rst.getString(11).equalsIgnoreCase("MCA")) {
                                        out.println("<option value=\"10\">10 (Matriculation)</option>"+
                                        "<option value=\"12\">10+2 (Intermediate)</option>"+
                                        "<option value=\"B.Sc.\">B.Sc.</option>"+
                                        "<option value=\"BCA\">BCA</option>"+
                                        "<option value=\"B.Tech.\">B.Tech.</option>"+
                                        "<option value=\"M.Sc.\">M.Sc.</option>"+
                                        "<option value=\"MCA\" selected>MCA</option>"+
                                        "<option value=\"M.Tech.\">M.Tech.</option>");
                                    }   else if (db.rst.getString(11).equalsIgnoreCase("M.Tech.")) {
                                        out.println("<option value=\"10\">10 (Matriculation)</option>"+
                                        "<option value=\"12\">10+2 (Intermediate)</option>"+
                                        "<option value=\"B.Sc.\">B.Sc.</option>"+
                                        "<option value=\"BCA\">BCA</option>"+
                                        "<option value=\"B.Tech.\">B.Tech.</option>"+
                                        "<option value=\"M.Sc.\">M.Sc.</option>"+
                                        "<option value=\"MCA\">MCA</option>"+
                                        "<option value=\"M.Tech.\" selected>M.Tech.</option>");
                                    }  

                                    
                                    out.println("</select>"+
                                    "</div>" +
                                    "<div class=\"form-group col-md-12\">" +
                                    "<label>Address</label>" +
                                    "<textarea name=address class=\"form-control\">"+db.rst.getString(12)+"</textarea>" +
                                    "</div>" +
                                    "<div class=\"form-group col-md-6\">"+
                                    "<label>City</label>"+
                                    "<input type=\"text\" name=city value=\""+db.rst.getString(13)+"\"  class=\"form-control\">" +
                                    "</div>" +
                                    "<div class=\"form-group col-md-6\">"+
                                    "<label>Contact</label>"+
                                    "<input type=\"number\" name=contact value=\""+db.rst.getString(14)+"\"  class=\"form-control\">" +
                                    "</div>" +
                                    "<div class=\"col-md-12\"><input type=submit name=save class=\"btn btn-success btn-block\" value=Save></div>" +
                                    
                                    "</form>"
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
            
            if (req.getParameter("save") != null) {
                db.pstmt = db.conn.prepareStatement("UPDATE member_info SET name = ?, fname = ?, email = ?, gender = ?, dob = ?, qualification = ?, address = ?, city = ?, contact = ? WHERE user_id = ?");
                db.pstmt.setString(1, req.getParameter("name"));
                db.pstmt.setString(2, req.getParameter("fname"));
                db.pstmt.setString(3, req.getParameter("email"));
                db.pstmt.setString(4, req.getParameter("gender"));
                db.pstmt.setString(5, req.getParameter("dob"));
                db.pstmt.setString(6, req.getParameter("qual"));
                db.pstmt.setString(7, req.getParameter("address"));
                db.pstmt.setString(8, req.getParameter("city"));
                db.pstmt.setString(9, req.getParameter("contact"));
                db.pstmt.setString(10, session.getAttribute("username").toString());
                int i = db.pstmt.executeUpdate();
                if (i > 0) {
                    res.sendRedirect("EditProfile?sucMsg=Successfully updated your profile.");
                } else {
                    res.sendRedirect("EditProfile?errMsg=Something went wrong while updating profile.");                    
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
                }
            } else {
                res.sendRedirect("home");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
