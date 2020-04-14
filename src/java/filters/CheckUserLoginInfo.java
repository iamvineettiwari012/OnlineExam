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
public class CheckUserLoginInfo implements Filter {
    public void init(FilterConfig config) {}
    public void destroy(){}
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) {
        try {
            String user = request.getParameter("username");
            String pass = request.getParameter("password");
            
            if (user != "" && pass != ""){
                fc.doFilter(request, response);
            } else {
                HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect("home?errMsg=Username and Password must be filled.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
