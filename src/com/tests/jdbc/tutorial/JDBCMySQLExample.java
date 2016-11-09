package com.tests.jdbc.tutorial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * It shows how to open a connection using a mysql driver, it makes a first select query and read the results.
 * 
 */
public class JDBCMySQLExample {

    // Would be better to handle the exceptions in a different way, but this is just for explanation purposes and this way is clearer.
    public static void main( String[] args ) throws ClassNotFoundException, SQLException {

        // Connection to JDBC using mysql driver.
        Class.forName("com.mysql.jdbc.Driver");
        
        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/db_jdbc_test?user=root&password=12345");
       
        selectAll(connect);

        // Close resources, in case of exception resources are not properly cleared.
    }

    /**
     * Select statement and print out results in a JDBC result set.
     * 
     * @param conn
     * @throws SQLException
     */
    private static void selectAll(java.sql.Connection conn) throws SQLException {
        Statement statement = conn.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM COUNTRIES");

        while(resultSet.next()) {
            String name = resultSet.getString("NAME");
            String population = resultSet.getString("POPULATION");

            System.out.println("NAME: " + name);
            System.out.println("POPULATION: " + population);
        }
    }
}