/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bin;

import java.sql.*;
/**
 *
 * @author Vineet Tiwari
 */
public class DatabaseConnection {
    
public Connection conn;
public Statement stmt;
public PreparedStatement pstmt;
public ResultSet rst, rst2, rst3;

public DatabaseConnection() {
    try {
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlineexam","root","");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
