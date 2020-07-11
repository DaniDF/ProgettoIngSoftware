package it.ingsoft.persistence.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.ingsoft.model.security.CredentialsDAO;

public class DB2CredentialsDAO implements CredentialsDAO {

	static
	{
		try
		{
			Connection connection = DB2FactoryDAO.createConnection();
			Statement statement = connection.prepareStatement("CREATE TABLE CREDENTIALS ("
																	+ "USERNAME VARCHAR(255) NOT NULL PRIMARY KEY, "
																	+ "PASSWORD VARCHAR(255) NOT NULL )");
			
			statement.close();
			DB2FactoryDAO.closeConnection(connection);
			
		} catch (SQLException e) {
			System.err.println("DB credenziali gia presente");
		}
	}
	
	@Override
	public boolean checkCredentials(String username, String password) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT USERNAME FROM CREDENTIALS " +
																										"WHERE USERNAME = ? AND " +
																										"PASSWORD = ?");
		prepStatement.setString(1, username);
		prepStatement.setString(2, password);
		
		ResultSet resS = prepStatement.executeQuery();
		
		boolean result = resS.next();
		result = result && !resS.next();
		
		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);

		return result;
	}

	@Override
	public void addCredentials(String username, String password) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO CREDENTIALS (" +
																								"USERNAME, " +
																								"PASSWORD) " +
																								"VALUES (?,?)");
		prepStatement.setString(1, username);
		prepStatement.setString(2, password);
		
		prepStatement.executeUpdate();
		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public List<String> getAllUsers() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		Statement statement = connection.createStatement();
		
		ResultSet resS = statement.executeQuery("SELECT USERNAME FROM CREDENTIALS");
		List<String> result = new ArrayList<>();
		
		while(resS.next())
		{
			result.add(resS.getString("USERNAME"));
		}
		
		statement.close();
		DB2FactoryDAO.closeConnection(connection);
		
		return result;
	}

	@Override
	public void delUser(String username) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM CREDENTIALS " +
																						 	  "WHERE " +
																							  "USERNAME = ?");
		prepStatement.setString(1, username);
		
		prepStatement.executeUpdate();
		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

}
