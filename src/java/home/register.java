/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Vineet Tiwari
 */
public class register extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession();
        if (session.getAttribute("usertype") == null) {
        try {
            res.setHeader("cache-control", "no-cache, no-store, must-revalidate");
            PrintWriter out = res.getWriter();
            out.println("<!DOCTYPE html>"
                        +"<html lang=\"en\">"
                        +"<head>"
                        +"<meta charset=\"UTF-8\">"
                        +"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                        +"<meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">"
                        +"<title>Online Exaination</title>"
                        +"<link rel=\"stylesheet\" href=\"css/bootstrap.min.css\">"
                        +"<link rel=\"stylesheet\" href=\"font-awesome/css/all.css\">"
                        +"<link rel=\"stylesheet\" href=\"css/style.css\">"
                        +"</head>"
                        +"<body>"
                        +"<div class=\"container-fluid reg-wrap\">"
                        +"<div class=\"col-md-6 offset-md-3 reg-form-wrap\">"
                        +"<header class=\"text-center\">"
                        +"<h1><i class=\"fas fa-users\"></i> Registration </h1>"
                        +"</header>"
                        +"<form class=\"myform\" method=\"post\" action=DoRegistration>"
                        +"<div class=\"form-row\">"
                        +"<div class=\"form-group col-md-6\">"
                        +"<label>Username</label>"
                        +"<input type=\"text\" name=\"username\" class=\"form-control\" placeholder=\"Enter username\">"
                        +"</div>"
                        +"<div class=\"form-group col-md-6\">"
                        +"<label for=\"inputPassword4\">Password</label>"
                        +"<input type=\"password\" name=\"password\" class=\"form-control\" placeholder=\"Enter password\">"
                        +"</div>"
                        +"</div>"
                        +"<div class=\"form-row\">"
                        +"<div class=\"form-group col-md-6\">"
                        +"<label>Name</label>"
                        +"<input type=\"text\" name=\"name\" class=\"form-control\" placeholder=\"Enter name\">"
                        +"</div>"
                        +"<div class=\"form-group col-md-6\">"
                        +"<label for=\"inputPassword4\">Father's Name</label>"
                        +"<input type=\"text\" name=\"fname\" class=\"form-control\" placeholder=\"Enter father name\">"
                        +"</div>"
                        +"</div>"
                        +"<div class=\"form-group\">"
                        +"<label>Email</label>"
                        +"<input type=\"email\" name=\"email\" class=\"form-control\" placeholder=\"Enter email\">"
                        +"</div>"
                        +"<div class=\"form-row\">"
                        +"<div class=\"form-group col-md-6\">"
                        +"<label>Gender</label>"
                        +"<div class=\"reg-gender col-md-12\">"
                        +"<label><input type=\"radio\" name=\"gender\" value=\"male\"> Male</label>"
                        +"<label class=\"mar-left\"><input type=\"radio\" name=\"gender\" value=\"female\"> Female</label>"
                        +"</div>"
                        +"</div>"
                        +"<div class=\"form-group col-md-6\">"
                        +"<label for=\"inputPassword4\">Date of birth</label>"
                        +"<input type=\"date\" name=\"dob\" class=\"form-control\">"
                        +"</div>"
                        +"</div>"
                        +"<div class=\"form-group\">"
                        +"<label>Qualification</label>"
                        +"<select class=\"form-control\" name=\"qualification\">"
                        +"<option value=\"none\" selected>Choose...</option>"
                        +"<option value=\"10\">10 (Matriculation)</option>"
                        +"<option value=\"12\">10+2 (Intermediate)</option>"
                        +"<option value=\"B.Sc.\">B.Sc.</option>"
                        +"<option value=\"BCA\">BCA</option>"
                        +"<option value=\"B.Tech.\">B.Tech.</option>"
                        +"<option value=\"M.Sc.\">M.Sc.</option>"
                        +"<option value=\"MCA\">MCA</option>"
                        +"<option value=\"M.Tech.\">M.Tech.</option>"
                        +"</select>"
                        +"</div>"
                        +"<div class=\"form-group\">"
                        +"<label>Address</label>"
                        +"<textarea name=\"address\" class=\"form-control\" placeholder=\"Enter address\"></textarea>"
                        +"</div>"
                        +"<div class=\"form-row\">"
                        +"<div class=\"form-group col-md-6\">"
                        +"<label>City</label>"
                        +"<input type=\"text\" class=\"form-control\" name=\"city\" placeholder=\"Enter city\">"
                        +"</div>"
                        +"<div class=\"form-group col-md-6\">"
                        +"<label>Contact</label>"
                        +"<input type=\"number\" name=\"contact\" class=\"form-control\" placeholder=\"Enter contact\">"
                        +"</div>"
                        +"</div>"
                        +"<div class=\"form-group\">"
                        +"<input type=\"submit\" class=\"btn btn-success btn-block\" name=\"register\" value=\"Register Now\">"
                        +"</div>"
                        +"</form>"
                        +"<p class=\"text-center\">Already Registerd ? <a href=\"home\">Login now</a></p>"
                        +"</div>"
                        +"</div>");
            if (req.getParameter("errMsg") != null) {
            
            out.println("<div aria-live=\"polite\" aria-atomic=\"true\" style=\"position: relative; min-height: 200px;\">"
                        + "<div class=\"toast col-md-2 \"  role=\"alert\" style=\"position: fixed; top: 5px; right: 5px; padding: 0px;\"  data-delay=\"5000\" data-autohide=\"true\">"
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
                        + "<div class=\"toast col-md-2 \"  role=\"alert\" style=\"position: fixed; top: 5px; right: 5px; padding: 0px;\"  data-delay=\"5000\" data-autohide=\"true\">"
                    +"  <div class=\"toast-header bg-success\">"
                    +"    <strong class=\"mr-auto\" style=\"color: #fff;\"><h4>Success Message<h4></strong>"
                    +"  </div>"
                    +"  <div class=\"toast-body err-toast-my\">"+
                    req.getParameter("sucMsg")
                    +"  </div>"
                    +"</div>"
                    + "</div>");
                
            }
            
            out.println("<script src=\"js/jquery.min.js\"></script>"
                        +"<script src=\"js/bootstrap.min.js\"></script>"
                        + "<script>$(document).ready(function(){" +
                            "  $('.toast').toast('show');" +
                            "});</script>"
                        +"</body>"
                        +"</html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        } else {
            try {
                if(session.getAttribute("usertype").equals("admin")){
                    res.sendRedirect("welcome");
                } else {
                    res.sendRedirect("dashboard");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
