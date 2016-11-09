package com.tests.jdbc.Test02;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StoredProcedureExample {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
	
		Class.forName("com.mysql.jdbc.Driver");
		
		Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/db_jdbc_test?user=root&password=12345");
		
		CallableStatement callableStatement = null;
		
		String spanishProcedure = "{call spanish(?)}";
		
		callableStatement = connect.prepareCall(spanishProcedure);
		
		callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);
		
		callableStatement.executeUpdate();
		
		String total = callableStatement.getString(1);
		
		System.out.println("Total = " + total);
	}
}