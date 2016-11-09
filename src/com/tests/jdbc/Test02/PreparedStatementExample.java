package com.tests.jdbc.Test02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatementExample {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.jdbc.Driver");
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db_jdbc_test?user=root&password=12345");
		
		String sql = "UPDATE COUNTRIES SET POPULATION = ? WHERE NAME = ?";
		
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		
		preparedStatement.setInt(1, 50000000);
		preparedStatement.setString(2, "SPAIN");
		
		int numberRows = preparedStatement.executeUpdate();
		
		System.out.println("Number of rows affected: " + numberRows);
	}
}