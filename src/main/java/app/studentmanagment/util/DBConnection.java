package app.studentmanagment.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides a connection to the Student Management database.
 * Uses JDBC to connect to the SQL Server database.
 */
public class DBConnection {

    private static final String URL =
            "jdbc:sqlserver://localhost:1433;"
          + "databaseName=StudentManagment;"
          + "integratedSecurity=true;"
          + "encrypt=false";

    /**
     * Creates and returns a connection to the database.
     *
     * @return a database connection
     * @throws SQLException if a database connection cannot be established
     */
    
    public static Connection getConnection() throws SQLException {
    	  try {
              Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
          } catch (ClassNotFoundException e) {
              throw new SQLException("SQL Server JDBC Driver not found.", e);
          }

          return DriverManager.getConnection(URL);
      }
    }
