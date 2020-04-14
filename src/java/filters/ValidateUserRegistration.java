
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vineet Tiwari
 */
public class ValidateUserRegistration implements Filter {
    public void init(FilterConfig config){}
    public void destroy(){}
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        String name = request.getParameter("name");
        String fname = request.getParameter("fname");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        String dob = request.getParameter("dob");
        String qualification = request.getParameter("qualification");
        String add = request.getParameter("address");
        String city = request.getParameter("city");
        String cont = request.getParameter("contact");
        try {
            if (user != "" && pass != "" && name != "" && fname != "" && email != "" && gender != "" && dob != "" && !qualification.equals("none") && add != "" && city != "" && cont != "") {
                if (cont.length() == 10){
                    fc.doFilter(request, response);
                }  else {
                HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect("register?errMsg=Contact number should be only of 10 digits.");
            }
            } else {
                HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect("register?errMsg=All the fields are required.");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
