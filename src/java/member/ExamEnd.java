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
public class ExamEnd extends HttpServlet {
public void service(HttpServletRequest req, HttpServletResponse res) {
        res.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        HttpSession session = req.getSession();
        DatabaseConnection db = new DatabaseConnection();
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
                            "</div>" +
                            "</nav>" +
                            "<div class=\"container-fluid dash-cont-home\">"+
                            "<div class=\"col-md-6 offset-md-3 my-exam-mark-3 text-center\">"
                            + "<h4> Exam was successfully submitted. You can check your result from your dashboard area.</h4>"
                            + "<a href=dashboard class=\"btn btn-block btn-success\">Back to dashboard</a>"+
                            
                            "</div>"+   
                            "<script src=\"js/jquery.min.js\"></script>" +
                            "<script src=\"js/bootstrap.min.js\"></script>" +
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
