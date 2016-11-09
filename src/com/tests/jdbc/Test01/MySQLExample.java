package com.tests.jdbc.Test01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLExample {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.jdbc.Driver");
		
		Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/db_jdbc_test?user=root&password=12345");
		
		selectAll(connect);
		selectAll2(connect);
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
	
	/**
	 * Other way how to use a resultSet.
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	private static void selectAll2(Connection conn) throws SQLException {
		
		System.out.println("Accessing column values by index...");
		
		Statement statement = conn.createStatement();
		
		// Creating the result set.
		ResultSet resultSet = statement.executeQuery("SELECT * FROM COUNTRIES");
		
		// Iterating through the results rows.
		while(resultSet.next()) {
			// Accessing column values by index.
			String name2 = resultSet.getString(2);
			int population2 = resultSet.getInt(3);
			System.out.println("NAME2: " + name2);
			System.out.println("POPULATION2: " + population2);
		}
	}
}