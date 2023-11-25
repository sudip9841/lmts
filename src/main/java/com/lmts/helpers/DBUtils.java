/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmts.helpers;

import java.sql.*;

public class DBUtils {
    public static Connection getDBConnection() throws SQLException {
        String connectionString = "jdbc:mysql://" + Config.dbHost + ":" + Config.dbPort + "/" + Config.dbName;
        return DriverManager.getConnection(connectionString, Config.dbUser, Config.dbPass);
    }
}
