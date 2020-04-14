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
public class ViewQuestions extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) {
        res.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        HttpSession session = req.getSession();
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
                            "<title>Questions</title>" +
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
                            "<h4>Dashboard <span class=\"sub-men-head\"><span class=\"sub-men\">Question List</span> / Dashboard</span></h4>" +
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
                            "<table class=\"table\">" +
                            "<thead class=\"thead-violet\">" +
                            "" +
                            "<tr>" +
                            "<th>Question</th>" +
                            "<th>Correct Option</th>" +
                            "<th>Subject</th>" +
                            "<th colspan=2>Action</th>" +
                            "</tr>" +
                            "</thead>" +
                            "<tbody>");
                        
                DatabaseConnection db = new DatabaseConnection();
                db.pstmt = db.conn.prepareStatement("SELECT ques_mstr.ques_id, ques_mstr.ques, ques_mstr.answer, ques_mstr.option1, ques_mstr.option2, ques_mstr.option3, ques_mstr.option4, subject.subject_name FROM ques_mstr, subject WHERE subject.subject_id = ques_mstr.subject_id");
                db.rst = db.pstmt.executeQuery();
                while (db.rst.next()) {
                    String ques_id = db.rst.getString(1);
                    String correct_opt_id = db.rst.getString(3);
                    out.println("<tr><td>"+db.rst.getString(2).toUpperCase()+"</td>");
                    if (db.rst.getString(3).equalsIgnoreCase(("option1"))) {
                       out.println("<td>"+db.rst.getString(4).toUpperCase()+"</td>");                        
                    } else if (db.rst.getString(3).equalsIgnoreCase(("option2"))) {
                       out.println("<td>"+db.rst.getString(5).toUpperCase()+"</td>");                        
                    } else if (db.rst.getString(3).equalsIgnoreCase(("option3"))) {
                       out.println("<td>"+db.rst.getString(6).toUpperCase()+"</td>");                        
                    } else if (db.rst.getString(3).equalsIgnoreCase(("option4"))) {
                       out.println("<td>"+db.rst.getString(7).toUpperCase()+"</td>");                        
                    }
                    out.println("<td>"+db.rst.getString(8).toUpperCase()+"</td>"
                            + "<form action=\"\"><input type=hidden name=ques_id value="+ques_id+"><td><button type=submit name=view value=view class=\"btn btn-primary\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"View Question\"><i class=\"fas fa-eye\"></i></button></td>"+
                            "<td><button type=submit name=delete value=delete class=\"btn btn-danger\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Delete Question\"><i class=\"fas fa-trash-alt\"></i></button></td></form></tr>");
                }       
                out.println("</tbody>" +
                            "</table>" +
                            "</div>");

// Activation or deactivation of user

if (req.getParameter("delete") != null) {
    if (req.getParameter("delete").equals("delete")) {
        db.pstmt = db.conn.prepareStatement("DELETE FROM ques_mstr WHERE ques_id = ?");
        db.pstmt.setString(1, req.getParameter("ques_id"));
        int i = db.pstmt.executeUpdate();
        if (i > 0) {
            res.sendRedirect("ViewQuestions?sucMsg=Successfully deleted question.");
        } else {
            res.sendRedirect("ViewQuestions?errMsg=Error occured while deleting question. Please try again later.");
        }
    } 
}







//Viewing Question               
                if (req.getParameter("view") != null && req.getParameter("view").equals("view")){
                db.pstmt = db.conn.prepareStatement("SELECT ques_mstr.ques, ques_mstr.option1, ques_mstr.option2, ques_mstr.option3, ques_mstr.option4, ques_mstr.answer, subject.subject_name FROM ques_mstr, subject WHERE ques_mstr.ques_id = ? AND subject.subject_id = ques_mstr.subject_id");
                db.pstmt.setString(1, req.getParameter("ques_id"));
                db.rst = db.pstmt.executeQuery();
                if (db.rst.next()) {
                out.println("<div class=\"modal fade my-modal\" id=\"viewProfile\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalScrollableTitle\" aria-hidden=\"true\">" +
                            "<div class=\"modal-dialog modal-dialog-scrollable\" role=\"document\">" +
                            "<div class=\"modal-content\">" +
                            "<div class=\"modal-header\">" +
                            "<h5 class=\"modal-title\" id=\"exampleModalScrollableTitle\">Question Profile - ID - "+req.getParameter("ques_id")+"</h5>" +
                            "<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">" +
                            "<span aria-hidden=\"true\">&times;</span>" +
                            "</button>" +
                            "</div>" +
                            "<div class=\"modal-body\">" +
                            "<table class=\"table table-borderless\">"
                            + "<tr><td width=200><b>Question : </b></td><td>"+db.rst.getString(1)+"</td></tr>"
                            + "<tr><td width=200><b>Option 1 : </b></td><td>"+db.rst.getString(2).toUpperCase()+"</td></tr>"
                            + "<tr><td width=200><b>Option 2 : </b></td><td>"+db.rst.getString(3).toUpperCase()+"</td></tr>"
                            + "<tr><td width=200><b>Option 3 : </b></td><td>"+db.rst.getString(4).toUpperCase()+"</td></tr>"
                            + "<tr><td width=200><b>Option 4 : </b></td><td>"+db.rst.getString(5).toUpperCase()+"</td></tr>"
                            + "<tr><td width=200><b>Correct Option : </b></td><td>"+db.rst.getString(6).toUpperCase()+"</td></tr>"
                            + "<tr><td width=200><b>Subject : </b></td><td>"+db.rst.getString(7).toUpperCase()+"</td></tr>"
                            +"</table>" +
                            "</div>" +
                            "<div class=\"modal-footer\">" +
                            "<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">Close</button>" +
                            "</div>" +
                            "</div>" +
                            "</div>" +
                            "</div>");
                } else {
                    res.sendRedirect("CandidateList?errMsg=Sorry no user found !");
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
                
                
                
                out.println("<div class=\"col-md-12 footer\">" +
                            "<h2 class=\"text-center\">&copy; 2019 | All Rights Reserved | Developed By Vineet Tiwari</h2>" +
                            "</div>" +
                            "" +
                            "</div>" +
                            "</div>" +
                            "" +
                            "<script src=\"js/jquery.min.js\"></script>" +
                            "<script src=\"js/bootstrap.bundle.min.js\"></script>" +
                            "<script src=\"js/bootstrap.min.js\"></script>" 
                        + "<script src=\"js/popper.min.js\"></script>" +
                            "<script src=\"js/script.js\"></script>" 
                        + "<script>$(function () {" +
                            "  $('[data-toggle=\"tooltip\"]').tooltip()" +
                            "});$(document).ready(function(){" +
                            "  $('.toast').toast('show');" +
                            "});</script>"+
                            "</body>" +
                            "</html>");
            } else {
                res.sendRedirect("home");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
