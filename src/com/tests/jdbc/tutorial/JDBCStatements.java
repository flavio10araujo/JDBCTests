package com.tests.jdbc.tutorial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class shows the main differences between prepared and normal JDBC statements.
 */
public class JDBCStatements {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // Connection to JDBC using Mysql driver.
        Class.forName("com.mysql.jdbc.Driver");
        
        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/db_jdbc_test?user=root&password=12345");

        preparedStatement(connect, "USA");
    }

    /**
     * This method shows the main advantages of prepared statements in comparison with normal statements.
     * 
     * @throws SQLException
     */
    private static void preparedStatement(Connection conn, String name) throws SQLException {
        /*
         * 1. Precompilation and DB-side caching of the SQL statement leads to overall faster
         * execution and the ability to reuse the same SQL statement (TODO)
         */

        // 2. Automatic prevention of SQL injection attacks by using setter and getters in the operations.
        System.out.println("Updating rows for " + name + "...");

        String sql = "UPDATE COUNTRIES SET POPULATION=? WHERE NAME=?";

        PreparedStatement updateStmt = conn.prepareStatement(sql);

        // Bind values into the parameters.
        updateStmt.setInt(1, 10000000); // population
        updateStmt.setString(2, name); // name

        // Update prepared statement using executeUpdate.
        int numberRows = updateStmt.executeUpdate();

        System.out.println(numberRows + " rows updated...");

        // 3. Ease use of non standard objects as parameters using the setObject.

        updateStmt.close();

        PreparedStatement updateStmt2 = conn.prepareStatement(sql);

        // Bind values into the parameters using setObject, can be used for any kind and type of parameter.
        updateStmt2.setObject(1, 10000000); // population
        updateStmt2.setObject(2, name); // name

        // Update prepared statement using executeUpdate.
        numberRows = updateStmt2.executeUpdate();

        System.out.println(numberRows + " rows updated...");
        updateStmt2.close();
    }
}