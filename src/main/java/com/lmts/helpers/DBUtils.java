
package com.lmts.helpers;

import java.sql.*;

public class DBUtils {
    public static Connection getDBConnection() throws SQLException {
        String connectionString = "jdbc:mysql://" + Config.dbHost + ":" + Config.dbPort + "/" + Config.dbName;
        return DriverManager.getConnection(connectionString, Config.dbUser, Config.dbPass);
    }
}
