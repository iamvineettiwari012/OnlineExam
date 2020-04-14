/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import bin.DatabaseConnection;
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
public class CheckRegUserDuplicate implements Filter {
    public void init(FilterConfig config){}
    public void destroy(){}
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) {
        String user = request.getParameter("username");
        try {
            DatabaseConnection db = new DatabaseConnection();
            db.pstmt = db.conn.prepareStatement("SELECT * FROM member_info WHERE user_id = ?");
            db.pstmt.setString(1, user);
            db.rst = db.pstmt.executeQuery();
            if (!db.rst.next()) {
                fc.doFilter(request, response);
            } else {
                HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect("register?errMsg=User already exists with username "+user);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
