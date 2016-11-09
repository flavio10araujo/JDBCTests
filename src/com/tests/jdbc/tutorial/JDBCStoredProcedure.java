package com.tests.jdbc.tutorial;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Usage of stored procedures and callable statements.
 */
public class JDBCStoredProcedure {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // Connection to JDBC using mysql driver.
        Class.forName("com.mysql.jdbc.Driver");
        
        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/db_jdbc_test?user=root&password=12345");

        CallableStatement callableStatement = null;

        // The procedure should be created in the database.
        String spanishProcedure = "{call spanish(?)}";

        // Callable statement is used.
        callableStatement = connect.prepareCall(spanishProcedure);

        // Out parameters, also in parameters are possible, but not in this case because our procedure has only out parameters.
        callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);

        // Execute using the callable statement method executeUpdate.
        callableStatement.executeUpdate();

        // Attributes are retrieved by index.
        String total = callableStatement.getString(1);

        System.out.println("amount of spanish countries " + total);
    }
}