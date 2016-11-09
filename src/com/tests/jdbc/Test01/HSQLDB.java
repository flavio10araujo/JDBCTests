package com.tests.jdbc.Test01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HSQLDB {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		// Loading the HSQLDB JDBC driver.
		Class.forName("org.hsqldb.jdbc.JDBCDriver");
		
		// Create the connection with the default credentials.
		java.sql.Connection conn = DriverManager.getConnection("jdbc:hsqldb:mem:mydb", "qualqueruser", "qualquersenha");
		
		// Create a table in memory.
		String countriesTableSQL = "create memory table COUNTRIES (NAME varchar(256) NOT NULL primary key, POPULATION INT NOT NULL);";
		
		// Execute the statement using JDBC normal Statements.
		Statement st = conn.createStatement();
		
		st.execute(countriesTableSQL);
		
		// Nothing is in the database because it is just in memory, non persistent.
		selectAll(conn);
		
		// After some insertions, the select shows something different.
		insertRows(conn);
		
		selectAll(conn);
	}
	
	private static void selectAll(Connection conn) throws SQLException {
		
		Statement statement = conn.createStatement();
	
		ResultSet resultSet = statement.executeQuery("SELECT * FROM COUNTRIES");
	
		while(resultSet.next()) {
			String name = resultSet.getString("NAME");
			int population = resultSet.getInt("POPULATION");
			System.out.println("NAME: " + name);
			System.out.println("POPULATION: " + population);
		}
	}
	
	private static void insertRows(Connection conn) throws SQLException {
		
        System.out.print("Inserting rows...");

        Statement insertStmt = conn.createStatement();

        String sql = "INSERT INTO COUNTRIES (NAME, POPULATION) VALUES ('SPAIN', '45000000')";
        insertStmt.executeUpdate(sql);

        sql = "INSERT INTO COUNTRIES (NAME, POPULATION) VALUES ('USA', '200000000')";
        insertStmt.executeUpdate(sql);

        sql = "INSERT INTO COUNTRIES (NAME, POPULATION) VALUES ('GERMANY', '90000000')";
        insertStmt.executeUpdate(sql);
        
        System.out.println(" Ok!");
    }
}