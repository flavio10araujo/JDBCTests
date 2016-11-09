package com.tests.jdbc.Test04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionExample {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // Delete all.
        delete();

        // Insert and update without using transactions = autocommit = true.
        withoutTransactions();

        // Lists all.
        System.out.println("*****");
        
        selectAll();

        // Delete all.
        delete();

        // Lists all.
        System.out.println( "*****" );
        
        selectAll();

        // Insert and update using transactions, at the end of the transaction will be commited.
        usingTransactions();

        // Lists all.
        System.out.println( "*****" );
        
        selectAll();

        // Delete all.
        delete();

        // Rollbacks the first transaction and commits the second.
        withoutTransactionsRollback();

        // Lists all.
        System.out.println( "*****" );
        
        selectAll();
    }

    /**
     * Deletes all entries for Japan.
     * 
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static void delete() throws SQLException, ClassNotFoundException {
        // Connection to JDBC using Mysql driver.
        Class.forName("com.mysql.jdbc.Driver");
        
        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/db_jdbc_test?user=root&password=12345");
        
        System.out.println("Deleting rows for JAPAN...");

        String sql = "DELETE FROM COUNTRIES WHERE NAME='JAPAN'";
        PreparedStatement deleteStmt = connect.prepareStatement(sql);

        // Delete statement using executeUpdate.
        int numberRows = deleteStmt.executeUpdate(sql);

        System.out.println(numberRows + " rows deleted...");

        connect.close();
    }

    /**
     * Select statement and print out results in a JDBC result set.
     * 
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static void selectAll() throws SQLException, ClassNotFoundException {
    	
        // Connection to JDBC using mysql driver.
        Class.forName("com.mysql.jdbc.Driver");
        
        Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/db_jdbc_test?user=root&password=12345");

        Statement statement = connect.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * from COUNTRIES WHERE NAME = 'JAPAN'");

        while(resultSet.next()) {
            String name = resultSet.getString("NAME");
            int population = resultSet.getInt("POPULATION");

            System.out.println("NAME: " + name);
            System.out.println("POPULATION: " + population);
        }

        connect.close();
    }

    /**
     * Inserts a row for Japan and updates it right away.
     * 
     * @param transactions if true, transactions are used and only is commited at the end, if false, autocommit=true is used.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static void insertAndUpdate(boolean transactions) throws SQLException, ClassNotFoundException {
    	
        // Connection to JDBC using mysql driver.
        Class.forName("com.mysql.jdbc.Driver");
        
        Connection connect = null;

        try {
            // Connection.
            connect = DriverManager.getConnection("jdbc:mysql://localhost/db_jdbc_test?user=root&password=12345");
            
            if (transactions) {
                connect.setAutoCommit(false);
            }
            
            System.out.println("Inserting row for Japan...");
            
            String sql = "INSERT INTO COUNTRIES (NAME, POPULATION) VALUES ('JAPAN', '127000000')";

            PreparedStatement insertStmt = connect.prepareStatement(sql);

            // Insert statement using executeUpdate.
            insertStmt.executeUpdate(sql);

            System.out.println("Updating row for Japan...");
            
            // Update statement using executeUpdate -> will cause an error, update will not be executed because the field population is an int.
            sql = "UPDATE COUNTRIES SET POPULATION = '10Mill' WHERE NAME = 'JAPAN'";
            
            PreparedStatement updateStmt = connect.prepareStatement(sql);

            updateStmt.executeUpdate(sql);
            
            if (transactions) {
                connect.commit();
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            connect.close();
        }
    }

    /**
     * Similar to the last method, rollback first transaction and commits the second one.
     * 
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static void insertAndUpdateRollback() throws SQLException, ClassNotFoundException {
        
    	Class.forName("com.mysql.jdbc.Driver");
    	
        Connection connect = null;
        
        try {
            // Connection to JDBC using mysql driver.
            connect = DriverManager.getConnection("jdbc:mysql://localhost/db_jdbc_test?user=root&password=12345");
            
            connect.setAutoCommit(false);

            System.out.println("Inserting row for Japan...");
            
            String sql = "INSERT INTO COUNTRIES (NAME, POPULATION) VALUES ('JAPAN', '127000000')";

            PreparedStatement insertStmt = connect.prepareStatement(sql);

            // Insert statement using executeUpdate.
            insertStmt.executeUpdate(sql);
            
            connect.rollback();

            System.out.println("Updating row for Japan...");
            
            sql = "UPDATE COUNTRIES SET POPULATION = '1000000' WHERE NAME = 'JAPAN'";
            
            PreparedStatement updateStmt = connect.prepareStatement(sql);

            // It will not change any data because the method rollback was called before and Japan was not inserted. 
            updateStmt.executeUpdate(sql);
            
            connect.commit();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            // Undoes all changes in current transaction.
            connect.rollback();
        }
        finally {
            connect.close();
        }
    }

    private static void withoutTransactions() throws SQLException, ClassNotFoundException {
        // Non updated row shown.
        System.out.println("without transactions...");
        
        insertAndUpdate(false);
    }

    private static void usingTransactions() throws SQLException, ClassNotFoundException {
        // Nothing shown.
        System.out.println("with transactions...");
        
        insertAndUpdate(true);
    }

    private static void withoutTransactionsRollback() throws SQLException, ClassNotFoundException {
        // Nothing shown.
        System.out.println("with rollback and transactions...");
        
        insertAndUpdateRollback();
    }
}