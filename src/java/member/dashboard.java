

package member;

import bin.DatabaseConnection;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Vineet Tiwari
 */
public class dashboard extends HttpServlet {
    int d[];
    String p[];
    public void service(HttpServletRequest req, HttpServletResponse res) {
        res.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        HttpSession session = req.getSession();
        DatabaseConnection db = new DatabaseConnection();
        try {
            if (session.getAttribute("usertype") != null && session.getAttribute("usertype").equals("member")) {
                if(session.getAttribute("exam_area_status") != null && session.getAttribute("exam_area_status").toString().equalsIgnoreCase("active")) {
                    res.sendRedirect("ExamArea");
                } else {
                    d = new int[5];
                    p = new String[5];
                    
                 db.pstmt = db.conn.prepareStatement("SELECT obtained_marks, datetime FROM result_mstr WHERE user_id = ? ORDER BY datetime DESC LIMIT 5");
                 db.pstmt.setString(1, session.getAttribute("username").toString());
                 db.rst = db.pstmt.executeQuery();
                 int h = 4;
                 while (db.rst.next()) {
                     d[h] = db.rst.getInt(1);
                     String d = db.rst.getString(2).substring(0, db.rst.getString(2).indexOf(" "));
                     p[h] = d;
                     h--;
                 }
                 
                System.out.println(Arrays.toString(d));
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
                            "<li class=\"nav-item active\">" +
                            "<a class=\"nav-link\" href=\"dashboard\"><i class=\"fas fa-home\"></i> Home <span class=\"sr-only\">(current)</span></a>" +
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
                            "<a class=\"dropdown-item\" href=\"EditProfile\"><i class=\"fas fa-user-edit\"></i> Edit Profile</a>" +
                            "<a class=\"dropdown-item\" href=\"ChangePassword\"><i class=\"fas fa-cog\"></i> Change Password</a>" +
                            "<a class=\"dropdown-item\" href=\"logout\"><i class=\"fas fa-sign-out-alt\"></i> Logout</a>" +
                            "</div>" +
                            "</li>	" +
                            "</ul>" +
                            "</div>" +
                            "</div>" +
                            "</nav>" +
                            "<div class=\"container-fluid dash-cont-home\">"
                                + "<div class=\"welcome-message col-md-6 offset-md-3 text-center\">"
                                    + "<canvas id=\"myChart\"></canvas>"
                                + "</div>"
                                + "<div class=\"col-md-6\"></div>" +
                            "</div>" +
                            "<script src=\"js/jquery.min.js\"></script>" +
                            "<script src=\"js/bootstrap.min.js\"></script>"
                          + "<script src=\"js/chart.js\"></script>"
                         + "<script>"
                         + "var ctx = document.getElementById('myChart').getContext('2d');\n" +
                            "var chart = new Chart(ctx, {" +
                            "type: 'bar',"
                          + "" +
                            "data: {" +
                            "labels: [");
                            for (int s=0; s<p.length; s++) {
                                out.print("\""+p[s]+"\",");
                            }
                        out.println("]," +
                            "datasets: [{" +
                            "label: \"Progress Report\",fill:false,"
                          + "backgroundColor: 'rgb(255,53,0)'," +
                            
                            "borderColor: 'rgb(255, 99, 132)'," +
                            "data: ");
                            out.println(Arrays.toString(d));
                            out.println("," +
                            "}]" +
                            "}," +
                            "options: {responsive:true,scales: {yAxes:[{ticks:{beginAtZero:true,stepSize:10,max:50}}]}}" +
                            "});"
                        
                           + "</script>" +
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
