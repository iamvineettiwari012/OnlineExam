/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package member;

import bin.DatabaseConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vineet Tiwari
 */
public class ExamArea extends HttpServlet {
    static LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();
    Thread t ;
    public void service(HttpServletRequest req, HttpServletResponse res) {
        res.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        HttpSession session = req.getSession();
        DatabaseConnection db = new DatabaseConnection();
        
        try {
            if (session.getAttribute("usertype") != null && session.getAttribute("usertype").equals("member")) {
                if(session.getAttribute("exam_area_status") != null && session.getAttribute("exam_area_status").toString().equalsIgnoreCase("active")) {
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
                            "<div class=\"container-fluid dash-cont-home\">"
                        + "<div class=\"col-md-8 offset-md-2 exam-area-back\">");
                   
                LinkedHashSet<String> qlist = (LinkedHashSet<String>) session.getAttribute("ques_list_from_db");
                Object[] ql = qlist.toArray();
                              
                 Date date = new Date();
                 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                 sdf.setTimeZone(TimeZone.getTimeZone("IST"));
                 String dateCurr = sdf.format(date);
                 
                 
                 
                if (req.getParameter("submit") != null) {
                    lhm.put(req.getParameter("ques_id_ans"), req.getParameter("answer"));
                    String[] quesid = lhm.keySet().toArray(new String[0]);
                    String[] anst = lhm.values().toArray(new String[0]);
                    int pap_id =0;
                    db.pstmt = db.conn.prepareStatement("SELECT MAX(paper_id) FROM exam_paper");
                    db.rst = db.pstmt.executeQuery();
                    if (db.rst.next()) {
                        if (db.rst.getString(1) == null) {
                            pap_id = 1000;
                        } else {
                            pap_id = Integer.parseInt(db.rst.getString(1));                            
                        }
                    }
                    pap_id++;
                    
                    int count = 0;
                    for (int i=0; i<lhm.size(); i++) {
                        db.pstmt = db.conn.prepareStatement("INSERT INTO exam_paper (paper_id, user_id, ques_id, atmt_ans, datetime) VALUES (?,?,?,?,?)");
                        db.pstmt.setString(1, String.valueOf(pap_id));
                        db.pstmt.setString(2, session.getAttribute("username").toString());
                        db.pstmt.setString(3, quesid[i]);
                        db.pstmt.setString(4, anst[i]);
                        db.pstmt.setString(5, dateCurr);
                        int ok = db.pstmt.executeUpdate();
                        if (ok > 0) {
                            count++;
                        }
                    }
                    if (count >= 9) {
                        int total_questions = lhm.size();
                        int correct_ques = 0;
                        int incorrect_ques = 0;
                        int obtained_marks = 0;
                        int toatal_marks = total_questions * 5;
                        
                        
                        db.pstmt = db.conn.prepareStatement("SELECT exam_paper.atmt_ans, ques_mstr.answer FROM exam_paper, ques_mstr WHERE exam_paper.ques_id = ques_mstr.ques_id AND exam_paper.paper_id = ?");
                        db.pstmt.setString(1, String.valueOf(pap_id));
                        db.rst = db.pstmt.executeQuery();
                        while (db.rst.next()) {
                            if (db.rst.getString(1).equals(db.rst.getString(2))) {
                                correct_ques++;
                            } else {
                                incorrect_ques++;
                            }
                        }
                        
                        obtained_marks = (correct_ques * 5) - (incorrect_ques * 2);
                        
                        db.pstmt = db.conn.prepareStatement("INSERT INTO result_mstr (paper_id, user_id, total_marks, obtained_marks, correct_ans, incorrect_ans, subject_id, datetime) VALUES (?,?,?,?,?,?,?,?)");
                        db.pstmt.setString(1, String.valueOf(pap_id));
                        db.pstmt.setString(2, session.getAttribute("username").toString());
                        db.pstmt.setInt(3, toatal_marks);
                        db.pstmt.setInt(4, obtained_marks);
                        db.pstmt.setInt(5, correct_ques);
                        db.pstmt.setInt(6, incorrect_ques);
                        db.pstmt.setString(7, session.getAttribute("subject_paper_id").toString());
                        db.pstmt.setString(8, dateCurr);
                        int ok = db.pstmt.executeUpdate();
                        if (ok > 0) {
                            
                        lhm.clear();
                        qlist.clear();     
                        session.removeAttribute("qc");
                        session.removeAttribute("ques_list_from_db");
                        session.removeAttribute("exam_area_status");
                            res.sendRedirect("ExamEnd");
                        }
                    }
                }
                
                if (req.getParameter("next") != null) {
                    int qc = Integer.parseInt(session.getAttribute("qc").toString());
                    if (req.getParameter("answer") == null) {   
                        db.pstmt = db.conn.prepareStatement("SELECT ques_id, ques, option1, option2, option3, option4 FROM ques_mstr WHERE subject_id = ? AND ques_id = ?");
                        db.pstmt.setString(1, session.getAttribute("subject_paper_id").toString());
                        db.pstmt.setString(2, ql[qc-1].toString());
                        db.rst = db.pstmt.executeQuery();
                        if (db.rst.next()){
                            int total_ques = ql.length;
                            int remaining_ques = total_ques - qc;
                            out.println("<form action=\"ExamArea\" method=\"post\"><table>"
                            + "<tr><th><h4>Question "+(total_ques - remaining_ques)+" / "+total_ques+"</h4></th></tr>"
                            + "<tr><th class=\"ques-head\">Ques : "+db.rst.getString(2)+"</th></tr>"
                            + "<tr><td><label class=myAns><input type=radio name=answer value=\"option1\"> A) "+db.rst.getString(3)+" </label></td></tr>"
                            + "<tr><td><label class=myAns><input type=radio name=answer value=\"option2\"> B) "+db.rst.getString(4)+" </label></td></tr>"
                            + "<tr><td><label class=myAns><input type=radio name=answer value=\"option3\"> C) "+db.rst.getString(5)+" </label></td></tr>"
                            + "<tr><td><label class=myAns><input type=radio name=answer value=\"option4\"> D) "+db.rst.getString(6)+" </label></td></tr>"
                            + "<input type=hidden name=ques_id_ans value=\""+db.rst.getString(1)+"\">");
                           if (remaining_ques > 0 && qc == 1) {
                             out.println("<tr><td><input type=submit name=next value=Next class=\"btn btn-primary\"></td></tr>");                           
                           } else if (remaining_ques > 0 && qc > 1) {
                             out.println("<tr><td><input type=submit name=next value=Next class=\"btn btn-primary\"> <input type=submit name=previous value=Previous class=\"btn btn-primary\"></td></tr>");                           
                           }  else {
                             out.println("<tr><td><input type=submit name=previous value=Previous class=\"btn btn-primary\"> <input type=submit name=submit value=Submit class=\"btn btn-success\"></td></tr>");
                           }
                        }   
                    } else if (lhm.get(ql[qc].toString()) != null) {
                         db.pstmt = db.conn.prepareStatement("SELECT ques_id, ques, option1, option2, option3, option4  FROM ques_mstr WHERE subject_id = ? AND ques_id = ? ");
                    db.pstmt.setString(1, session.getAttribute("subject_paper_id").toString());
                    db.pstmt.setString(2, ql[qc].toString());
                    db.rst = db.pstmt.executeQuery();
                    if (db.rst.next()) {   
                        qc++;
                        session.setAttribute("qc", String.valueOf(qc));
                         
                        int total_ques = ql.length;
                        int remaining_ques = total_ques - qc;
                       out.println("<form action=\"ExamArea\" method=\"post\"><table>"
                        + "<tr><th><h4>Question "+(total_ques - remaining_ques)+" / "+total_ques+"</h4></th></tr>"
                        + "<tr><th class=\"ques-head\">Ques : "+db.rst.getString(2)+"</th></tr>");
                       if (lhm.get(db.rst.getString(1)).equals("option1")){
                           out.println("<tr><td><label class=myAns><input type=radio name=answer value=\"option1\" checked> A) "+db.rst.getString(3)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option2\"> B) "+db.rst.getString(4)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option3\"> C) "+db.rst.getString(5)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option4\"> D) "+db.rst.getString(6)+" </label></td></tr>");                           
                       } else if (lhm.get(db.rst.getString(1)).equals("option2")){
                           out.println("<tr><td><label class=myAns><input type=radio name=answer value=\"option1\"> A) "+db.rst.getString(3)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option2\" checked> B) "+db.rst.getString(4)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option3\"> C) "+db.rst.getString(5)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option4\"> D) "+db.rst.getString(6)+" </label></td></tr>");                           
                       } else if (lhm.get(db.rst.getString(1)).equals("option3")){
                           out.println("<tr><td><label class=myAns><input type=radio name=answer value=\"option1\"> A) "+db.rst.getString(3)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option2\"> B) "+db.rst.getString(4)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option3\" checked> C) "+db.rst.getString(5)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option4\"> D) "+db.rst.getString(6)+" </label></td></tr>");                           
                       }  else if (lhm.get(db.rst.getString(1)).equals("option4")){
                           out.println("<tr><td><label class=myAns><input type=radio name=answer value=\"option1\"> A) "+db.rst.getString(3)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option2\"> B) "+db.rst.getString(4)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option3\"> C) "+db.rst.getString(5)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option4\" checked> D) "+db.rst.getString(6)+" </label></td></tr>");                           
                       }
                       out.println("<input type=hidden name=ques_id_ans value=\""+db.rst.getString(1)+"\">");
                       if (remaining_ques > 0 && qc == 1) {
                         out.println("<tr><td><input type=submit name=next value=Next class=\"btn btn-primary\"></td></tr>");                           
                       } else if (remaining_ques > 0 && qc > 1) {
                         out.println("<tr><td><input type=submit name=next value=Next class=\"btn btn-primary\"> <input type=submit name=previous value=Previous class=\"btn btn-primary\"></td></tr>");                           
                       }  else {
                         out.println("<tr><td><input type=submit name=previous value=Previous class=\"btn btn-primary\"> <input type=submit name=submit value=Submit class=\"btn btn-success\"></td></tr>");
                       }
                       out.println("</table></form>");
                    }
                    } else {
                         lhm.put(req.getParameter("ques_id_ans"), req.getParameter("answer"));
                         db.pstmt = db.conn.prepareStatement("SELECT ques_id, ques, option1, option2, option3, option4  FROM ques_mstr WHERE subject_id = ? AND ques_id = ? ");
                    db.pstmt.setString(1, session.getAttribute("subject_paper_id").toString());
                    db.pstmt.setString(2, ql[qc].toString());
                    db.rst = db.pstmt.executeQuery();
                    if (db.rst.next()) {   
                        qc++;
                        session.setAttribute("qc", String.valueOf(qc));
                        int total_ques = ql.length;
                        int remaining_ques = total_ques - qc;
                       out.println("<form action=\"ExamArea\" method=\"post\"><table>"
                        + "<tr><th><h4>Question "+(total_ques - remaining_ques)+" / "+total_ques+"</h4></th></tr>"
                        + "<tr><th class=\"ques-head\">Ques : "+db.rst.getString(2)+"</th></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option1\"> A) "+db.rst.getString(3)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option2\"> B) "+db.rst.getString(4)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option3\"> C) "+db.rst.getString(5)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option4\"> D) "+db.rst.getString(6)+" </label></td></tr>"
                        + "<input type=hidden name=ques_id_ans value=\""+db.rst.getString(1)+"\">");
                       if (remaining_ques > 0 && qc == 1) {
                         out.println("<tr><td><input type=submit name=next value=Next class=\"btn btn-primary\"></td></tr>");                           
                       } else if (remaining_ques > 0 && qc > 1) {
                         out.println("<tr><td><input type=submit name=next value=Next class=\"btn btn-primary\"> <input type=submit name=previous value=Previous class=\"btn btn-primary\"></td></tr>");                           
                       }  else {
                         out.println("<tr><td><input type=submit name=previous value=Previous class=\"btn btn-primary\"> <input type=submit name=submit value=Submit class=\"btn btn-success\"></td></tr>");
                       }
                       out.println("</table></form>");
                    }
                         
                        
                    }
                }
                
                if (req.getParameter("previous")!=null) {  
                    int qc = Integer.parseInt(session.getAttribute("qc").toString());
                    if (req.getParameter("answer") == null) {
                        db.pstmt = db.conn.prepareStatement("SELECT ques_id, ques, option1, option2, option3, option4 FROM ques_mstr WHERE subject_id = ? AND ques_id = ?");
                        db.pstmt.setString(1, session.getAttribute("subject_paper_id").toString());
                        db.pstmt.setString(2, ql[qc-1].toString());
                        db.rst = db.pstmt.executeQuery();
                        if (db.rst.next()){
                            int total_ques = ql.length;
                            int remaining_ques = total_ques - qc;
                            out.println("<form action=\"ExamArea\" method=\"post\"><table>"
                            + "<tr><th><h4>Question "+(total_ques - remaining_ques)+" / "+total_ques+"</h4></th></tr>"
                            + "<tr><th class=\"ques-head\">Ques : "+db.rst.getString(2)+"</th></tr>"
                            + "<tr><td><label class=myAns><input type=radio name=answer value=\"option1\"> A) "+db.rst.getString(3)+" </label></td></tr>"
                            + "<tr><td><label class=myAns><input type=radio name=answer value=\"option2\"> B) "+db.rst.getString(4)+" </label></td></tr>"
                            + "<tr><td><label class=myAns><input type=radio name=answer value=\"option3\"> C) "+db.rst.getString(5)+" </label></td></tr>"
                            + "<tr><td><label class=myAns><input type=radio name=answer value=\"option4\"> D) "+db.rst.getString(6)+" </label></td></tr>"
                            + "<input type=hidden name=ques_id_ans value=\""+db.rst.getString(1)+"\">");
                           if (remaining_ques > 0 && qc == 1) {
                             out.println("<tr><td><input type=submit name=next value=Next class=\"btn btn-primary\"></td></tr>");                           
                           } else if (remaining_ques > 0 && qc > 1) {
                             out.println("<tr><td><input type=submit name=next value=Next class=\"btn btn-primary\"> <input type=submit name=previous value=Previous class=\"btn btn-primary\"></td></tr>");                           
                           }  else {
                             out.println("<tr><td><input type=submit name=previous value=Previous class=\"btn btn-primary\"> <input type=submit name=submit value=Submit class=\"btn btn-success\"></td></tr>");
                           }
                        }                           
                    } else {
                        lhm.put(req.getParameter("ques_id_ans"), req.getParameter("answer"));
                        qc-=2;
                        session.setAttribute("qc", String.valueOf(qc));
                        db.pstmt = db.conn.prepareStatement("SELECT ques_id, ques, option1, option2, option3, option4  FROM ques_mstr WHERE subject_id = ? AND ques_id = ?");
                        db.pstmt.setString(1, session.getAttribute("subject_paper_id").toString());
                        db.pstmt.setString(2, ql[qc].toString());
                        db.rst = db.pstmt.executeQuery();
                        if (db.rst.next()) {
                            qc++;
                            session.setAttribute("qc", String.valueOf(qc));
                            int total_ques = ql.length;
                        int remaining_ques = total_ques - qc;
                       out.println("<form action=\"ExamArea\" method=\"post\"><table>"
                        + "<tr><th><h4>Question "+(total_ques - remaining_ques)+" / "+total_ques+"</h4></th></tr>"
                        + "<tr><th class=\"ques-head\">Ques : "+db.rst.getString(2)+"</th></tr>");
                       if (lhm.get(db.rst.getString(1)).equals("option1")){
                           out.println("<tr><td><label class=myAns><input type=radio name=answer value=\"option1\" checked> A) "+db.rst.getString(3)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option2\"> B) "+db.rst.getString(4)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option3\"> C) "+db.rst.getString(5)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option4\"> D) "+db.rst.getString(6)+" </label></td></tr>");                           
                       } else if (lhm.get(db.rst.getString(1)).equals("option2")){
                           out.println("<tr><td><label class=myAns><input type=radio name=answer value=\"option1\"> A) "+db.rst.getString(3)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option2\" checked> B) "+db.rst.getString(4)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option3\"> C) "+db.rst.getString(5)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option4\"> D) "+db.rst.getString(6)+" </label></td></tr>");                           
                       } else if (lhm.get(db.rst.getString(1)).equals("option3")){
                           out.println("<tr><td><label class=myAns><input type=radio name=answer value=\"option1\"> A) "+db.rst.getString(3)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option2\"> B) "+db.rst.getString(4)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option3\" checked> C) "+db.rst.getString(5)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option4\"> D) "+db.rst.getString(6)+" </label></td></tr>");                           
                       }  else if (lhm.get(db.rst.getString(1)).equals("option4")){
                           out.println("<tr><td><label class=myAns><input type=radio name=answer value=\"option1\"> A) "+db.rst.getString(3)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option2\"> B) "+db.rst.getString(4)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option3\"> C) "+db.rst.getString(5)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option4\" checked> D) "+db.rst.getString(6)+" </label></td></tr>");                           
                       }
                       
                      out.println("<input type=hidden name=ques_id_ans value=\""+db.rst.getString(1)+"\">");
                       if (remaining_ques > 0 && qc == 1) {
                         out.println("<tr><td><input type=submit name=next value=Next class=\"btn btn-primary\"></td></tr>");                           
                       } else if (remaining_ques > 0 && qc > 1) {
                         out.println("<tr><td><input type=submit name=next value=Next class=\"btn btn-primary\"> <input type=submit name=previous value=Previous class=\"btn btn-primary\"></td></tr>");                           
                       }  else {
                         out.println("<tr><td><input type=submit name=previous value=Previous class=\"btn btn-primary\"> <input type=submit name=submit value=Submit class=\"btn btn-success\"></td></tr>");
                       }
                       out.println("</table></form>");
                            
                        } else {
                            
                        }
                    }                                     
                }
                
                if (req.getParameter("next") == null && req.getParameter("previous") == null && req.getParameter("submit") == null) {
                    int qc = Integer.parseInt(session.getAttribute("qc").toString());
                    db.pstmt = db.conn.prepareStatement("SELECT ques_id, ques, option1, option2, option3, option4  FROM ques_mstr WHERE subject_id = ? AND ques_id = ?");
                    db.pstmt.setString(1, session.getAttribute("subject_paper_id").toString());
                    db.pstmt.setString(2, ql[qc].toString());
                    db.rst = db.pstmt.executeQuery();
                    if (db.rst.next()) {
                        qc++;
                        session.setAttribute("qc", String.valueOf(qc));
                        int total_ques = ql.length;
                        int remaining_ques = total_ques - qc;
                       out.println("<form action=\"ExamArea\" method=\"post\"><table>"
                        + "<tr><th><h4>Question "+(total_ques - remaining_ques)+" / "+total_ques+" <span id=\"time\"></span></h4></th></tr>"
                        + "<tr><th class=\"ques-head\">Ques : "+db.rst.getString(2)+"</th></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option1\"> A) "+db.rst.getString(3)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option2\"> B) "+db.rst.getString(4)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option3\"> C) "+db.rst.getString(5)+" </label></td></tr>"
                        + "<tr><td><label class=myAns><input type=radio name=answer value=\"option4\"> D) "+db.rst.getString(6)+" </label></td></tr>"
                        + "<input type=hidden name=ques_id_ans value=\""+db.rst.getString(1)+"\">");
                       if (remaining_ques > 0 && qc == 1) {
                         out.println("<tr><td><input type=submit name=next value=Next class=\"btn btn-primary\"></td></tr>");                           
                       } else if (remaining_ques > 0 && qc > 1) {
                         out.println("<tr><td><input type=submit name=next value=Next class=\"btn btn-primary\"> <input type=submit name=previous value=Previous class=\"btn btn-primary\"></td></tr>");                           
                       }  else {
                         out.println("<tr><td><input type=submit name=previous value=Previous class=\"btn btn-primary\"> <input type=submit name=submit value=Submit class=\"btn btn-success\"></td></tr>");
                       }
                       out.println("</table></form>");
                    }
                    
                }
                                              
                   out.println("</div>"+
                            "</div>" +
                            "<script src=\"js/jquery.min.js\"></script>" +
                            "<script src=\"js/bootstrap.min.js\"></script>"
                           + "<script>"
                           + "$('body').keydown(function(e) {"
                           + "return false;"
                           + "});"
                           + "</script>" +
                            "</body>" +
                            "</html>");
                
                if (req.getParameter("take_exam") != null) {
                    session.setAttribute("exam_area_status", "active");
                    session.setAttribute("subject_paper_id", req.getParameter("sub_id"));
                    res.sendRedirect("ExamArea");
                }
                } else {
                    res.sendRedirect("Exam");
                }
                
            } else {
                res.sendRedirect("home");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
