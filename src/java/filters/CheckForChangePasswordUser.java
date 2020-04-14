/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import bin.DatabaseConnection;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Vineet Tiwari
 */
public class CheckForChangePasswordUser implements Filter {
    public void init(FilterConfig config) {}
    public void destroy() {}
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            String opass = request.getParameter("oldpass");
            String npass = request.getParameter("newpass");
            String cpass = request.getParameter("cpass");
            
            if (opass != "" && npass != "" && cpass != "") {
                HttpServletRequest req = (HttpServletRequest) request;
                HttpSession session = req.getSession();
                String username = session.getAttribute("username").toString();
                ServletRequest requests = (ServletRequest) req;
                DatabaseConnection db = new DatabaseConnection();
                db.pstmt = db.conn.prepareStatement("SELECT pswd FROM member_info WHERE user_id = ?");
                db.pstmt.setString(1, username);
                db.rst = db.pstmt.executeQuery();
                if (db.rst.next()) {
                    chain.doFilter(requests, response);
                } else {
                    HttpServletResponse res = (HttpServletResponse) response;
                    res.sendRedirect("ChangePassword?errMsg=Old password does not matched.");
                }
            } else {
                HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect("ChangePassword?errMsg=All the fields are required.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
