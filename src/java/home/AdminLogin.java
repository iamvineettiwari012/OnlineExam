/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

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
public class AdminLogin extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession();
        if (session.getAttribute("usertype") == null) {
        try {
            res.setHeader("cache-control", "no-cache, no-store, must-revalidate");
            PrintWriter out = res.getWriter();
            res.setContentType("text/html;charset=UTF-8");
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
                        +"<div class=\"container-fluid home-wrap\">"
                        +"<div class=\"col-md-4 offset-md-4 login-form-wrap\">"
                        +"<header class=\"text-center\">"
                        +"<h1><i class=\"fas fa-user-cog\"></i> Login</h1>"
                        +"</header>"
                        +"<form class=\"myform col-md-12\" action=DoAdminLogin method=post>"
                        +"<div class=\"form-group\">"
                        +"<label>Username</label>"
                        +"<input type=\"text\" name=\"username\" class=\"form-control\" placeholder=\"Enter username\">"
                        +"</div>"
                        +"<div class=\"form-group\">"
                        +"<label>Password</label>"
                        +"<input type=\"password\" name=\"password\" class=\"form-control\" placeholder=\"Enter password\">"
                        +"</div>"
                        +"<div class=\"form-group\">"
                        +"<input type=\"submit\" class=\"btn btn-success btn-block\" name=\"login\" value=\"LOGIN\">"
                        +"</div>"
                        +"</form>"
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
                if (session.getAttribute("usertype").equals("admin")) {
                    res.sendRedirect("welcome");
                } else {
                    res.sendRedirect("dashboard");
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
