/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package member;

import bin.DatabaseConnection;
import java.io.*;
import java.util.LinkedHashSet;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Vineet Tiwari
 */
public class Exam_Intro extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) {
        res.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        HttpSession session = req.getSession();
        DatabaseConnection db = new DatabaseConnection();
        
        LinkedHashSet<String> qlist = new LinkedHashSet<String>();
        qlist.clear();
        try {
            if (session.getAttribute("usertype") != null && session.getAttribute("usertype").equals("member")) {
                if(session.getAttribute("exam_area_status") != null && session.getAttribute("exam_area_status").toString().equalsIgnoreCase("active")) {
                    res.sendRedirect("ExamArea");
                } else {
                PrintWriter out = res.getWriter();
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
                            "<li class=\"nav-item \">" +
                            "<a class=\"nav-link\" href=\"dashboard\"><i class=\"fas fa-home\"></i> Home <span class=\"sr-only\">(current)</span></a>" +
                            "</li>" +
                            "<li class=\"nav-item active\">"+
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
                            "<a class=\"dropdown-item\" href=\"EditProfile\"><i class=\"fas fa-user-edit\"></i> Edit Profile</a>" +
                            "<a class=\"dropdown-item\" href=\"ChangePassword\"><i class=\"fas fa-cog\"></i> Change Password</a>" +
                            "<a class=\"dropdown-item\" href=\"logout\"><i class=\"fas fa-sign-out-alt\"></i> Logout</a>" +
                            "</div>" +
                            "</li>	" +
                            "</ul>" +
                            "</div>" +
                            "</div>" +
                            "</nav>" +
                            "<div class=\"container-fluid dash-cont-home\">"+
                            "<div class=\"col-md-6 offset-md-3 my-exam-mark-2\">"
                        + "<ol type=\"I\">"
                        + "<li><span>Every question consists of 5 marks.</span></li>"
                        + "<li><span>Every wrong question will decrease 2 marks.</span></li>"
                        + "<li><span>Do not forget to submit your answers.</span></li>"
                        + "<li><span>Any misbehaviour will make you out of exam.</span></li>"
                        + "<li><span>If you enter exam once, you will get out of it only after the submission of exam.</span></li>"
                        + "<li><span>Please cooperate your invegilator.</span></li>"
                        + "</ol>"
                        + "<form action=\"\" method=\"post\">"
                        + "<input type=\"hidden\" name=\"sub_id\" value=\""+req.getParameter("exam_id")+"\">"                            
                        + "<div class=\"row\">"
                        + "<div class=\"form-group col-md-6\">"
                        + "<input type=\"submit\" value=\"Continue To Exam\" name=\"take_exam\" class=\"btn btn-block btn-success\">"
                        + "</div>"
                        + "<div class=\"form-group col-md-6\">"
                                + "<a href=\"Exam\" class=\"btn btn-block btn-danger\"> Back </a>"
                        + "</div>"
                        + "</div> </form>"
                           + "</div>"+                               
                            "</div>" +
                            "<script src=\"js/jquery.min.js\"></script>" +
                            "<script src=\"js/bootstrap.min.js\"></script>" +
                            "</body>" +
                            "</html>");
                
                if (req.getParameter("take_exam") != null) {
                    session.setAttribute("subject_paper_id", req.getParameter("sub_id"));
                    session.setAttribute("exam_area_status", "active");
                    db.pstmt = db.conn.prepareStatement("SELECT ques_id FROM ques_mstr WHERE subject_id = ? ORDER BY RAND() LIMIT 10");
                    db.pstmt.setString(1, session.getAttribute("subject_paper_id").toString());
                    db.rst = db.pstmt.executeQuery();
                    while (db.rst.next()) {
                        qlist.add(db.rst.getString(1));
                    }
                    session.setAttribute("qc", "0");
                    session.setAttribute("ques_list_from_db", qlist);
                    res.sendRedirect("ExamArea");
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
